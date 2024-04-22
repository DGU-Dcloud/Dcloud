package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    // 필요한 경우 여기에 쿼리 메소드를 추가할 수 있습니다.
}
