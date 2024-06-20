package com.example.Kun_uz.repository;


import com.example.Kun_uz.entity.CommentReactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentReactionRepository extends CrudRepository<CommentReactionEntity,Integer> {

    @Query(value = "FROM CommentReactionEntity AS c WHERE c.commentId=?1 AND c.profileId=?2")
    Optional<CommentReactionEntity> findByCommentIdAndProfileId(Integer commentId, Integer profileId);

}
