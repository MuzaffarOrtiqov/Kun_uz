package com.example.Kun_uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDTO {
    //title,description,content,image_id, region_id,category_id, articleType(array)

    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull(message = "ImageId is required")
    private String imageId;
    @NotNull(message = "RegionId is required")
    private Integer regionId;
    @NotNull(message = "CategoryId is required")
    private Integer categoryId;
    @NotNull(message = "TypeId is required")
    private List<Integer> types;
}
