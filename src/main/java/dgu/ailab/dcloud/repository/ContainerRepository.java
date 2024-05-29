package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    // 필요한 경우 여기에 쿼리 메소드를 추가할 수 있습니다.
    int countByServer_ServerName(String serverName);
    @Query("SELECT c.sshPort FROM Container c WHERE c.server.serverName LIKE %:serverName%")
    List<Integer> findUsedPortsByServerNameContaining(@Param("serverName") String serverName);
    List<Container> findByUser_UserIDAndStatus(String userId, String status);

}
