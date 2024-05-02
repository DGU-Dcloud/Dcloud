package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ReportDto;
import dgu.ailab.dcloud.entity.Report;
import dgu.ailab.dcloud.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public ReportDto insertReport(ReportDto reportDto) {
        try {
            // Assuming ReportEntity conversion methods are available
            // Convert DTO to entity before saving
            Report reportEntity = convertToEntity(reportDto);
            reportEntity = reportRepository.save(reportEntity);
            // Convert back to DTO to return
            return convertToDto(reportEntity);
        } catch (Exception e) {
            logger.error("Error saving report: {}", e.getMessage(), e);
            return null;
        }
    }

    private Report convertToEntity(ReportDto reportDto) {
        // Conversion logic here
        Report reportEntity = new Report();
        reportEntity.setReportID(reportDto.getReportId());
        reportEntity.setCreatedAt(reportDto.getCreatedAt());
        reportEntity.setCategory(reportDto.getCategory());
        reportEntity.setUserId(reportDto.getUserId());
        reportEntity.setDepartment(reportDto.getDepartment());
        reportEntity.setRequirement(reportDto.getRequirement());
        reportEntity.setSshPort(reportDto.getSshPort());
        reportEntity.setStudentID(reportDto.getStudentId());
        reportEntity.setWhy(reportDto.getWhy());
        reportEntity.setIsAnswered(reportDto.isAnswered());
        return reportEntity;
    }

    private ReportDto convertToDto(Report reportEntity) {
        // Conversion logic here
        return new ReportDto(
                reportEntity.getIsAnswered(),
                reportEntity.getReportID(),
                reportEntity.getCreatedAt(),
                reportEntity.getCategory(),
                reportEntity.getUserId(),
                reportEntity.getDepartment(),
                reportEntity.getRequirement(),
                reportEntity.getSshPort(),
                reportEntity.getStudentID(),
                reportEntity.getWhy()
        );
    }
}
