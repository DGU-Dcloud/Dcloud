package dgu.ailab.dcloud.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class DockerImageId implements Serializable {
    private String imageName;
    private String imageTag;
}
