package dgu.ailab.dcloud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "REPORT")
@Getter @Setter @NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportID", nullable = false)
    private Integer reportID;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "userid")
    private String userId;

    @Column(name = "department")
    private String department;

    @Column(name = "studentID")
    private String studentID;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "sshPort")
    private Integer sshPort;

    @Column(name = "why", length = 1000)
    private String why;

    @Column(name = "requirement", length = 2000)
    private String requirement;

    @Column(name = "isAnswered")
    private Boolean isAnswered;

    // Post와의 관계 추가
    @ManyToOne
    @JoinColumn(name = "postid")
    private Post post;
}
