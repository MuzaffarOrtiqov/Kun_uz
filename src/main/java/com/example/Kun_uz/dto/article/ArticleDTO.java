package com.example.Kun_uz.dto.article;

import com.example.Kun_uz.entity.CategoryEntity;
import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.entity.RegionEntity;
import com.example.Kun_uz.entity.TypesEntity;
import com.example.Kun_uz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ArticleDTO {
    //    id(uuid),title,description,content,shared_count,image_id,
    //    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    //    created_date,published_date,visible,view_count
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer sharedCount;
    private String imageId;
    private RegionEntity region;
    private CategoryEntity category;
    private ProfileEntity moderator;
    private ProfileEntity publisher;
    private List<Integer> types;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewsCount;

    public ArticleDTO(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
