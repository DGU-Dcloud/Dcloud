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
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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?charset=utf-8";

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public ResponseEntity<?> createReport(@RequestBody Object reportDto, HttpServletRequest request) {
        if (reportDto instanceof ContainerConnectionErrorDto) {
            return processContainerConnectionErrorReport((ContainerConnectionErrorDto) reportDto, request);
        } else if (reportDto instanceof ContainerRelocationRequestDto) {
            return processContainerRelocationRequestReport((ContainerRelocationRequestDto) reportDto, request);
        } else if (reportDto instanceof ExtendExpirationDateDto) {
            return processExtendExpirationDateReport((ExtendExpirationDateDto) reportDto, request);
        } else if (reportDto instanceof JustInquiryDto) {
            return processJustInquiryReport((JustInquiryDto) reportDto, request);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> processContainerConnectionErrorReport(ContainerConnectionErrorDto reportDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            reportDto.setUserId(userId);
        }

        ContainerConnectionErrorDto savedReport = reportService.insertContainerConnectionErrorReport(reportDto);
        if (savedReport != null) {
            sendMessageToSlack(reportDto);
            return ResponseEntity.ok(savedReport);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> processContainerRelocationRequestReport(ContainerRelocationRequestDto reportDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            reportDto.setUserId(userId);
        }

        ContainerRelocationRequestDto savedReport = reportService.insertContainerRelocationRequestReport(reportDto);
        if (savedReport != null) {
            sendMessageToSlack(reportDto);
            return ResponseEntity.ok(savedReport);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> processExtendExpirationDateReport(ExtendExpirationDateDto reportDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            reportDto.setUserId(userId);
        }

        ExtendExpirationDateDto savedReport = reportService.insertExtendExpirationDateReport(reportDto);
        if (savedReport != null) {
            sendMessageToSlack(reportDto);
            return ResponseEntity.ok(savedReport);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> processJustInquiryReport(JustInquiryDto reportDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            reportDto.setUserId(userId);
        }

        JustInquiryDto savedReport = reportService.insertJustInquiryReport(reportDto);
        if (savedReport != null) {
            sendMessageToSlack(reportDto);
            return ResponseEntity.ok(savedReport);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private void sendMessageToSlack(ContainerConnectionErrorDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 사용자 ID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- SSH 포트 : ").append(reportDto.getSshPort()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(ContainerRelocationRequestDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 사용자 ID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 이유 : ").append(reportDto.getWhy()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(ExtendExpirationDateDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 사용자 ID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
        messageBuilder.append("- 허가 : ").append(reportDto.getPermission()).append("\n");
        messageBuilder.append("- 만료일 : ").append(reportDto.getRequirement()).append("\n");
        messageBuilder.append("- 이유 : ").append(reportDto.getWhy()).append("\n");

        String message = messageBuilder.toString();
        sendSlackMessage(message);
    }

    private void sendMessageToSlack(JustInquiryDto reportDto) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("사용자로부터 오류신고가 들어왔습니다.\n");
        messageBuilder.append("- 카테고리 : ").append(reportDto.getCategory()).append("\n");
        messageBuilder.append("- 사용자 ID: ").append(reportDto.getUserId()).append("\n");
        messageBuilder.append("- 학과 : ").append(reportDto.getDepartment()).append("\n");
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