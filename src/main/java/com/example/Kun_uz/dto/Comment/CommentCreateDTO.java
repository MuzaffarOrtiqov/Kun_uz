package com.example.Kun_uz.dto.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    // (content,article_id,reply_id)
    @NotBlank(message = "Conetent is required")
    private String content;
    @NotBlank(message = "Article Id cant be blank")
    private String articleId;
    @NotNull(message = "ReplyId is required")
    private Integer replyId;

}
