package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.entity.Role;
import dgu.ailab.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // CORS
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/signup")
    public String signup(@RequestBody SignupDto signupDto) {
        if (userService.isUserExists(signupDto.getUserID())) {
            return "This ID is already in use.";
        }

        Integer roleId = userService.getRoleId(signupDto.getRole());
        if (roleId == null) {
            return "Invalid role.";
        }

        userService.signup(signupDto, roleId);
        return "Membership registration is complete.";
    }
}
