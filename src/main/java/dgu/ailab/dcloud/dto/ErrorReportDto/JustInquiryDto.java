package dgu.ailab.dcloud.dto.ErrorReportDto;

import dgu.ailab.dcloud.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustInquiryDto {
    private String name;
    private String department;
    private String userId;
    private String studentId;
    private String why; // inquiryDetails;
    private String category;

    public JustInquiryDto save(ReportService reportService) {
        return reportService.insertJustInquiryReport(this);
    }
}