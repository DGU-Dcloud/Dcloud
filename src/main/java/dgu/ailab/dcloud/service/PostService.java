package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.entity.Comment;
import dgu.ailab.dcloud.entity.Post;
import dgu.ailab.dcloud.repository.PostRepository;
import dgu.ailab.dcloud.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    // 모든 게시물을 불러오는 메서드
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 ID에 해당하는 포스트를 가져오는 메서드
    public Post getPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.orElse(null); // 만약 해당 ID에 해당하는 포스트가 없으면 null을 반환
    }

    // 특정 포스트에 대한 모든 댓글을 가져오는 메서드
    public List<Comment> getAllCommentsForPost(Long postId) {
        return commentRepository.findByPostPostID(postId);
    }

    // 새로운 댓글을 추가하는 메서드
    public Comment addCommentToPost(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            comment.setPost(post);
            return commentRepository.save(comment);
        }
        return null;
    }

    // 새 포스트 작성하기
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
}
