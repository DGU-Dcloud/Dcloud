package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_roleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserUserID(String userId);
}
