package dgu.ailab.dcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dgu.ailab.dcloud.dto.ActionRequestDto;
import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.dto.ReportDto;
import dgu.ailab.dcloud.service.ContainerService;
import dgu.ailab.dcloud.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

// ContainerController는 사용자의 컨테이너 요청을 처리하는 컨트롤러 입니다.

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ContainerController {

    private final ContainerService containerService;
    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);
    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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?charset=utf-8";

    @Autowired
    public ContainerController(ContainerService containerService, ReportService reportService) {
        this.containerService = containerService;
        this.reportService = reportService;
    }

    @GetMapping("/containers")
    public ResponseEntity<List<ContainerDto>> getAllContainers() {
        List<ContainerDto> containers = containerService.getAllContainers();
        return ResponseEntity.ok(containers);
    }

    @GetMapping("/allcontainerrequest")
    public ResponseEntity<ResponseDto> getAllContainerRequests() {
        try {
            logger.info("Fetching all container requests");
            List<ContainerRequestDto> containerRequests = containerService.findAllContainerRequests();
            List<ReportDto> answeredReports = reportService.findByIsAnsweredFalse();

            ResponseDto responseDto = new ResponseDto();
            responseDto.setContainerRequests(containerRequests);
            responseDto.setAnsweredReports(answeredReports);
            logger.info("{}", responseDto.toString());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            logger.error("Error fetching container requests and reports", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/admin-action") // admin이 apply or deny를 한 경우
    public ResponseEntity<?> handleRequestAction(@RequestBody ActionRequestDto requestDto) {
        try {

            if ("Apply".equals(requestDto.getAction())) {
                containerService.applyRequests(requestDto.getIds());
            } else if ("Deny".equals(requestDto.getAction())) {
                containerService.denyRequests(requestDto.getIds());
            } else if ("Pending".equals(requestDto.getAction())) {
                containerService.pendingRequests(requestDto.getIds());
            }
            else {
                return ResponseEntity.badRequest().body("Invalid action specified");
            }
            return ResponseEntity.ok("Action processed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing request: " + e.getMessage());
        }
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
//        sendMessageToSlack(containerRequestDto);

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

    private void sendMessageToSlack(ContainerRequestDto containerRequestDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 컨테이너 생성 요청이 들어왔습니다.\n");
        messageBuilder.append("- 사용자 ID: ").append(containerRequestDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(containerRequestDto.getDepartment()).append("\n");
        messageBuilder.append("- 서버 : ").append(containerRequestDto.getEnvironment()).append("\n");
        messageBuilder.append("- 학번 : ").append(containerRequestDto.getStudentId()).append("\n");
        messageBuilder.append("- 사용 용도 : ").append(containerRequestDto.getUsageDescription()).append("\n");
        messageBuilder.append("- Image Name : ").append(containerRequestDto.getImageName()).append("\n");
        messageBuilder.append("- Image Tag : ").append(containerRequestDto.getImageTag()).append("\n");
        messageBuilder.append("- 희망 GPU 모델 : ").append(containerRequestDto.getGpuModel()).append("\n");
        messageBuilder.append("- 지도 교수 : ").append(containerRequestDto.getProfessorName()).append("\n");
        messageBuilder.append("- 사용 기한 : ").append(containerRequestDto.getExpectedExpirationDate()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendSlackMessage(String message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ObjectMapper objectMapper = new ObjectMapper();
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(Map.of("text", message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // JSON 처리 예외 처리
        }
        restTemplate.postForEntity(SLACK_WEBHOOK_URL, payload, String.class);
    }


    public class ResponseDto {
        private List<ContainerRequestDto> containerRequests;
        private List<ReportDto> answeredReports;

        // 기본 생성자
        public ResponseDto() {
        }

        // 생성자
        public ResponseDto(List<ContainerRequestDto> containerRequests, List<ReportDto> answeredReports) {
            this.containerRequests = containerRequests;
            this.answeredReports = answeredReports;
        }

        // Getters and Setters
        public List<ContainerRequestDto> getContainerRequests() {
            return containerRequests;
        }

        public void setContainerRequests(List<ContainerRequestDto> containerRequests) {
            this.containerRequests = containerRequests;
        }

        public List<ReportDto> getAnsweredReports() {
            return answeredReports;
        }

        public void setAnsweredReports(List<ReportDto> answeredReports) {
            this.answeredReports = answeredReports;
        }

        @Override
        public String toString() {
            return "ResponseDto{" +
                    "containerRequests=" + containerRequests +
                    ", answeredReports=" + answeredReports +
                    '}';
        }
    }
}
