package dgu.ailab.dcloud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer sshPort=-1;

    @Column(name = "why", length = 1000)
    private String why;

    @Column(name = "requirement", length = 2000)
    private String requirement;

    @Column(name = "isAnswered")
    private Boolean isAnswered;

    // Post와의 관계 추가
    @ManyToOne
    @JoinColumn(name = "postid")
    @JsonBackReference // 참조 당하는 쪽을 나타내는 어노테이션. 순환 참조 문제를 해결하기 위해 추가함.
    private Post post;
}
