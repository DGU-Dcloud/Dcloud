package dgu.ailab.dcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerConnectionErrorDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerRelocationRequestDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ExtendExpirationDateDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.JustInquiryDto;
import dgu.ailab.dcloud.dto.ReportDto;
import dgu.ailab.dcloud.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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?charset=utf-8";

    @Value("${upload.path}")
    private String uploadDir;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @PostMapping("/reports")
//    public ResponseEntity<?> createReport(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
    @PostMapping(value = "/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReport(@RequestParam(value = "file", required = false) MultipartFile file,
                                          @RequestParam Map<String, String> requestParams,
                                          HttpServletRequest request) {
        logger.info("Received a request to create a report.");
        // requestBody를 로깅하여 확인
//        logger.info("Request Body: {}", requestBody);

        try {
            String category = (String) requestParams.get("category");
            logger.info("category: {}", category);
            // reportData 생성
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("name", requestParams.get("name"));
            reportData.put("department", requestParams.get("department"));
            reportData.put("studentId", requestParams.get("studentId"));
            reportData.put("sshPort", requestParams.get("sshPort"));
            reportData.put("category", requestParams.get("category"));

            if (category == null ) {
                logger.info("카테고리 누락");
                return ResponseEntity.badRequest().build();
            }

            switch (category) {
                case "Container Connection Error":
                    logger.info("카테고리 인식");
                    return processReport("Container Connection Error", reportData, file, request);
                case "Container Relocation Request":
                    reportData.put("reason", requestParams.get("reason"));
                    return processReport("Container Relocation Request", reportData, file, request);
                case "Extend Expiration Date":
                    reportData.put("reason", requestParams.get("reason"));
                    reportData.put("expirationDate", requestParams.get("expirationDate"));
                    return processReport("Extend Expiration Date", reportData, file, request);
                case "Just Inquiry":
                    reportData.put("inquiryDetails", requestParams.get("inquiryDetails"));
                    return processReport("Just Inquiry", reportData, file, request);
                default:
                    return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the report request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<?> processReport(String category, Map<String, Object> reportData, MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
        }

        // Add userId to reportData
        reportData.put("userId", userId);

        // Handle file upload
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = System.currentTimeMillis() + ext;


            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    logger.error("Failed to create upload directory: {}", e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            Path filePath = uploadPath.resolve(newFilename);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("Failed to save uploaded file: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            reportData.put("imagePath", filePath.toString());
        }

        // Handle report based on category
        switch (category) {
            case "Container Connection Error":
                logger.info("processReport Container Connection Error");
                return processContainerConnectionErrorReport(reportData);
            case "Container Relocation Request":
                return processContainerRelocationRequestReport(reportData);
            case "Extend Expiration Date":
                return processExtendExpirationDateReport(reportData);
            case "Just Inquiry":
                return processJustInquiryReport(reportData);
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> processContainerConnectionErrorReport(Map<String, Object> reportData) {
        logger.info("reportData(변환전): {}", reportData);
        ContainerConnectionErrorDto reportDto = mapToContainerConnectionErrorDto(reportData);
        logger.info("reportDto(변환후): {}", reportDto);
        return ResponseEntity.ok(reportDto.save(reportService));
    }

    private ResponseEntity<?> processContainerRelocationRequestReport(Map<String, Object> reportData) {
        logger.info("reportData(변환전): {}", reportData);
        ContainerRelocationRequestDto reportDto = mapToContainerRelocationRequestDto(reportData);
        logger.info("reportDto(변환후): {}", reportDto);
        return ResponseEntity.ok(reportDto.save(reportService));
    }

    private ResponseEntity<?> processExtendExpirationDateReport(Map<String, Object> reportData) {
        logger.info("reportData(변환전): {}", reportData);
        ExtendExpirationDateDto reportDto = mapToExtendExpirationDateDto(reportData);
        logger.info("reportDto(변환후): {}", reportDto);
        return ResponseEntity.ok(reportDto.save(reportService));
    }

    private ResponseEntity<?> processJustInquiryReport(Map<String, Object> reportData) {
        logger.info("reportData(변환전): {}", reportData);
        JustInquiryDto reportDto = mapToJustInquiryDto(reportData);
        logger.info("reportDto(변환후): {}", reportDto);
        return ResponseEntity.ok(reportDto.save(reportService));
    }


    private ContainerConnectionErrorDto mapToContainerConnectionErrorDto(Map<String, Object> reportData) {
        ContainerConnectionErrorDto dto = new ContainerConnectionErrorDto();
        dto.setName((String) reportData.get("name"));
        dto.setDepartment((String) reportData.get("department"));
        dto.setUserId((String) reportData.get("userId"));
        dto.setStudentId((String) reportData.get("studentId"));
        dto.setCategory((String) reportData.get("category"));
        dto.setImagePath((String) reportData.get("imagePath"));

        // SSH 포트를 정수로 변환하여 저장, 예외 처리 추가
        try {
            String sshPortString = (String) reportData.get("sshPort");
            int sshPort = Integer.parseInt(sshPortString);
            dto.setSshPort(sshPort);
        } catch (NumberFormatException e) {
            // 예외 발생시 로그 출력 및 적절한 처리를 수행
            logger.error("Failed to parse SSH port: {}", e.getMessage());
            // 예외 처리 방법에 따라 기본값 설정 또는 예외 전파 등의 처리 가능
        }

//        sendMessageToSlack(dto);

        return dto;
    }

    private ContainerRelocationRequestDto mapToContainerRelocationRequestDto(Map<String, Object> reportData) {
        ContainerRelocationRequestDto dto = new ContainerRelocationRequestDto();
        dto.setName((String) reportData.get("name"));
        dto.setDepartment((String) reportData.get("department"));
        dto.setUserId((String) reportData.get("userId"));
        dto.setWhy((String) reportData.get("reason"));
        dto.setStudentId((String) reportData.get("studentId"));

        // SSH 포트를 정수로 변환하여 저장, 예외 처리 추가
        try {
            String sshPortString = (String) reportData.get("sshPort");
            int sshPort = Integer.parseInt(sshPortString);
            dto.setSshPort(sshPort);
        } catch (NumberFormatException e) {
            // 예외 발생시 로그 출력 및 적절한 처리를 수행
            logger.error("Failed to parse SSH port: {}", e.getMessage());
            // 예외 처리 방법에 따라 기본값 설정 또는 예외 전파 등의 처리 가능
        }

        dto.setCategory((String) reportData.get("category"));

//        sendMessageToSlack(dto);

        return dto;
    }

    private ExtendExpirationDateDto mapToExtendExpirationDateDto(Map<String, Object> reportData) {
        ExtendExpirationDateDto dto = new ExtendExpirationDateDto();
        dto.setName((String) reportData.get("name"));
        dto.setDepartment((String) reportData.get("department"));
        dto.setUserId((String) reportData.get("userId"));
        dto.setStudentId((String) reportData.get("studentId"));

        // SSH 포트를 정수로 변환하여 저장, 예외 처리 추가
        try {
            String sshPortString = (String) reportData.get("sshPort");
            int sshPort = Integer.parseInt(sshPortString);
            dto.setSshPort(sshPort);
        } catch (NumberFormatException e) {
            // 예외 발생시 로그 출력 및 적절한 처리를 수행
            logger.error("Failed to parse SSH port: {}", e.getMessage());
            // 예외 처리 방법에 따라 기본값 설정 또는 예외 전파 등의 처리 가능
        }

        //dto.setPermission((Boolean) reportData.get("permission"));
        dto.setRequirement((String) reportData.get("expirationDate"));
        dto.setWhy((String) reportData.get("reason"));
        dto.setCategory((String) reportData.get("category"));

//        sendMessageToSlack(dto);

        return dto;
    }

    private JustInquiryDto mapToJustInquiryDto(Map<String, Object> reportData) {
        JustInquiryDto dto = new JustInquiryDto();
        dto.setName((String) reportData.get("name"));
        dto.setDepartment((String) reportData.get("department"));
        dto.setUserId((String) reportData.get("userId"));
        dto.setStudentId((String) reportData.get("studentId"));
        dto.setWhy((String) reportData.get("inquiryDetails"));
        dto.setCategory((String) reportData.get("category"));

//        sendMessageToSlack(dto);

        return dto;
    }

    private void sendMessageToSlack(ContainerConnectionErrorDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 이름 : ").append(reportDto.getName()).append("\n");
        messageBuilder.append("- userID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 학번 : ").append(reportDto.getStudentId()).append("\n");
        messageBuilder.append("- SSH 포트 : ").append(reportDto.getSshPort()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(ContainerRelocationRequestDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 이름 : ").append(reportDto.getName()).append("\n");
        messageBuilder.append("- userID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 학번 : ").append(reportDto.getStudentId()).append("\n");
        messageBuilder.append("- SSH 포트 : ").append(reportDto.getSshPort()).append("\n");
        messageBuilder.append("- 사유 : ").append(reportDto.getWhy()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(ExtendExpirationDateDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 이름 : ").append(reportDto.getName()).append("\n");
        messageBuilder.append("- userID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 학번 : ").append(reportDto.getStudentId()).append("\n");
        //messageBuilder.append("- 허가 : ").append(reportDto.getPermission()).append("\n");
        messageBuilder.append("- SSH 포트 : ").append(reportDto.getSshPort()).append("\n");
        messageBuilder.append("- 희망 연장 기한 : ").append(reportDto.getRequirement()).append("\n");
        messageBuilder.append("- 연장 사유 : ").append(reportDto.getWhy()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(JustInquiryDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 이름 : ").append(reportDto.getName()).append("\n");
        messageBuilder.append("- userID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 학번 : ").append(reportDto.getStudentId()).append("\n");
        messageBuilder.append("- 문의 내용 : ").append(reportDto.getWhy()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }
    private void sendSlackMessage(String message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String payload = "{\"text\": \"" + message + "\"}";
        restTemplate.postForEntity(SLACK_WEBHOOK_URL, payload, String.class);
    }
}