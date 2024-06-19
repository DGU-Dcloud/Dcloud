package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.UserInfoDto;
import dgu.ailab.dcloud.dto.UserRoleDto;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.repository.UserRepository;
import dgu.ailab.dcloud.service.ContainerService;
import dgu.ailab.dcloud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private UserRepository userRepository;
    private UserService userRoleService;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    public RoleController(UserService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/api/user-role")
    public ResponseEntity<UserRoleDto> getUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
        }

        if (userId != null) {
            UserRoleDto userRole = userRoleService.getUserRoleDto(userId);
            userRole.toString();
//            logger.info("roleId: {}", userRole.toString());
            return ResponseEntity.ok(userRole);
        } else {
            return ResponseEntity.badRequest().body(new UserRoleDto(userId, null));
        }
    }

}
