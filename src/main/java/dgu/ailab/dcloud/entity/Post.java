package dgu.ailab.dcloud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "`POST`")
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID")
    private Integer postID;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "imagePath", length = 500)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    // post를 참조하는 comments객체. post가 삭제되면, comment도 삭제됨을 나타내는 어노테이션.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments;

    // Report와의 관계 추가
    @OneToMany(mappedBy = "post")
    @JsonManagedReference // 순환 참조 해결을 위한 어노테이션. 관리측 참조를 나타냄. post쪽에서도 어노테이션을 추가함.
    private Set<Report> reports;

    public Post() {}
}