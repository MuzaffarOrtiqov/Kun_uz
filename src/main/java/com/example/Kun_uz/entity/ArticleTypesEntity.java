package com.example.Kun_uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
    @Column(name = "types_id")
    private Integer typesId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "types_id", insertable = false, updatable = false)
    private TypesEntity types;

}
