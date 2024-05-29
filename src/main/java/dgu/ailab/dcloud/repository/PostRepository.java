package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    void deleteByPostID(Long postId);
}
