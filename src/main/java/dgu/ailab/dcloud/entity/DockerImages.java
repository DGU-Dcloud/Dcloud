package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "`DOCKER_IMAGES`")
@Getter
@Setter // Lombok 어노테이션을 사용하여 게터와 세터 메소드 생성
public class DockerImages {

    @EmbeddedId // 복합키라서 DockerImageID class를 새로 만듦.
    public DockerImageId id;

}
