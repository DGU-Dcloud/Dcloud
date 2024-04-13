package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.LoginDto;
import dgu.ailab.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class LoginController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto loginDto){
        if (userService.authenticate(loginDto.getUserID(), loginDto.getPassword())) {
            logger.info("Retrieved login id: {}", loginDto.getUserID());
            logger.info("Retrieved login pw: {}", loginDto.getPassword());
            return "Login Successful";
        } else {
            logger.info("Retrieved login id: {}", loginDto.getUserID());
            logger.info("Retrieved login pw: {}", loginDto.getPassword());
            return "Login Failed";
        }
    }

}
