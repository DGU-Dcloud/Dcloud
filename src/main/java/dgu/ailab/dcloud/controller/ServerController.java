package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.ServerDto;
import dgu.ailab.dcloud.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@CrossOrigin
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public List<ServerDto> getAllServers() {
        return serverService.getAllServers();
    }
}
