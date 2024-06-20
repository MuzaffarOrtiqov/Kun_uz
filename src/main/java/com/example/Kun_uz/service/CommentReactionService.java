package com.example.Kun_uz.service;

import com.example.Kun_uz.dto.Comment.CommentReactionCreateDTO;
import com.example.Kun_uz.entity.ArticleLikeEntity;
import com.example.Kun_uz.entity.CommentReactionEntity;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.CommentReactionRepository;
import com.example.Kun_uz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentReactionService {
    @Autowired
    private CommentReactionRepository commentReactionRepository;

    public void likeComment(CommentReactionCreateDTO dto) {
        Integer userId = SecurityUtil.getProfileId();
        if (userId != dto.getProfileId()) {
            throw new AppBadException("To like this comment, you must be logged in");
        }
        Optional<CommentReactionEntity> optional = commentReactionRepository.findByCommentIdAndProfileId(dto.getCommentId(), dto.getProfileId());
        if (optional.isEmpty()) {  // if not have reaction,  set a reaction
            CommentReactionEntity commentReactionEntity = new CommentReactionEntity();
            commentReactionEntity.setCommentId(dto.getCommentId());
            commentReactionEntity.setProfileId(dto.getProfileId());
            commentReactionEntity.setReaction(dto.getReaction());
            commentReactionEntity.setCreatedDate(LocalDateTime.now());
            commentReactionRepository.save(commentReactionEntity);
            return;
        }
        CommentReactionEntity commentReactionEntity = optional.get();
        if (commentReactionEntity.getReaction().equals(dto.getReaction())) {
            commentReactionRepository.delete(commentReactionEntity);  //delete reaction
            return;
        }
        commentReactionEntity.setReaction(dto.getReaction());   //set new reaction for a user
        commentReactionEntity.setCreatedDate(LocalDateTime.now());
        commentReactionRepository.save(commentReactionEntity);
    }

}
