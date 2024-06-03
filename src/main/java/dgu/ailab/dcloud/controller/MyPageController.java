package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.*;
import dgu.ailab.dcloud.service.ContainerService;
import dgu.ailab.dcloud.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

import dgu.ailab.dcloud.entity.User;

import dgu.ailab.dcloud.service.UserService;

import java.util.List;

// MyPageController는 유저의 개인정보를 불러오는 컨트롤러 입니다.

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // CORS 설정 변경
public class MyPageController {

    private final UserService userService;
    private final ContainerService containerService;
    private final ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public MyPageController(UserService userService, ContainerService containerService, ReportService reportService) {
        this.userService = userService;
        this.containerService = containerService;
        this.reportService = reportService;
    }

    @GetMapping("/api/yourinfo")
    public UserDashboardDto getUserInfo(HttpSession session) {
        String userId = (String) session.getAttribute("userID");
        logger.info("Retrieved userId: {}", userId);
        if (userId != null) {
            UserInfoDto userInfo = userService.getUserInfo(userId);
            List<ContainerRequestDto> containerRequests = containerService.getContainerRequestStatus(userId);
            List<ContainerDto> activeContainers = containerService.getActiveContainer(userId);
            List<ReportDto> reports = reportService.getReport(userId);
            return new UserDashboardDto(userInfo, containerRequests, activeContainers, reports);
        } else {
            throw new IllegalStateException("No user is logged in or session does not exist");
        }
    }
}
