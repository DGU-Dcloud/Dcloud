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

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    public Post() {}
}

