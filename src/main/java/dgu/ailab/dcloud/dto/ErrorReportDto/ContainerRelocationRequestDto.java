package dgu.ailab.dcloud.dto.ErrorReportDto;

import dgu.ailab.dcloud.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerRelocationRequestDto {
    private String name;
    private String department;
    private String userId;
    private String studentId;
    private int sshPort;
    private String why; // reason;
    private String category;

    public ContainerRelocationRequestDto save(ReportService reportService) {
        return reportService.insertContainerRelocationRequestReport(this);
    }
}