package com.example.Kun_uz.entity;

import com.example.Kun_uz.enums.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_reaction")
public class ArticleLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "reaction")
    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @Column(name = "like_count")
    private Integer likeCount=0;
}
