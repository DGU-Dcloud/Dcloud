package dgu.ailab.dcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dgu.ailab.dcloud.dto.ReportDto;
import dgu.ailab.dcloud.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Date;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
//    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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";

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        logger.info("Received userId: {}", userId);
        if (session != null) {
            userId = (String) session.getAttribute("userID");
            reportDto.setUserId(userId); // Set user ID from session if present

            logger.info("Received new report submission: {}", reportDto);
        }
        reportDto.setCreatedAt(new Date());
        // Log the receipt of a new report
        logger.info("Received new report submission: {}", reportDto);


        ReportDto savedReport = reportService.insertReport(reportDto);
        if (savedReport != null) {
            logger.info("Report saved successfully with ID: {}", savedReport.getReportId());
            return ResponseEntity.ok(savedReport);
        } else {
            logger.error("Failed to save the report");
            return ResponseEntity.badRequest().build(); // Optionally, return more specific error responses
        }
    }


}
