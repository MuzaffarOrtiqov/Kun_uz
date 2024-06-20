package com.example.Kun_uz.repository;

import com.example.Kun_uz.Mapper.CommentMapper;
import com.example.Kun_uz.entity.CommentEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
    @Query(value = "SELECT  c.id as id , c.created_date as createdDate,c.updated_date as updatedDate , p.id  as profileId, p.name as profileName , p.surname as profileSurname FROM comment c \n" +
            " INNER JOIN profile p ON p.id=c.profile_id  \n" +
            " WHERE c.article_id=?1",nativeQuery = true)
    List<CommentMapper> getAllCommentsForArticle(String articleId);

/*    @Query(value = "SELECT  new com.example.Kun_uz.Mapper.CommentMapper2( c.id  , c.createdDate ,c.updatedDate  , p.id  , p.name  , p.surname)  FROM CommentEntity c \n" +
            " INNER JOIN ProfileEntity p ON p.id=c.profileId  \n" +
            " WHERE c.articleId=?1")
    List<CommentMapper> newGetAllCommentsForArticle(String articleId);*/

    @Query(value = "select c.id as id, c.created_date as createdDate, " +
            "c.updated_date as updatedDate,p.id as profileId,p.name as profileName ," +
            "p.surname as profileSurname, c.content as content, a.id as articleId,a.title as articleTitle," +
            " c.reply_id as replyId"  +
            " from comment as c " +
            " inner join  article as a on a.id = c.article_id " +
            " inner join profile as p on c.profile_id=p.id " +
            "where c.article_id =?1 ",nativeQuery = true)
    PageImpl<CommentMapper> findById(String articleId, Pageable pageable);

    @Query(value = "FROM CommentEntity WHERE replyId=?1")
    List<CommentEntity> getCommentReplyList(Integer commentId);
}
