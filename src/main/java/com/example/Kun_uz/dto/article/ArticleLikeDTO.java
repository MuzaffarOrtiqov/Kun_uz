package com.example.Kun_uz.dto.article;

import com.example.Kun_uz.enums.Reaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {
    //    id,profile_id,article_id,created_date,status,
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private Reaction reaction;
    private Integer likeCount;
}
