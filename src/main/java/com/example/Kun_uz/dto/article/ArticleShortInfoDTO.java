package com.example.Kun_uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {
    //id(uuid),title,description,image(id,url),published_date

    private String id;
    private String title;
    private String description;
    private String imageId;
    private LocalDateTime publishDate;

}
