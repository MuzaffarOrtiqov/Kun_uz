package com.example.Kun_uz.entity;

import com.example.Kun_uz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    private String id= UUID.randomUUID().toString();
    @Column(name = "title",columnDefinition = "text")
    private String title;
    @Column(name = "descrption", columnDefinition = "text")
    private String description;
    @Column(name = "content", columnDefinition = "text")
    private String content;
    @Column(name = "shared_count")
    private Integer sharedCount=0;
    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status=ArticleStatus.NOT_PUBLISHED;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "visible")
    private Boolean visible=true;
    @Column(name = "view_count")
    private Integer viewCount=0;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @OneToMany(mappedBy = "article")
    private List<ArticleTypesEntity> articleTypes;




}
