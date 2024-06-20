package com.example.Kun_uz.service;

import com.example.Kun_uz.dto.article.ArticleLikeCreateDTO;
import com.example.Kun_uz.entity.ArticleLikeEntity;
import com.example.Kun_uz.enums.Reaction;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.ArticleLikeRepository;
import com.example.Kun_uz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public void likeArticle(ArticleLikeCreateDTO dto) {
        Integer userId = SecurityUtil.getProfileId();
        if (userId != dto.getProfileId()) {
            throw new AppBadException("To like this article, you must be logged in");
        }
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(dto.getArticleId(), dto.getProfileId());
        if (optional.isEmpty()) {  // if not have reaction,  set a reaction
            ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();
            articleLikeEntity.setArticleId(dto.getArticleId());
            articleLikeEntity.setProfileId(dto.getProfileId());
            articleLikeEntity.setReaction(dto.getReaction());
            articleLikeEntity.setCreatedDate(LocalDateTime.now());
  //          articleLikeRepository.incrementReactionCount(articleLikeEntity);
            articleLikeRepository.save(articleLikeEntity);
            return;
        }
        ArticleLikeEntity articleLikeEntity = optional.get();
        if (articleLikeEntity.getReaction().equals(dto.getReaction())) {
            articleLikeRepository.delete(articleLikeEntity);  //delete reaction
            return;
        }
        articleLikeEntity.setReaction(dto.getReaction());   //set new reaction for a user
        articleLikeEntity.setCreatedDate(LocalDateTime.now());
        articleLikeRepository.save(articleLikeEntity);
    }
}
