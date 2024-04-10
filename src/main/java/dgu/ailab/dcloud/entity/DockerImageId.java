package dgu.ailab.dcloud.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import lombok.*;

@Embeddable
@Data
@Getter
public class DockerImageId implements Serializable {
    public String imageName;
    public String imageTag;
}
