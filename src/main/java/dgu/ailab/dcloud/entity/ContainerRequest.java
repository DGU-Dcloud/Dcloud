package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "container_request")
@Getter
@Setter
@NoArgsConstructor // Lombok 어노테이션을 사용하여 기본 생성자 자동 생성
public class ContainerRequest {

    @Id
    @Column(name = "request_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "image_name", referencedColumnName = "imageName"),
            @JoinColumn(name = "image_tag", referencedColumnName = "imageTag")
    })
    private DockerImages dockerImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;  // User 엔티티와의 연결

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "department")
    private String department;

    @Column(name = "professor_name")
    private String professorName;

    @Column(name = "usage_description")
    private String usageDescription;

    @Column(name = "expected_expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expectedExpirationDate;

    @Column(name = "environment")
    private String environment;

    @Column(name = "gpu_model")
    private String gpuModel;

    @OneToMany(mappedBy = "containerRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Container> container;

    @Override
    public String toString() {
        return "ContainerRequest{" +
                "requestId=" + requestId +
                ", dockerImages=" + (dockerImages != null ? dockerImages.getId() : "null") +
                ", userId='" + user.getUserID() + '\'' +
                ", studentId='" + studentId + '\'' +
                ", department='" + department + '\'' +
                ", professorName='" + professorName + '\'' +
                ", usageDescription='" + usageDescription + '\'' +
                ", expectedExpirationDate=" + expectedExpirationDate +
                ", environment='" + environment + '\'' +
                ", gpuModel='" + gpuModel + '\'' +
                ", container=" + container +
                '}';
    }

}

