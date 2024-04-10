package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
@CrossOrigin
public class ContainerController {

    private final ContainerService containerService;

    @Autowired
    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping
    public ResponseEntity<List<ContainerDto>> getAllContainers() {
        List<ContainerDto> containers = containerService.getAllContainers();
        return ResponseEntity.ok(containers);
    }
}
