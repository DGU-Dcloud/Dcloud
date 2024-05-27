package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.dto.CommentDto;
import dgu.ailab.dcloud.dto.PostDto;
import dgu.ailab.dcloud.entity.Comment;
import dgu.ailab.dcloud.entity.Post;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import dgu.ailab.dcloud.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PostController {
    private final PostService postService;

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

    // 댓글 삭제 구현해야 함.
    // 게시글 삭제 구현해야 함.
//    @DeleteMapping("/{postId}/comments/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
//        Comment comment = commentRepository.findById(commentId).orElse(null);
//        if (comment != null && comment.getPost().getPostID().equals(postId) && comment.getUser().getUserID().equals(userDetails.getUsername())) {
//            commentRepository.delete(comment);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
}
