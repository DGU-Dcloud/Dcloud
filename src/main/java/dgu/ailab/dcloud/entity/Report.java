package dgu.ailab.dcloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "`REPORT`")
@Getter @Setter @NoArgsConstructor// Lombok 어노테이션 사용
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportID", nullable = false)
    private Integer reportID;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "isAnswered")
    private Boolean isAnswered;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "containerID", referencedColumnName = "containerID")
    private Container container;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "imageName", referencedColumnName = "imageName", insertable = false, updatable = false),
            @JoinColumn(name = "imageTag", referencedColumnName = "imageTag", insertable = false, updatable = false)
    })
    private DockerImages dockerImages;

    @Column(name = "serverName")
    private String serverName;

}