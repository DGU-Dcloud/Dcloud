package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ServerDto;
import dgu.ailab.dcloud.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public List<ServerDto> getAllServers() {
        return serverRepository.findAll().stream()
                .map(server -> {
                    // 여기서는 Entity를 DTO로 변환하는 로직을 구현해야 합니다.
                    // 단순 예제로 직접 필드를 설정합니다. 실제 구현에서는 ModelMapper 등을 사용할 수 있습니다.
                    ServerDto dto = new ServerDto();
                    dto.setServerName(server.getServerName());
                    dto.setPublicIP(server.getPublicIP());
                    dto.setPublicSubnetMask(server.getPublicSubnetMask());
                    dto.setPrivateIP(server.getPrivateIP());
                    dto.setPrivateSubnetMask(server.getPrivateSubnetMask());
                    dto.setRamGB(server.getRamGB());
                    dto.setCpuName(server.getCpuName());
                    dto.setCpuCnt(server.getCpuCnt());
                    dto.setGpuName(server.getGpuName());
                    dto.setGpuCnt(server.getGpuCnt());
                    dto.setSsdGB(server.getSsdGB());
                    dto.setSshPort(server.getSshPort());
                    return dto;
                }).collect(Collectors.toList());
    }
}
