package com.example.Kun_uz.dto.Comment;

import com.example.Kun_uz.dto.article.ArticleDTO;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    private String content;

    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProfileDTO profile;
    private ArticleDTO article;
    private Integer replyId;
    private Boolean visible;


}
