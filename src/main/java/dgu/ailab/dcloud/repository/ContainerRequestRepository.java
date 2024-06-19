package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.dto.ContainerRequestDto;
import dgu.ailab.dcloud.entity.Container;
import dgu.ailab.dcloud.entity.ContainerRequest;
import dgu.ailab.dcloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface ContainerRequestRepository extends JpaRepository<ContainerRequest, Long> {
    // 필요한 경우 여기에 쿼리 메소드를 추가할 수 있습니다.
    List<ContainerRequest> findByUserUserID(String userID);

    @Modifying
    @Transactional
    @Query("UPDATE ContainerRequest cr SET cr.status = :status WHERE cr.requestId IN :ids")
    void updateStatusByIds(List<Integer> ids, String status);

    ContainerRequest findByRequestId(Integer i);
}
