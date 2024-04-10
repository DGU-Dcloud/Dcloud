package dgu.ailab.dcloud.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContainerDto {

    private Integer containerID;
    private String containerName;
    private String imageName;
    private String imageTag;
    private String serverName;
    private Integer sshPort;
    private Integer jupyterPort;
    private Date createdAt;
    private Date deletedAt;
    private String note;

    // 생성자, getter, setter는 Lombok을 사용하여 자동으로 생성됩니다.
    // 필요에 따라 커스텀 생성자를 추가하여 엔티티로부터 DTO를 쉽게 생성할 수 있습니다.
    public ContainerDto() {
    }

    public ContainerDto(Integer containerID, String containerName, String imageName, String imageTag,
                        String serverName, Integer sshPort, Integer jupyterPort, Date createdAt,
                        Date deletedAt, String note) {
        this.containerID = containerID;
        this.containerName = containerName;
        this.imageName = imageName;
        this.imageTag = imageTag;
        this.serverName = serverName;
        this.sshPort = sshPort;
        this.jupyterPort = jupyterPort;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.note = note;
    }

}
