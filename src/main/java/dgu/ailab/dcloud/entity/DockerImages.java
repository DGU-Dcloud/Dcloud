package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity // 이 파일이 JPA Entity 파일임을 나타냄.
@Table(name = "`DOCKER_IMAGES`") // 이 엔티티가 매핑될 데이터베이스의 테이블 이름을 지정함.
@Getter
@Setter // Lombok 어노테이션을 사용하여 게터와 세터 메소드 생성
public class DockerImages {

    @EmbeddedId // 복합키를 사용하는 엔티티의 ID를 나타냄.
    public DockerImageId id;

    @Column(name = "os_version", length = 50)
    private String osVersion;

    @Column(name = "tf_version", length = 50)
    private String tfVersion;

    @Column(name = "cuda_version", length = 50)
    private String cudaVersion;


    @OneToMany(mappedBy = "dockerImages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContainerRequest> containerRequest;
}


