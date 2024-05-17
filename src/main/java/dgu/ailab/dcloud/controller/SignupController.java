package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.entity.Role;
import dgu.ailab.dcloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 회원가입용 컨트롤러

@RestController
@CrossOrigin // CORS
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
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
//        logger.info("Retrieved roleId: {}", roleId);

        if (roleId == null) {
            return "Invalid role.";
        }

        userService.signup(signupDto, roleId);
        return "Membership registration is complete.";
    }
}
