package dgu.ailab.dcloud.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;


// AuthController는 로그아웃과 세션 검증을 위한 컨트롤러 입니다.

@RestController
@CrossOrigin
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @GetMapping("/api/check-auth")
    public ResponseEntity<Map<String, String>> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없다면 null을 반환
        Map<String, String> response = new HashMap<>();

        if (session != null) {
            String userId = (String) session.getAttribute("userID");
            if (userId != null) {
                response.put("userID", userId);
                response.put("message", "User is authenticated");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "User is not authenticated");
                return ResponseEntity.status(401).body(response);
            }
        } else {
            response.put("message", "Session not found");
            return ResponseEntity.status(401).body(response);
        }
    }


}
