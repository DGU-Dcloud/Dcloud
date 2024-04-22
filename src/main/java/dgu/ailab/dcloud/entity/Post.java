package dgu.ailab.dcloud.entity;

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

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    // post를 참조하는 comments객체. post가 삭제되면, comment도 삭제됨을 나타내는 어노테이션.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments;

    public Post() {}
}

