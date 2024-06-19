package dgu.ailab.dcloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Lombok 어노테이션을 사용하여 기본 생성자 생성
public class ServerDto {

    private String serverName;
    private String publicIP;
    private String publicSubnetMask;
    private String privateIP;
    private String privateSubnetMask;
    private Integer ramGB;
    private String cpuName;
    private Integer cpuCnt;
    private String gpuName;
    private Integer gpuCnt;
    private Integer ssdGB;
    private Integer sshPort;

    // 여기에는 @OneToMany 관계에 있는 컨테이너들의 리스트나 세트를 포함시키는 것을 고려할 수 있습니다.
    // 하지만 DTO는 일반적으로 엔티티의 간단한 표현으로 사용되기 때문에,
    // 복잡한 관계를 포함시키는 것은 선택적입니다.
    // 필요에 따라 ContainerDto의 Set 또는 List를 추가할 수 있습니다.
}
