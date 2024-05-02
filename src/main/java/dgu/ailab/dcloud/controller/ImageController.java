package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.DockerImageDto;
import dgu.ailab.dcloud.service.ContainerService;
import dgu.ailab.dcloud.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

// ImageController는 컨테이너 요청 폼에 이미지를 나타내기 위한 컨트롤러 입니다.

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<DockerImageDto>> getAllDockerImages() {
        List<DockerImageDto> images = imageService.getAllDockerImages();
        return ResponseEntity.ok(images);
    }
}
