package com.example.Kun_uz.dto.article;

import com.example.Kun_uz.dto.category.CategoryDTO;
import com.example.Kun_uz.dto.region.RegionDTO;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class ArticleResponseDTO {
    //   id(uuid),title,description,image(id,url),published_date
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private RegionDTO region;
    private CategoryDTO category;
    private ProfileDTO moderator;
    private ProfileDTO publisher;
    private List<Integer> types;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewsCount;
    private Integer LikeCount;
    private Integer sharedCount;
    private String content;
}
