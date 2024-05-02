package dgu.ailab.dcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.service.ContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

// ContainerController는 사용자의 컨테이너 요청을 처리하는 컨트롤러 입니다.

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ContainerController {

    private final ContainerService containerService;
    private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);
    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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";

    @Autowired
    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping("/containers")
    public ResponseEntity<List<ContainerDto>> getAllContainers() {
        List<ContainerDto> containers = containerService.getAllContainers();
        return ResponseEntity.ok(containers);
    }

    @PostMapping("/containerrequest") // insert to request form
    public ResponseEntity<ContainerRequestDto> createContainer(@RequestBody ContainerRequestDto containerRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            containerRequestDto.setUserId(userId);
        }

        // Send message to Slack
        sendMessageToSlack("Processing container request for user: " + containerRequestDto.toString());

        ContainerRequestDto savedContainerRequest = containerService.insertContainerRequest(containerRequestDto);
        logger.info("Retrieved roleId: {}", containerRequestDto.getDepartment());
        logger.info("Retrieved roleId: {}", containerRequestDto.getImageTag());
        logger.info("Retrieved roleId: {}", containerRequestDto.getImageName());
        logger.info("Retrieved roleId: {}", containerRequestDto.getGpuModel());
        logger.info("Retrieved roleId: {}", containerRequestDto.getUsageDescription());
        logger.info("Retrieved roleId: {}", containerRequestDto.getExpectedExpirationDate());
        logger.info("Retrieved roleId: {}", containerRequestDto.getEnvironment());
        logger.info("Retrieved roleId: {}", containerRequestDto.getStudentId());

        if (savedContainerRequest != null) {
            return ResponseEntity.ok(savedContainerRequest);
        } else {
            return ResponseEntity.badRequest().build(); // Optionally, return more specific error responses
        }
    }

    private void sendMessageToSlack(String message) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(Map.of("text", message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle JSON processing exception
        }
        restTemplate.postForEntity(SLACK_WEBHOOK_URL, payload, String.class);
    }
}
