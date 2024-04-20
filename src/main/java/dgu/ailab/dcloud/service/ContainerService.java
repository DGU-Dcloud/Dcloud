package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.controller.SignupController;
import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.ContainerRequest;
import dgu.ailab.dcloud.repository.ContainerRepository;
import dgu.ailab.dcloud.repository.ContainerRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerService {

    private final ContainerRepository containerRepository;
    private final ContainerRequestRepository containerRequestRepository;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    public ContainerService(ContainerRepository containerRepository, ContainerRequestRepository containerRequestRepository) {
        this.containerRepository = containerRepository;
        this.containerRequestRepository = containerRequestRepository;
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
                container.getNote(),
                container.getStatus()
        );
    }

    @Transactional
    public ContainerRequestDto insertContainerRequest(ContainerRequestDto containerRequestDto) {
        ContainerRequest containerRequest = containerRequestDto.toEntity();

        logger.info("toString() checkehck {}", containerRequest.toString());

        // Populate the container entity with dto data
        containerRequestRepository.save(containerRequest);

        return containerRequestDto; // return DTO, possibly update with id if necessary
    }
}