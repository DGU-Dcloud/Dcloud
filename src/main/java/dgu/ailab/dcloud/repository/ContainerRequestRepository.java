package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.ContainerRequest;
import dgu.ailab.dcloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRequestRepository extends JpaRepository<ContainerRequest, Long> {
    // 필요한 경우 여기에 쿼리 메소드를 추가할 수 있습니다.
    List<ContainerRequest> findByUserUserID(String userID);
}
