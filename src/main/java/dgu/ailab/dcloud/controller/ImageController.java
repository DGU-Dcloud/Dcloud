package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.DockerImageDto;
import dgu.ailab.dcloud.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

// ImageController는 컨테이너 요청 폼에 이미지를 나타내기 위한 컨트롤러 입니다.

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<DockerImageDto>> getAllDockerImages() {
        List<DockerImageDto> images = imageService.getAllDockerImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{imagePath}")
    public ResponseEntity<Resource> getImage(@PathVariable String imagePath) throws IOException {
        Path filePath = Paths.get(uploadPath, imagePath);
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            throw new IOException("File not found " + imagePath);
        }

        String contentType = Files.probeContentType(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
