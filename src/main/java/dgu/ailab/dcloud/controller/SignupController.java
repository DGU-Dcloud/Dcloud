package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupDto signupDto) {
        if (userService.isUserExists(signupDto.getUserID())) {
            return "이미 사용 중인 아이디입니다.";
        }
        userService.signup(signupDto);
        return "회원가입이 완료되었습니다.";
    }
}
