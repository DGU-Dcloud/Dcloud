package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.CommentDto;
import dgu.ailab.dcloud.dto.PostDto;
import dgu.ailab.dcloud.entity.Comment;
import dgu.ailab.dcloud.entity.Post;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import dgu.ailab.dcloud.repository.CommentRepository;
import dgu.ailab.dcloud.repository.PostRepository;
import dgu.ailab.dcloud.repository.UserRepository;
import dgu.ailab.dcloud.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PostController {
    private final PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 포스트 가져오기
    @GetMapping("/posts")
    public List<PostDto> getAllPosts() {
        return postService.getAllPostss();
    }

    // 특정 포스트 가져오기
    @GetMapping("/posts/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 특정 포스트의 모든 댓글 가져오기
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsForPost(@PathVariable Long postId) {
        return postService.getAllCommentsForPost(postId);
    }

    // 새 포스트 작성하기
    @PostMapping("/create-post")
    public Post createPost(@RequestBody PostDto post, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
        }
        post.setUserId(userId);
        logger.info("POST: {}", post);
        return postService.createPost(post);
    }

    // 특정 포스트에 댓글 추가하기
    @PostMapping("/posts/{postId}/comments")
    public Comment addCommentToPost(@PathVariable Long postId, @RequestBody Comment comment, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = "";
        if (session != null) {
            userId = (String) session.getAttribute("userID");
        }

        return postService.addCommentToPost(postId, comment, userId);
    }

    // 댓글 삭제 기능.
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found");
        }

        String userId = (String) session.getAttribute("userID");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        String commentUserId = comment.getUser().getUserID();
        if (!userId.equals(commentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this comment");
        }

        // 위 검증을 다 거치고 이상 없다면? 댓글 삭제함.
        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }

    // 게시글 삭제 기능.
    @DeleteMapping("/postdelete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(401).body("Session not found");
        }

        String userId = (String) session.getAttribute("userID");
        if (userId == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        try {
            postService.deletePost(postId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

}
