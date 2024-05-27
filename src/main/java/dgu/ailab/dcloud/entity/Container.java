package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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
            @JoinColumn(name = "imageName", referencedColumnName = "imageName", insertable = true, updatable = true),
            @JoinColumn(name = "imageTag", referencedColumnName = "imageTag", insertable = true, updatable = true)
    })
    private DockerImages dockerImages;

    @ManyToOne
    @JoinColumn(name = "serverName", referencedColumnName = "serverName", insertable = true, updatable = true)
    private Server server;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = true, updatable = true)
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

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "request_id", insertable = true, updatable = true)
    private ContainerRequest containerRequest;

    // Lombok을 사용하지 않는다면, 필요한 getter와 setter 메소드를 수동으로 추가해야 합니다.
    // 기본 생성자 또한 Lombok @NoArgsConstructor 어노테이션으로 자동 생성할 수 있습니다.
}
