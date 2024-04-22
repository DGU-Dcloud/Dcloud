package dgu.ailab.dcloud.controller;

import dgu.ailab.dcloud.entity.Comment;
import dgu.ailab.dcloud.entity.Post;
import dgu.ailab.dcloud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 포스트 가져오기
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // 특정 포스트 가져오기
    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 특정 포스트의 모든 댓글 가져오기
    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getAllCommentsForPost(@PathVariable Long postId) {
        return postService.getAllCommentsForPost(postId);
    }

    // 새 포스트 작성하기
    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    // 특정 포스트에 댓글 추가하기
    @PostMapping("/posts/{postId}/comments")
    public Comment addCommentToPost(@PathVariable Long postId, @RequestBody Comment comment) {
        return postService.addCommentToPost(postId, comment);
    }
}
