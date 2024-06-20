package com.example.Kun_uz.dto.article;

import com.example.Kun_uz.enums.Reaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ArticleLikeCreateDTO {
    @NotBlank(message = "ArticleId is required")
    private String articleId;
    @NotNull(message = "profileId is required")
    private Integer profileId;
    @NotNull(message = "Reaction is required")
    @Valid
    private Reaction reaction;
}
