package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.CommentDto;
import dgu.ailab.dcloud.dto.PostDto;
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
    public List<PostDto> getAllPostss() {
        List<Post> allPosts = postRepository.findAll();

        // "notice" 카테고리를 제일 위에 위치시키기
        List<PostDto> noticePosts = allPosts.stream()
                .filter(post -> "notice".equalsIgnoreCase(post.getCategory()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // "notice" 카테고리를 제외한 게시물을 날짜 순으로 정렬하기
        List<PostDto> otherPosts = allPosts.stream()
                .filter(post -> !"notice".equalsIgnoreCase(post.getCategory()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // "notice" 카테고리를 제일 위에 위치시킨 후, 나머지 게시물을 추가하여 전체 리스트 반환
        List<PostDto> sortedPosts = new ArrayList<>(noticePosts);
        sortedPosts.addAll(otherPosts);

        return sortedPosts;
    }

    public PostDto convertToDTO(Post post) {
        if (post == null) {
            return null;
        }

        PostDto dto = new PostDto();
        dto.setPostID(post.getPostID());
        dto.setCategory(post.getCategory());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUser().getUserID()); // Assuming User entity has a getUserID method
        dto.setCreatedAt(post.getCreatedAt());

        return dto;
    }

    // 특정 ID에 해당하는 포스트를 가져오는 메서드
    public PostDto getPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            return convertToDTO(postOptional.get());
        } else {
            return null; // 만약 해당 ID에 해당하는 포스트가 없으면 null을 반환
        }
    }

    // 특정 포스트에 대한 모든 댓글을 가져오는 메서드
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }
        List<Comment> comments = commentRepository.findByPost(post);
        return convertToDTO(comments);
    }

    // List<Comment>를 List<CommentDto>로 변환하는 메서드
    public List<CommentDto> convertToDTO(List<Comment> comments) {
        if (comments == null) {
            return null;
        }

        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 단일 Comment를 CommentDto로 변환하는 메서드
    public CommentDto convertToDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentID());
        dto.setPostId(comment.getPost().getPostID());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getUserID());

        return dto;
    }

    // 새로운 댓글을 추가하는 메서드
    public Comment addCommentToPost(Long postId, Comment comment, String userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findByUserID(userId);


        if (post != null) {
            comment.setPost(post);
            comment.setCreatedAt(new Date());
            comment.setUser(user);
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
    public Post createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId()).orElse(null);

        if (user == null) {
            throw new PostCreationFailedException("Failed to create post. User not found.");
        } else if (!isManager(user)) {
            throw new PostCreationFailedException("Failed to create post. User is not a manager.");
        } else {
            // 현재 날짜 설정
            postDto.setCreatedAt(new Date());
            // PostDto를 Post 엔티티로 변환하여 저장
            Post post = convertToEntity(postDto, user);
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

    private Post convertToEntity(PostDto postDto, User user) {
        Post post = new Post();
        post.setCategory(postDto.getCategory());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUser(user);
        return post;
    }
}
