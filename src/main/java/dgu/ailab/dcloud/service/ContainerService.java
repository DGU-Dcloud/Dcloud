package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.controller.SignupController;
import dgu.ailab.dcloud.dto.ContainerDto;
import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.ContainerRequest;
import dgu.ailab.dcloud.entity.DockerImageId;
import dgu.ailab.dcloud.entity.DockerImages;
import dgu.ailab.dcloud.repository.ContainerRepository;
import dgu.ailab.dcloud.repository.ContainerRequestRepository;
import dgu.ailab.dcloud.repository.DockerImagesRepository;
import dgu.ailab.dcloud.repository.ServerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContainerService {
    private final ServerService serverService;
    private final ContainerRepository containerRepository;
    private final ContainerRequestRepository containerRequestRepository;
    private final ServerRepository serverRepository;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    private final SshCommand sshCommand;
    private final DockerImagesRepository dockerImagesRepository;

    @Autowired
    public ContainerService(ServerService serverService, ContainerRepository containerRepository, ContainerRequestRepository containerRequestRepository, ServerRepository serverRepository, SshCommand sshCommand, DockerImagesRepository dockerImagesRepository) {
        this.serverService = serverService;
        this.containerRepository = containerRepository;
        this.containerRequestRepository = containerRequestRepository;
        this.serverRepository = serverRepository;
        this.sshCommand = sshCommand;
        this.dockerImagesRepository = dockerImagesRepository;
    }


    public List<ContainerRequestDto> findAllContainerRequests() {
        return containerRequestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
        containerRequest.setStatus("Pending");
        containerRequest.setCreatedAt(new Date()); // 현재 시간 설정
        // Populate the container entity with dto data
        containerRequestRepository.save(containerRequest);

        return containerRequestDto; // return DTO, possibly update with id if necessary
    }


    public List<ContainerRequestDto> getContainerRequestStatus(String userId) {
        List<ContainerRequest> containerRequests = containerRequestRepository.findByUserUserID(userId);
        return containerRequests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ContainerRequestDto convertToDto(ContainerRequest containerRequest) {
        // 여기에 ContainerRequest를 ContainerRequestDto로 변환하는 로직 구현
        return new ContainerRequestDto(
                containerRequest.getRequestId(),
                containerRequest.getDepartment(),
                containerRequest.getEnvironment(),
                containerRequest.getExpectedExpirationDate(),
                containerRequest.getGpuModel(),
                containerRequest.getProfessorName(),
                containerRequest.getStudentId(),
                containerRequest.getUsageDescription(),
                containerRequest.getUser() != null ? containerRequest.getUser().getUserID() : null,
                containerRequest.getDockerImages() != null ? containerRequest.getDockerImages().getId().getImageName() : null,
                containerRequest.getDockerImages() != null ? containerRequest.getDockerImages().getId().getImageTag() : null,
                containerRequest.getStatus(),
                containerRequest.getCreatedAt()
        );
    }

    @Transactional
    public void applyRequests(List<Integer> ids) { // admin이 컨테이너 요청을 apply한 경우
        logger.info("im applyRequests");
        String username = "mingyun"; // Use 'svmanager' in production
        String password = "alsrbs1212"; // Use environment-specific secure storage for passwords in production

        containerRequestRepository.updateStatusByIds(ids, "Approved");
        for(Integer i : ids){
            ContainerRequest request = containerRequestRepository.findByRequestId(i);
            String host="";
            String serverName="";
            if ("LAB".equals(request.getEnvironment())) {
                host = "210.94.179.18";
                serverName="LAB";
            } else if ("FARM".equals(request.getEnvironment())) {
                host = "210.94.179.19";
                serverName="FARM";
            }

            // 어떤 서버에 배정할 것인지, port에 담는다. 8081~8089
            int port = findPortByGpuModel(request.getGpuModel(),host);
            Random random = new Random();

            serverName+=port%10;
            logger.info("{}",serverName);

            // 여분의 포트 2개를 가져온다.
            int emptyPort1=findAvailablePort(host, port); //
            int emptyPort2=findAvailablePort(host, port) +1;

            String command="sudo docker run -dit --gpus '\"device=all\"' --memory=128g --memory-swap=128g -p ";
            command+=emptyPort1+":22 -p "+emptyPort2+":8888"
                    +" -it --runtime=nvidia --cap-add=SYS_ADMIN --ipc=host --mount type=bind,source=\"/home/tako"
                    + port % 10 +"/share/user-share/\",target=/home/ --name "
                    +request.getUser().getUserID()+emptyPort1+"_dcloud"+"  -e USER_ID="
                    +request.getUser().getUserID()+" -e USER_PW=ailab2260 -e USER_GROUP=default -e UID="
                    +(random.nextInt(65000 - 2000 + 1) + 2000) +" dguailab/"
                    +request.getDockerImages().getId().getImageName()+":"
                    +request.getDockerImages().getId().getImageTag();

            logger.info("{}",command);

            // sshCommand 실행 결과를 boolean 변수에 저장
            boolean commandExecutionResult = sshCommand.executeCommand(host, port, username, password, command);

            if (commandExecutionResult) {
                // 컨테이너 정보 저장
                Container container = new Container();

                String imageName = request.getDockerImages().getId().getImageName();
                String imageTag = request.getDockerImages().getId().getImageTag();
                DockerImageId dockerImageId = new DockerImageId(imageName, imageTag);
                DockerImages dockerImages = dockerImagesRepository.findById(dockerImageId)
                        .orElseThrow(() -> new IllegalArgumentException("Docker image not found with id: " + dockerImageId));
                container.setDockerImages(dockerImages);
                container.setServer(serverRepository.findByServerName(serverName)); // 서버 정보 설정
                container.setUser(request.getUser());
                container.setCreatedAt(new Date());
                container.setContainerName(request.getUser().getUserID() +emptyPort1+ "_dcloud");
                container.setSshPort(emptyPort1);
                container.setJupyterPort(emptyPort2);
                container.setNote("Created by applyRequests");
                container.setStatus("active");
                container.setContainerRequest(request);
                container.setDeletedAt(request.getExpectedExpirationDate());
                logger.info("{}",container);
                containerRepository.save(container);
            } else {
                logger.error("Container creation failed for request: {}", request.getRequestId());
            }
        }
    }

    /*
    *
    * sudo docker run -dit --gpus '"device=all"' --memory=128g --memory-swap=128g -p 9349:22 -p 9350:8888 -it --runtime=nvidia --cap-add=SYS_ADMIN --ipc=host --mount type=bind,source="/home/tako4/share/user-share/",target=/home/ --name zio_jy_from7  -e USER_ID=zio -e USER_PW=ailab2260 -e USER_GROUP=default -e UID=$UID_VALUE dguailab/decs:1.4.18
    *
    *
    * */


    public void denyRequests(List<Integer> ids) {  // admin이 컨테이너 요청을 deny한 경우
        containerRequestRepository.updateStatusByIds(ids, "Rejected");
    }
    public void pendingRequests(List<Integer> ids) {  // admin이 컨테이너 요청을 pending으로 돌려놓고자 할 경우
        containerRequestRepository.updateStatusByIds(ids, "Pending");
    }

    public int findPortByGpuModel(String gpuModel,String host) { // 어디 서버에 생성할지 정의하는 부분.
        int defaultPort = 8080; // 기본 SSH 포트
        List<String> servers = serverService.findByPublicIPAndGpuNameContaining(host, gpuModel);
        logger.info("{}", servers.toString());

        int containerCount=987654321;
        String minContainerServer="";
        for (String serverName : servers) { // 유저가 희망하는 GPU Model가 장착된 서버 가운데, 컨테이너가 가장 적은 서버를 찾는다.
            if( containerCount >= containerRepository.countByServer_ServerName(serverName) ){
                containerCount = containerRepository.countByServer_ServerName(serverName);
                minContainerServer=serverName;
            }
        }

//        logger.info("{}", minContainerServer);
//        logger.info("{}", containerCount);
//        logger.info("{}", defaultPort);

        defaultPort+=minContainerServer.charAt(minContainerServer.length() - 1) - '0'; // LAB8이 가장 작은 서버라면, 8을 defaultPort에 더해준다.

        return defaultPort;
    }

    private int findAvailablePort(String host, int port) {
        int basePort = 9000 + (port - 8081) * 100; // 랩1이면 9000부터.. 이런식임. 랩2면 9100부터..
        List<Object[]> usedPorts;

        if (host.equals("210.94.179.18")) {
            usedPorts = containerRepository.findUsedPortsByServerNameContaining("LAB");
        } else if (host.equals("210.94.179.19")) {
            usedPorts = containerRepository.findUsedPortsByServerNameContaining("FARM");
        } else {
            throw new IllegalArgumentException("Invalid host: " + host);
        }

        Set<Integer> allUsedPorts = new HashSet<>();
        for (Object[] ports : usedPorts) {
            allUsedPorts.add((Integer) ports[0]); // sshPort
            allUsedPorts.add((Integer) ports[1]); // jupyterPort
        }

        for (int i = basePort; i < basePort + 100; i++) {
            if (!allUsedPorts.contains(i)) {
                return i;
            }
        }

        // 사용 가능한 포트를 찾지 못한 경우 예외 처리
        throw new RuntimeException("Available port not found for host: " + host);
    }


    public List<ContainerDto> getActiveContainer(String userId) {
        List<Container> activeContainers = containerRepository.findByUser_UserIDAndStatus(userId, "active");
        return activeContainers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}