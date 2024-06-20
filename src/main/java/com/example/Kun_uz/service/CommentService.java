package com.example.Kun_uz.service;

import com.example.Kun_uz.Mapper.CommentMapper;
import com.example.Kun_uz.dto.Comment.CommentCreateDTO;
import com.example.Kun_uz.dto.Comment.CommentFilterDTO;
import com.example.Kun_uz.dto.Comment.CommentResponseDTO;
import com.example.Kun_uz.dto.Comment.CommentUpdateDTO;
import com.example.Kun_uz.dto.FilterResponseDTO;
import com.example.Kun_uz.dto.article.ArticleDTO;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.entity.CommentEntity;
import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.CommentCustomRepository;
import com.example.Kun_uz.repository.CommentRepository;
import com.example.Kun_uz.util.JWTUtil;
import com.example.Kun_uz.util.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentCustomRepository commentCustomRepository;


    public CommentResponseDTO createComment(CommentCreateDTO comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(comment.getContent());
        commentEntity.setArticleId(comment.getArticleId());
        commentEntity.setReplyId(comment.getReplyId());
        commentEntity.setProfileId(SecurityUtil.getProfileId());
        commentRepository.save(commentEntity);
        return toDTO(commentEntity);
    }

    public CommentResponseDTO toDTO(CommentEntity comment) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setContent(comment.getContent());
        return commentResponseDTO;
    }

    public CommentResponseDTO updateComment(Integer commentId, CommentUpdateDTO comment) {
        CommentEntity commentEntity = getCommentById(commentId);
        if (!SecurityUtil.getProfileId().equals(commentEntity.getProfileId())) {
            throw new AppBadException("You can only update your comment");
        }
        commentEntity.setContent(comment.getContent());
        commentEntity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(commentEntity);
        return toDTO(commentEntity);
    }

    public CommentEntity getCommentById(Integer commentId) {
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new AppBadException("Comment not found");
        }
        return optional.get();
    }

    public CommentResponseDTO deleteComment(Integer commentId) {
        CommentEntity commentEntity = getCommentById(commentId);
        ProfileEntity profile = SecurityUtil.getProfile();
        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            if (!SecurityUtil.getProfileId().equals(commentEntity.getProfileId())) {
                throw new AppBadException("You can only delete your comment");
            }
            commentRepository.deleteById(commentId);
        }
        commentRepository.deleteById(commentId);
        return toDTO(commentEntity);
    }

    public List<CommentMapper> getAllCommentsForArticle(String articleId) {
        List<CommentMapper> mapperList = commentRepository.getAllCommentsForArticle(articleId);
        if (mapperList.isEmpty()) {
            throw new AppBadException("No comments found for article");
        }
        return mapperList;
    }


    public Page<CommentMapper> getAllCommentsWithDetails(int page, int size, String articleId) {
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<CommentMapper> commentEntityPage = commentRepository.findById(articleId, pageable);
        Long total = commentEntityPage.getTotalElements();
        List<CommentMapper> comments = commentEntityPage.getContent();
        return new PageImpl<>(comments, pageable, total);
    }

    public PageImpl<CommentResponseDTO> getAllCommentsWithFilter(CommentFilterDTO filterDTO, int page, int size) {
        FilterResponseDTO<CommentEntity> filter = commentCustomRepository.filter(filterDTO, page, size);
        List<CommentResponseDTO> dtoList = new LinkedList<>();
        for (CommentEntity commentEntity : filter.getContent()) {
            CommentResponseDTO dto = new CommentResponseDTO();
            /*    id,created_date,update_date,profile_id,content,article_id,reply_id,visible*/
            dto.setId(commentEntity.getId());
            dto.setCreatedDate(commentEntity.getCreatedDate());
            dto.setUpdatedDate(commentEntity.getUpdatedDate());

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(commentEntity.getProfileId());
            dto.setProfile(profileDTO);

            dto.setContent(commentEntity.getContent());

            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(commentEntity.getArticleId());
            dto.setArticle(articleDTO);

            dto.setReplyId(commentEntity.getReplyId());
            dto.setVisible(commentEntity.getVisible());
            dtoList.add(dto);

        }
        return new PageImpl<>(dtoList, PageRequest.of(page, size), filter.getTotalCount());
    }


    public List<CommentResponseDTO> getCommentReplyList(Integer commentId) {
        List<CommentEntity> commentEntityList = commentRepository.getCommentReplyList(commentId);
        if (commentEntityList.isEmpty()){
            throw new AppBadException("No comments found for article");
        }
        List<CommentResponseDTO> dtoList = new LinkedList<>();
        commentEntityList.forEach(comment -> {
            if(comment.getVisible().equals(false)){
                throw new AppBadException("Comment is not present");
            }
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
