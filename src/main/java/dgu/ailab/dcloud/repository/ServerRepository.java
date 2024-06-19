package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends JpaRepository<Server, String> {
    @Query("SELECT s.serverName FROM Server s WHERE s.publicIP = :host AND s.gpuName LIKE CONCAT('%', :gpuModel, '%')")
    List<String> findByPublicIPAndGpuNameContaining(@Param("host") String host, @Param("gpuModel") String gpuModel);

    Server findByServerName(String serverName);
}

