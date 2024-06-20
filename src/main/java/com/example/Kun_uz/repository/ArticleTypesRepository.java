package com.example.Kun_uz.repository;

import com.example.Kun_uz.entity.ArticleTypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "select types_id from article_type where article_id=?1",nativeQuery = true)
    List<Integer> findTypesIdByArticleId(String articleId);

    void deleteByArticleIdAndTypesId(String articleId, Integer typesId);
}
