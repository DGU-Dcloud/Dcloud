package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.dto.CommentDto;
import dgu.ailab.dcloud.entity.Comment;
import dgu.ailab.dcloud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
