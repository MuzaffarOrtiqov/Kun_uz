package com.example.Kun_uz.dto.Comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentFilterDTO {
/*    id,created_date_from,created_date_to,profile_id,article_id*/
    private Integer id;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
    private Integer profileId;
    private String articleId;

}
