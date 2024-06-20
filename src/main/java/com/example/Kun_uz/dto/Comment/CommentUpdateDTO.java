package com.example.Kun_uz.dto.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {
    //(content,article_id)
    @NotBlank(message = "Content cant be blank")
    private String content;

}
