package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.LoginDto;
import dgu.ailab.dcloud.service.SshCommand;
import dgu.ailab.dcloud.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;

// LoginController는 로그인 기능을 위한 컨트롤러 입니다.

@RestController
@CrossOrigin
public class LoginController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final SshCommand sshCommand;
    @Autowired
    public LoginController(UserService userService, SshCommand sshCommand) {
        this.userService = userService;
        this.sshCommand = sshCommand;
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        if (userService.authenticate(loginDto.getUserID(), loginDto.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", loginDto.getUserID());
            logger.info("Login successful. UserID stored in session: {}", session.getAttribute(loginDto.getUserID()));
            sshCommand.executeCommand("210.94.179.18",8081,"mingyun","alsrbs1212","nvidia-smi");
            return "Login Successful";
        } else {
            logger.info("Login failed for userID: {}", loginDto.getUserID());
            return "Login Failed";
        }
    }
}
