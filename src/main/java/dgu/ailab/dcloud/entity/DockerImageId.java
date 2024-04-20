package dgu.ailab.dcloud.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Getter
@NoArgsConstructor
public class DockerImageId implements Serializable {
    public String imageName;
    public String imageTag;

    public DockerImageId(String imageName, String imageTag) {
        this.imageName = imageName;
        this.imageTag = imageTag;
    }
}