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
        return "단순 문의 보고서 정보" +
                " - 이름: '" + name + '\'' +
                ", 부서: '" + department + '\'' +
                ", 사용자 ID: '" + userId + '\'' +
                ", 학생 ID: '" + studentId + '\'' +
                ", 문의 내용: '" + why + '\'' +
                ", 카테고리: '" + category + '\'';
    }


}