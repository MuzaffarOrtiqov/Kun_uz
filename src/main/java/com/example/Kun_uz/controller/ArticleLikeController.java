package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.article.ArticleLikeCreateDTO;
import com.example.Kun_uz.service.ArticleLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/article")
@RestController
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/reaction")
    public ResponseEntity likeArticle(@Valid @RequestBody ArticleLikeCreateDTO  dto) {
        articleLikeService.likeArticle(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Success");

    }

}
