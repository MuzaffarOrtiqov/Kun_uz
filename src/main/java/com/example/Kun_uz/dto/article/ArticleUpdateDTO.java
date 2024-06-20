package com.example.Kun_uz.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//TODO Remember to write constraints
@Getter
@Setter
public class ArticleUpdateDTO {

    // title,description,content,shared_count,image_id, region_id,category_id
    @NotBlank(message = "Article id is required")
    private String articleId;
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
    private List<Integer> types;
}
