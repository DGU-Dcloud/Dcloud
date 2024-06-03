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
    private int postid;

    public ContainerRelocationRequestDto save(ReportService reportService) {
        return reportService.insertContainerRelocationRequestReport(this);
    }

    @Override
    public String toString() {
        return "컨테이너 이전 요청 보고서 정보\n" +
                " - 이름: '" + name + '\'' +
                "\n - 학과: '" + department + '\'' +
                "\n - 사용자 ID: '" + userId + '\'' +
                "\n - 학번: '" + studentId + '\'' +
                "\n - SSH 포트: " + sshPort +
                "\n - 이유: '" + why + '\'' +
                "\n - 카테고리: '" + category + '\'';
    }

}