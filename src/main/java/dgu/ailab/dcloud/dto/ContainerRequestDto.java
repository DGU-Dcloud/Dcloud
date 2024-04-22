package dgu.ailab.dcloud.dto;

import dgu.ailab.dcloud.entity.ContainerRequest;
import dgu.ailab.dcloud.entity.DockerImageId;
import dgu.ailab.dcloud.entity.DockerImages;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.repository.UserRepository;
import lombok.Data;
import java.util.Date;

@Data
public class ContainerRequestDto {

    UserRepository userRepository;

    private Integer requestId;
    private String department;
    private String environment;
    private Date expectedExpirationDate;
    private String gpuModel;
    private String professorName;
    private String studentId;
    private String usageDescription;
    private String userId;
    private String imageName;
    private String imageTag;
    private String status;  // 추가된 필드
    private Date createdAt; // 추가된 필드

    // 기본 생성자
    public ContainerRequestDto() {
    }

    // 모든 필드를 포함한 생성자
    public ContainerRequestDto(Integer requestId, String department, String environment, Date expectedExpirationDate,
                               String gpuModel, String professorName, String studentId, String usageDescription,
                               String userId, String imageName, String imageTag, String status, Date createdAt) {
        this.requestId = requestId;
        this.department = department;
        this.environment = environment;
        this.expectedExpirationDate = expectedExpirationDate;
        this.gpuModel = gpuModel;
        this.professorName = professorName;
        this.studentId = studentId;
        this.usageDescription = usageDescription;
        this.userId = userId;
        this.imageName = imageName;
        this.imageTag = imageTag;
        this.status = status;  // 추가된 필드
        this.createdAt = createdAt; // 추가된 필드
    }


    // DTO를 Entity로 변환하는 메소드
    public ContainerRequest toEntity() {
        ContainerRequest containerRequest = new ContainerRequest();
        // Set scalar properties
        containerRequest.setStudentId(this.studentId);
        containerRequest.setDepartment(this.department);
        containerRequest.setProfessorName(this.professorName);
        containerRequest.setUsageDescription(this.usageDescription);
        containerRequest.setExpectedExpirationDate(this.expectedExpirationDate);
        containerRequest.setEnvironment(this.environment);
        containerRequest.setGpuModel(this.gpuModel);
        containerRequest.setStatus(this.status); // 추가된 필드
        containerRequest.setCreatedAt(this.createdAt); // 추가된 필드

        // Fetch and set the associated user
        if (this.userId != null) {
            User user=new User();
            user.setUserID(this.userId);
            containerRequest.setUser(user);
        }

        // Set the DockerImages association if necessary
        if (this.imageName != null && this.imageTag != null) {
            DockerImages dockerImages = new DockerImages();
            DockerImageId id = new DockerImageId(this.imageName, this.imageTag);
            dockerImages.setId(id);
            containerRequest.setDockerImages(dockerImages);
        }

        return containerRequest;
    }


    // Lombok의 @Data 어노테이션은 getter와 setter 메서드를 자동으로 생성합니다.
    // 추가적으로 equals(), hashCode() 및 toString() 메서드도 제공합니다.
}
