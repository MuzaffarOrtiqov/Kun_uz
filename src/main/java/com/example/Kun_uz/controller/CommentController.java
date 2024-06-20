package com.example.Kun_uz.controller;

import com.example.Kun_uz.Mapper.CommentMapper;
import com.example.Kun_uz.dto.Comment.CommentCreateDTO;
import com.example.Kun_uz.dto.Comment.CommentFilterDTO;
import com.example.Kun_uz.dto.Comment.CommentResponseDTO;
import com.example.Kun_uz.dto.Comment.CommentUpdateDTO;
import com.example.Kun_uz.dto.FilterResponseDTO;
import com.example.Kun_uz.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/comment")
@RestController
@Validated
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentCreateDTO comment) {
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @PutMapping("/update/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Integer commentId, @Valid @RequestBody CommentUpdateDTO comment) {
        return ResponseEntity.ok(commentService.updateComment(commentId, comment));

    }

    @DeleteMapping("/delete/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

    @GetMapping("/allCommentsForArticle/{articleId}")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<List<CommentMapper>> getAllCommentsForArticle(@PathVariable String articleId) {
        return ResponseEntity.ok(commentService.getAllCommentsForArticle(articleId));
    }


   /* @GetMapping("/test/{articleId}")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<List<CommentMapper>> newGetAllCommentsForArticle(@PathVariable String articleId) {
        return ResponseEntity.ok(commentService.newGetAllCommentsForArticle(articleId));
    }*/


    @GetMapping("/allCommentListPagination/{articleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<CommentMapper>> getAllCommentListPagination(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                           @RequestParam(name = "size", defaultValue = "3") int size,
                                                                           @PathVariable String articleId) {
        return ResponseEntity.ok(commentService.getAllCommentsWithDetails(page - 1, size, articleId));

    }

    @PostMapping("/allCommentListFilter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<CommentResponseDTO>> getCommentsFilter(@RequestBody CommentFilterDTO commentFilterDTO,
                                                                          @RequestParam(name = "page", defaultValue = "1") int page,
                                                                          @RequestParam(name = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(commentService.getAllCommentsWithFilter(commentFilterDTO, page - 1, size));

    }
    @GetMapping("/comment_reply_list/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_PUBLISH','ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<List<CommentResponseDTO>> getCommentReplyList(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.getCommentReplyList(commentId));
    }

}
