package com.example.Kun_uz.repository;

import com.example.Kun_uz.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    @Query(value = "FROM ArticleLikeEntity AS al WHERE al.articleId=?1 AND al.profileId=?2")
    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Query(value = "select * from ",nativeQuery = true)
    void incrementReactionCount(ArticleLikeEntity articleLikeEntity);
}
