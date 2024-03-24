package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "`CONTAINER`")
@Getter @Setter
public class Container {

    @Id
    @Column(name = "containerID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략을 가정함
    private Integer containerID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "imageName", referencedColumnName = "imageName", insertable = false, updatable = false),
            @JoinColumn(name = "imageTag", referencedColumnName = "imageTag", insertable = false, updatable = false)
    })
    private DockerImages dockerImages;

    @ManyToOne
    @JoinColumn(name = "serverName", referencedColumnName = "serverName", insertable = false, updatable = false)
    private Server server;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;

    @Column(name = "createdAt")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "deletedAt")
    @Temporal(TemporalType.DATE)
    private Date deletedAt;

    @Column(name = "containerName")
    private String containerName;

    @Column(name = "sshPort")
    private Integer sshPort;

    @Column(name = "jupyterPort")
    private Integer jupyterPort;

    @Column(name = "note")
    private String note;

    // Lombok을 사용하지 않는다면, 필요한 getter와 setter 메소드를 수동으로 추가해야 합니다.
    // 기본 생성자 또한 Lombok @NoArgsConstructor 어노테이션으로 자동 생성할 수 있습니다.
}
