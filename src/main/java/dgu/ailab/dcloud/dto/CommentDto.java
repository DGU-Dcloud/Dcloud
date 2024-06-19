package dgu.ailab.dcloud.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentDto {
    private Integer commentId;
    private Integer postId;
    private Date createdAt;
    private String content;
    private String userId;
}
