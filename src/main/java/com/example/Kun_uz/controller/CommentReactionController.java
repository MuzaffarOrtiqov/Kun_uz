package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.Comment.CommentReactionCreateDTO;
import com.example.Kun_uz.dto.article.ArticleLikeCreateDTO;
import com.example.Kun_uz.service.CommentReactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RestController
public class CommentReactionController {
    @Autowired
    private CommentReactionService commentReactionService;


    @PostMapping("/reaction")
    public ResponseEntity likeComment(@Valid @RequestBody CommentReactionCreateDTO dto) {
        commentReactionService.likeComment(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Success");

    }

}
