package dgu.ailab.dcloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "`SERVER`")
@Getter @Setter @NoArgsConstructor// Lombok 어노테이션을 사용하여 게터와 세터 메소드 생성
public class Server {

    @Id
    @Column(name = "serverName", length = 50, nullable = false)
    private String serverName;

    @Column(name = "publicIP", length = 16)
    private String publicIP;

    @Column(name = "publicSubnetMask", length = 16)
    private String publicSubnetMask;

    @Column(name = "privateIP", length = 16)
    private String privateIP;

    @Column(name = "privateSubnetMask", length = 16)
    private String privateSubnetMask;

    @Column(name = "ram_GB")
    private Integer ramGB;

    @Column(name = "cpuName", length = 255)
    private String cpuName;

    @Column(name = "cpuCnt")
    private Integer cpuCnt;

    @Column(name = "gpuName", length = 255)
    private String gpuName;

    @Column(name = "gpuCnt")
    private Integer gpuCnt;

    @Column(name = "ssd_GB")
    private Integer ssdGB;

    @Column(name = "sshPort")
    private Integer sshPort;

    //server삭제되면, container도 삭제 수정 변경됨. mappedBy는 Container엔티티에 server라는 필드가 있음을 뜻함.
    //cascadeType.ALL은 서버 엔티티에 저장,업데이트,삭제 명령이 컨테이너 엔티티에도 적용됨을 의미한다.
    //orphanRemoval=true는 서버와 컨테이너간 참조가 제거된 경우, 해당 인스턴스는 컨테이너 엔티티에서 삭제됨을 의미한다.
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Container> containers;
}
