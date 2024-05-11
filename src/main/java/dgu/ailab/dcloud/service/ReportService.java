package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerConnectionErrorDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerRelocationRequestDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ExtendExpirationDateDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.JustInquiryDto;
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
    public ContainerConnectionErrorDto insertContainerConnectionErrorReport(ContainerConnectionErrorDto reportDto) {
        // ContainerConnectionErrorDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 ContainerConnectionErrorDto로 매핑하여 반환
        return mapToContainerConnectionErrorDto(report);
    }

    public ContainerRelocationRequestDto insertContainerRelocationRequestReport(ContainerRelocationRequestDto reportDto) {
        // ContainerRelocationRequestDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 ContainerRelocationRequestDto로 매핑하여 반환
        return mapToContainerRelocationRequestDto(report);
    }

    public ExtendExpirationDateDto insertExtendExpirationDateReport(ExtendExpirationDateDto reportDto) {
        // ExtendExpirationDateDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 ExtendExpirationDateDto로 매핑하여 반환
        return mapToExtendExpirationDateDto(report);
    }

    public JustInquiryDto insertJustInquiryReport(JustInquiryDto reportDto) {
        // JustInquiryDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 JustInquiryDto로 매핑하여 반환
        return mapToJustInquiryDto(report);
    }

    private Report mapToReportEntity(ContainerConnectionErrorDto reportDto) {
        Report report = new Report();
        report.setCategory(reportDto.getCategory());
        report.setDepartment(reportDto.getDepartment());
        report.setUserId(reportDto.getUserId());
        report.setSshPort(reportDto.getSshPort());
        return report;
    }

    private Report mapToReportEntity(ContainerRelocationRequestDto reportDto) {
        Report report = new Report();
        report.setCategory(reportDto.getCategory());
        report.setDepartment(reportDto.getDepartment());
        report.setUserId(reportDto.getUserId());
        report.setWhy(reportDto.getWhy());
        return report;
    }

    private Report mapToReportEntity(ExtendExpirationDateDto reportDto) {
        Report report = new Report();
        report.setCategory(reportDto.getCategory());
        report.setDepartment(reportDto.getDepartment());
        report.setUserId(reportDto.getUserId());
        //report.setPermission(reportDto.getPermission());
        report.setRequirement(reportDto.getRequirement());
        report.setWhy(reportDto.getWhy());
        return report;
    }

    private Report mapToReportEntity(JustInquiryDto reportDto) {
        Report report = new Report();
        report.setCategory(reportDto.getCategory());
        report.setDepartment(reportDto.getDepartment());
        report.setUserId(reportDto.getUserId());
        report.setWhy(reportDto.getWhy());
        return report;
    }

    // Report 엔티티를 ContainerConnectionErrorDto로 매핑하는 메서드
    private ContainerConnectionErrorDto mapToContainerConnectionErrorDto(Report report) {
        ContainerConnectionErrorDto dto = new ContainerConnectionErrorDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setSshPort(report.getSshPort());
        return dto;
    }

    // Report 엔티티를 ContainerRelocationRequestDto로 매핑하는 메서드
    private ContainerRelocationRequestDto mapToContainerRelocationRequestDto(Report report) {
        ContainerRelocationRequestDto dto = new ContainerRelocationRequestDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setWhy(report.getWhy());
        return dto;
    }

    // Report 엔티티를 ExtendExpirationDateDto로 매핑하는 메서드
    private ExtendExpirationDateDto mapToExtendExpirationDateDto(Report report) {
        ExtendExpirationDateDto dto = new ExtendExpirationDateDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setPermission(Boolean.TRUE);
        dto.setRequirement(report.getRequirement());
        dto.setWhy(report.getWhy());
        return dto;
    }

    // Report 엔티티를 JustInquiryDto로 매핑하는 메서드
    private JustInquiryDto mapToJustInquiryDto(Report report) {
        JustInquiryDto dto = new JustInquiryDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setWhy(report.getWhy());
        return dto;
    }
}