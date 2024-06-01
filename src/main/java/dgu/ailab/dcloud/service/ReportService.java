package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerConnectionErrorDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ContainerRelocationRequestDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.ExtendExpirationDateDto;
import dgu.ailab.dcloud.dto.ErrorReportDto.JustInquiryDto;
import dgu.ailab.dcloud.dto.ReportDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.Post;
import dgu.ailab.dcloud.entity.Report;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.repository.PostRepository;
import dgu.ailab.dcloud.repository.ReportRepository;
import dgu.ailab.dcloud.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, PostRepository postRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    public ContainerConnectionErrorDto insertContainerConnectionErrorReport(ContainerConnectionErrorDto reportDto) {
        // ContainerConnectionErrorDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);
        User user = userRepository.findByUserID(reportDto.getUserId());
        Post post = new Post();

        if (user == null) {
            throw new IllegalStateException("User not found with ID: " + reportDto.getUserId());
        }
        post.setCategory("UserReport");
        post.setCreatedAt(new Date());
        post.setTitle("Report by "+reportDto.getUserId());
        post.setContent(reportDto.toString());
        post.setUser(user);

        report.setPost(post);

        // 오류 게시글, 오류 신청 저장
        postRepository.save(post);
        reportRepository.save(report);


        // 저장된 엔티티를 다시 ContainerConnectionErrorDto로 매핑하여 반환
        return mapToContainerConnectionErrorDto(report);
    }

    public ContainerRelocationRequestDto insertContainerRelocationRequestReport(ContainerRelocationRequestDto reportDto) {
        // ContainerRelocationRequestDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);


        Post post = new Post();
        User user = userRepository.findByUserID(reportDto.getUserId());
        if (user == null) {
            throw new IllegalStateException("User not found with ID: " + reportDto.getUserId());
        }

        post.setCategory("UserReport");
        post.setCreatedAt(new Date());
        post.setTitle("Report by "+reportDto.getUserId());
        post.setContent(reportDto.toString());
        post.setUser(user);

        report.setPost(post);

        postRepository.save(post);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 ContainerRelocationRequestDto로 매핑하여 반환
        return mapToContainerRelocationRequestDto(report);
    }

    public ExtendExpirationDateDto insertExtendExpirationDateReport(ExtendExpirationDateDto reportDto) {
        // ExtendExpirationDateDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);


        Post post = new Post();
        User user = userRepository.findByUserID(reportDto.getUserId());
        if (user == null) {
            throw new IllegalStateException("User not found with ID: " + reportDto.getUserId());
        }

        post.setCategory("UserReport");
        post.setCreatedAt(new Date());
        post.setTitle("Report by "+reportDto.getUserId());
        post.setContent(reportDto.toString());
        post.setUser(user);

        report.setPost(post);

        postRepository.save(post);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 ExtendExpirationDateDto로 매핑하여 반환
        return mapToExtendExpirationDateDto(report);
    }

    public JustInquiryDto insertJustInquiryReport(JustInquiryDto reportDto) {
        // JustInquiryDto를 Report 엔티티로 매핑하여 저장
        Report report = mapToReportEntity(reportDto);


        Post post = new Post();
        User user = userRepository.findByUserID(reportDto.getUserId());
        if (user == null) {
            throw new IllegalStateException("User not found with ID: " + reportDto.getUserId());
        }

        post.setCategory("UserReport");
        post.setCreatedAt(new Date());
        post.setTitle("Report by "+reportDto.getUserId());
        post.setContent(reportDto.toString());
        post.setUser(user);

        report.setPost(post);

        postRepository.save(post);
        // ReportRepository를 사용하여 엔티티를 저장
        reportRepository.save(report);
        // 저장된 엔티티를 다시 JustInquiryDto로 매핑하여 반환
        return mapToJustInquiryDto(report);
    }

    private Report mapToReportEntity(ContainerConnectionErrorDto reportDto) {
        Report report = new Report();
        report.setIsAnswered(false);
        report.setCreatedAt(new Date());
        report.setCategory(reportDto.getCategory());
        report.setUserId(reportDto.getUserId());
        report.setDepartment(reportDto.getDepartment());
        report.setSshPort(reportDto.getSshPort());
        report.setStudentID(reportDto.getStudentId());
        report.setUser_name(reportDto.getName());

        return report;
    }

    private Report mapToReportEntity(ContainerRelocationRequestDto reportDto) {
        Report report = new Report();
        report.setIsAnswered(false);
        report.setCreatedAt(new Date());
        report.setCategory(reportDto.getCategory());
        report.setUserId(reportDto.getUserId());
        report.setDepartment(reportDto.getDepartment());
        report.setSshPort(reportDto.getSshPort());
        report.setStudentID(reportDto.getStudentId());
        report.setWhy(reportDto.getWhy());
        report.setUser_name(reportDto.getName());
        return report;
    }

    private Report mapToReportEntity(ExtendExpirationDateDto reportDto) {
        Report report = new Report();
        report.setIsAnswered(false);
        report.setCreatedAt(new Date());
        report.setCategory(reportDto.getCategory());
        report.setUserId(reportDto.getUserId());
        report.setDepartment(reportDto.getDepartment());
        report.setSshPort(reportDto.getSshPort());
        //report.setPermission(reportDto.getPermission());
        report.setRequirement(reportDto.getRequirement());
        report.setStudentID(reportDto.getStudentId());
        report.setWhy(reportDto.getWhy());
        report.setUser_name(reportDto.getName());
        return report;
    }

    private Report mapToReportEntity(JustInquiryDto reportDto) {
        Report report = new Report();
        report.setIsAnswered(false);
        report.setCreatedAt(new Date());
        report.setCategory(reportDto.getCategory());
        report.setUserId(reportDto.getUserId());
        report.setDepartment(reportDto.getDepartment());
        //report.setSshPort(reportDto.getSshPort()); 현재는 없는데, 나중에 필요시 주석 해제
        report.setStudentID(reportDto.getStudentId());
        report.setWhy(reportDto.getWhy());
        report.setUser_name(reportDto.getName());
        return report;
    }

    // Report 엔티티를 ContainerConnectionErrorDto로 매핑하는 메서드
    private ContainerConnectionErrorDto mapToContainerConnectionErrorDto(Report report) {
        ContainerConnectionErrorDto dto = new ContainerConnectionErrorDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setSshPort(report.getSshPort());
        dto.setPostid(report.getPost().getPostID());
        return dto;
    }

    // Report 엔티티를 ContainerRelocationRequestDto로 매핑하는 메서드
    private ContainerRelocationRequestDto mapToContainerRelocationRequestDto(Report report) {
        ContainerRelocationRequestDto dto = new ContainerRelocationRequestDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setWhy(report.getWhy());
        dto.setPostid(report.getPost().getPostID());
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
        dto.setPostid(report.getPost().getPostID());
        return dto;
    }

    // Report 엔티티를 JustInquiryDto로 매핑하는 메서드
    private JustInquiryDto mapToJustInquiryDto(Report report) {
        JustInquiryDto dto = new JustInquiryDto();
        dto.setCategory(report.getCategory());
        dto.setDepartment(report.getDepartment());
        dto.setUserId(report.getUserId());
        dto.setWhy(report.getWhy());
        dto.setPostid(report.getPost().getPostID());
        return dto;
    }

    // My Page 유저가 요청한 신고 조회하는 메소드
    public List<ReportDto> getReport(String userId) {
        List<Report> reports = reportRepository.findByUserId(userId);
        return reports.stream().map(report -> new ReportDto(
                report.getIsAnswered(),
                report.getReportID(),
                report.getCreatedAt(),
                report.getCategory(),
                report.getUserId(),
                report.getDepartment(),
                report.getRequirement(),
                report.getSshPort(),
                report.getStudentID(),
                report.getWhy(),
                report.getPost().getPostID()
        )).collect(Collectors.toList());
    }

    public List<ReportDto> findByIsAnsweredFalse() {
        List<Report> unansweredReports = reportRepository.findByIsAnsweredFalse();
        List<ReportDto> reportDtos = new ArrayList<>();

        for (Report report : unansweredReports) {
            ReportDto reportDto = new ReportDto();
            reportDto.setAnswered(report.getIsAnswered());

            reportDto.setReportId(report.getReportID());
            reportDto.setCreatedAt(report.getCreatedAt());
            reportDto.setCategory(report.getCategory());
            reportDto.setUserId(report.getUserId());
            reportDto.setDepartment(report.getDepartment());
            reportDto.setRequirement(report.getRequirement());
            reportDto.setSshPort(report.getSshPort());
            reportDto.setStudentId(report.getStudentID());
            reportDto.setWhy(report.getWhy());
            reportDto.setPostId(report.getPost().getPostID());

            reportDtos.add(reportDto);
        }

        return reportDtos;
    }
}