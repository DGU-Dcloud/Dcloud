package dgu.ailab.dcloud.dto;

import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.User;
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
    private String status;
    private String requestID;

    // 생성자, getter, setter는 Lombok을 사용하여 자동으로 생성됩니다.
    // 필요에 따라 커스텀 생성자를 추가하여 엔티티로부터 DTO를 쉽게 생성할 수 있습니다.
    public ContainerDto() {
    }

    public ContainerDto(Integer containerID, String containerName, String imageName, String imageTag,
                        String serverName, Integer sshPort, Integer jupyterPort, Date createdAt,
                        Date deletedAt, String note, String status) {
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
        this.status = status;
    }

    public Container toEntity() {
        Container container = new Container();
        container.setContainerID(this.containerID); // ID는 보통 DB에서 생성되므로 설정할 필요가 없을 수 있습니다.
        container.setContainerName(this.containerName);
        // Assume imageName and imageTag are used to fetch a DockerImages entity
        // Assume serverName is used to fetch a Server entity
        container.setCreatedAt(this.createdAt);
        container.setDeletedAt(this.deletedAt);
        container.setSshPort(this.sshPort);
        container.setJupyterPort(this.jupyterPort);
        container.setNote(this.note);
        container.setStatus(this.status);
        return container;
    }

}
