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

    public ExtendExpirationDateDto save(ReportService reportService) {
        return reportService.insertExtendExpirationDateReport(this);
    }
}
