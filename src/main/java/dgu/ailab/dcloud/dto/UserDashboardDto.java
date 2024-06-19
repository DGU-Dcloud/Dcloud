package dgu.ailab.dcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDashboardDto {
    private UserInfoDto userInfo;
    private List<ContainerRequestDto> containerRequests;
    private List<ContainerDto> activeContainers;
    private List<ReportDto> reports;
}
