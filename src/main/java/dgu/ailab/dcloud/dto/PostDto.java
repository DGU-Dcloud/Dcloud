package dgu.ailab.dcloud.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private Integer postID;
    private String category;
    private String title;
    private String content;
    private String userId;
    private Date createdAt;
    private String imagePath;


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
