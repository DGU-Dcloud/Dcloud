package dgu.ailab.dcloud.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DockerImageDto {

    private String imageName;
    private String imageTag;
    private String osVersion;
    private String tfVersion;
    private String cudaVersion;

    // 기본 생성자
    public DockerImageDto() {}

    // 매개변수가 있는 생성자
    public DockerImageDto(String imageName, String imageTag, String osVersion, String tfVersion, String cudaVersion) {
        this.imageName = imageName;
        this.imageTag = imageTag;
        this.osVersion = osVersion;
        this.tfVersion = tfVersion;
        this.cudaVersion = cudaVersion;
    }

}
