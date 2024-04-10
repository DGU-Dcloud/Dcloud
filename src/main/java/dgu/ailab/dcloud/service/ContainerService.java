package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerService {

    private final ContainerRepository containerRepository;

    @Autowired
    public ContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public List<ContainerDto> getAllContainers() {
        // Container 엔티티 리스트를 ContainerDTO 리스트로 변환
        return containerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Container 엔티티를 ContainerDTO로 변환하는 메서드
    private ContainerDto convertToDTO(Container container) {
        return new ContainerDto(
                container.getContainerID(),
                container.getContainerName(),
                container.getDockerImages() != null ? container.getDockerImages().getId().getImageName() : null, // 수정된 접근 방식
                container.getDockerImages() != null ? container.getDockerImages().getId().getImageTag() : null,   // 수정된 접근 방식
                container.getServer() != null ? container.getServer().getServerName() : null,
                container.getSshPort(),
                container.getJupyterPort(),
                container.getCreatedAt(),
                container.getDeletedAt(),
                container.getNote()
        );
    }
}