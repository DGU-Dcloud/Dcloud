package dgu.ailab.dcloud.dto.ErrorReportDto;

import dgu.ailab.dcloud.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerConnectionErrorDto {
    private String name;
    private String department;
    private String userId;
    private String studentId;
    private int sshPort;
    private String category;

    public void setUserId(String userId) {
    }
    public ContainerConnectionErrorDto save(ReportService reportService) {
        return reportService.insertContainerConnectionErrorReport(this);
    }

}