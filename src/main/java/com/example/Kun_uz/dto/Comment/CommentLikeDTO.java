package com.example.Kun_uz.dto.Comment;

import com.example.Kun_uz.enums.Reaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    //    id,profile_id,comment_id,created_date,status,
    private Integer id;
    private Integer profileId;
    private Integer commentId;
    private LocalDateTime createdDate;
    private Reaction reaction;
}
