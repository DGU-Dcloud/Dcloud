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

    //server entity 삭제되면, container entity도 삭제 수정 변경됨.
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Container> containers;
}
