package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserID(String userId);

    User findByUserID(String userID);
}
