package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.LoginDto;
import dgu.ailab.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        if (userService.authenticate(loginDto.getUserID(), loginDto.getPassword())) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }

}
