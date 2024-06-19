package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.DockerImageDto;
import dgu.ailab.dcloud.entity.DockerImages;
import dgu.ailab.dcloud.repository.ContainerRepository;
import dgu.ailab.dcloud.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<DockerImageDto> getAllDockerImages() {
        // Container 엔티티 리스트를 ContainerDTO 리스트로 변환
        return imageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // DockerImages 엔티티를 DockerImageDto로 변환
    public DockerImageDto convertToDto(DockerImages dockerImages) {
        DockerImageDto dto = new DockerImageDto(
                dockerImages.getId().getImageName(),
                dockerImages.getId().getImageTag(),
                dockerImages.getOsVersion(),
                dockerImages.getTfVersion(),
                dockerImages.getCudaVersion()
        );
        return dto;
    }
}
