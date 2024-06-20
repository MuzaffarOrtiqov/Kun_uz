package com.example.Kun_uz.repository;

import com.example.Kun_uz.Mapper.ArticleShortInfoMapper;
import com.example.Kun_uz.entity.ArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity,String> {

    Optional<ArticleEntity> findByIdIs(String articleId);
  /*  @Transactional
    @Modifying  //id(uuid),title,description,image(id,url),published_date
    @Query(value = "select a.id, a.title from article  as a\n" +
            "inner join  article_type as at on a.id= at.article_id\n" +
            "order by a.created_date desc limit 5  ",nativeQuery = true)
    List<ArticleEntity> findLast5ByArticleTypes(Integer typesId);*/


    @Query(value = "select a.id,a.title,a.description,a.image_id,a.published_date from article as a " +
            " inner join article_type as at on a.id = at.article_id and at.types_id=:typesId and a.visible=true and a.status='PUBLISHED'" +
            "order by a.created_date desc LIMIT :limit", nativeQuery = true)

    List<ArticleShortInfoMapper> findLast5ByArticleTypes(@Param("typesId") Integer typesId, @Param("limit") int limit);


    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " where a.visible = true and a.status = 'PUBLISHED' " +
            " and a.id not in ?1 " +
            " order by a.createdDate desc " +
            " limit 8 ")
    List<ArticleShortInfoMapper> find8ArticlesNotIn(@Param("articleIds") List<String> articleIds);

    @Query(value = "SELECT a.id, a.title, a.description, a.imageId,a.publishedDate " +
            "FROM ArticleEntity AS a  " +
            "INNER JOIN ArticleTypesEntity as at ON a.id=at.articleId " +
            "WHERE at.typesId=?1 " +
            "AND a.id <> ?2 AND a.status='PUBLISHED' AND a.visible = true " +
            "ORDER BY a.createdDate DESC LIMIT 4")
    List<ArticleShortInfoMapper> find4ArticleByTypesExcludingOne(Integer typesId, String articleId);
}
