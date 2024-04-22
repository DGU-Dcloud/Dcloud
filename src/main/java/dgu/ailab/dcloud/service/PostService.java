package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.entity.*;
import dgu.ailab.dcloud.repository.PostRepository;
import dgu.ailab.dcloud.repository.CommentRepository;
import dgu.ailab.dcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    // 모든 게시물을 불러오는 메서드
    public List<Post> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();

        // "notice" 카테고리를 제일 위에 위치시키기
        List<Post> noticePosts = allPosts.stream()
                .filter(post -> "notice".equalsIgnoreCase(post.getCategory()))
                .collect(Collectors.toList());

        // "notice" 카테고리를 제외한 게시물을 날짜 순으로 정렬하기
        List<Post> otherPosts = allPosts.stream()
                .filter(post -> !"notice".equalsIgnoreCase(post.getCategory()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        // "notice" 카테고리를 제일 위에 위치시킨 후, 나머지 게시물을 추가하여 전체 리스트 반환
        List<Post> sortedPosts = new ArrayList<>(noticePosts);
        sortedPosts.addAll(otherPosts);

        return sortedPosts;
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
    public class PostCreationFailedException extends RuntimeException {
        public PostCreationFailedException(String message) {
            super(message);
        }
    }

    // 새 포스트 작성하기
    public Post createPost(Post post, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new PostCreationFailedException("Failed to create post. User not found.");
        } else if (!isManager(user)) {
            throw new PostCreationFailedException("Failed to create post. User is not a manager.");
        } else {
            // 현재 날짜 설정
            post.setCreatedAt(new Date());
            // 사용자 설정
            //post.setUser(user);
            return postRepository.save(post);
        }
    }


    // 사용자의 역할이 'manager'인지 확인하는 메서드
    private boolean isManager(User user) {
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if ("manager".equalsIgnoreCase(role.getRoleName())) {
                return true;
            }
        }
        return false;
    }

}
