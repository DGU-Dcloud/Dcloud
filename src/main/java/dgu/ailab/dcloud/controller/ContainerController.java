package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.service.ContainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ContainerController {

    private final ContainerService containerService;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    @Autowired
    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping("/containers")
    public ResponseEntity<List<ContainerDto>> getAllContainers() {
        List<ContainerDto> containers = containerService.getAllContainers();
        return ResponseEntity.ok(containers);
    }

    @PostMapping("/containerrequest")
    public ResponseEntity<ContainerRequestDto> createContainer(@RequestBody ContainerRequestDto containerRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId="";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            containerRequestDto.setUserId(userId);
        }


        ContainerRequestDto savedContainerRequest = containerService.insertContainerRequest(containerRequestDto);
        logger.info("Retrieved roleId: {}", containerRequestDto.getDepartment());
        logger.info("Retrieved roleId: {}", containerRequestDto.getImageTag());
        logger.info("Retrieved roleId: {}", containerRequestDto.getImageName());
        logger.info("Retrieved roleId: {}", containerRequestDto.getGpuModel());
        logger.info("Retrieved roleId: {}", containerRequestDto.getUsageDescription()); // 여기부터 널나옴.
        logger.info("Retrieved roleId: {}", containerRequestDto.getExpectedExpirationDate());
        logger.info("Retrieved roleId: {}", containerRequestDto.getEnvironment());
        logger.info("Retrieved roleId: {}", containerRequestDto.getStudentId());

        if (savedContainerRequest != null) {
            return ResponseEntity.ok(savedContainerRequest);
        } else {
            return ResponseEntity.badRequest().build(); // Optionally, return more specific error responses
        }
    }
}
