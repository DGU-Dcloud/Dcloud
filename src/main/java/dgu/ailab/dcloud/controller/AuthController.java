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

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")  // CORS 설정 추가
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @GetMapping("/api/check-auth")
    public ResponseEntity<String> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없다면 null을 반환
        if (session != null) {
            String userId = (String) session.getAttribute("userID");
            if (userId != null) {
                logger.info("Session ID: {}, User ID: {}", session.getId(), userId);
                return ResponseEntity.ok("User is authenticated");
            } else {
                logger.info("No user ID found in session");
                return ResponseEntity.status(401).body("User is not authenticated");
            }
        } else {
            logger.info("No session found");
            return ResponseEntity.status(401).body("Session not found");
        }
    }


}
