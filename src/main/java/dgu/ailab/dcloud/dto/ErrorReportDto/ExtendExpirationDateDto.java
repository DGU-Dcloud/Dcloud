package dgu.ailab.dcloud.dto.ErrorReportDto;

import dgu.ailab.dcloud.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendExpirationDateDto {
    private String name;
    private String department;
    private String userId;
    private String studentId;
    private int sshPort;
    private Boolean permission;
    private String requirement; // expirationDate;
    private String why; // reason;
    private String category;
    private int postid;

    public ExtendExpirationDateDto save(ReportService reportService) {
        return reportService.insertExtendExpirationDateReport(this);
    }

    @Override
    public String toString() {
        return "만료일 연장 요청 보고서 정보" +
                " - 이름: '" + name + '\'' +
                ", 부서: '" + department + '\'' +
                ", 사용자 ID: '" + userId + '\'' +
                ", 학생 ID: '" + studentId + '\'' +
                ", SSH 포트: " + sshPort +
                ", 허가 여부: " + permission +
                ", 만료일 연장 요구 사항: '" + requirement + '\'' +
                ", 이유: '" + why + '\'' +
                ", 카테고리: '" + category + '\'';
    }


}
