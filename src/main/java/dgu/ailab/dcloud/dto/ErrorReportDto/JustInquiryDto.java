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
    private int postid;

    public JustInquiryDto save(ReportService reportService) {
        return reportService.insertJustInquiryReport(this);
    }

    @Override
    public String toString() {
        return "단순 문의 보고서 정보\n" +
                " - 이름: '" + name + '\'' +
                "\n - 학과: '" + department + '\'' +
                "\n - 사용자 ID: '" + userId + '\'' +
                "\n - 학번: '" + studentId + '\'' +
                "\n - 문의 내용: '" + why + '\'' +
                "\n - 카테고리: '" + category + '\'';
    }


}