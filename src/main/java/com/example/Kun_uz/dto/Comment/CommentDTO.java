package com.example.Kun_uz.dto.Comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
/*  id,created_date,update_date,profile_id,content,article_id,reply_id,visible*/
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer profileId;
    private String content;
    private String articleId;
    private Integer replyId;
    private Boolean visible;
}
