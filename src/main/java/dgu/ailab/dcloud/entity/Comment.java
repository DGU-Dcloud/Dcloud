package dgu.ailab.dcloud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "`COMMENT`")
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Integer commentID;

    @ManyToOne
    @JoinColumn(name = "postID")
    @JsonBackReference
    private Post post;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    public Comment() {}
}

