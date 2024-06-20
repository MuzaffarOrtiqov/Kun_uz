package com.example.Kun_uz.dto.Comment;

import com.example.Kun_uz.enums.Reaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReactionCreateDTO {
    @NotNull(message = "CommentId is required")
    private Integer commentId;
    @NotNull(message = "Profile Id is required")
    private Integer profileId;
    @NotNull(message = "Reaction is required")
    @Valid
    private Reaction reaction;
}
