package com.example.Kun_uz.Mapper;

import java.time.LocalDateTime;

public interface ArticleShortInfoMapper {
    //         id(uuid),title,description,image(id,url),published_date
    String getId();
    String getTitle();
    String getDescription();
    String getImageId();
    LocalDateTime getPublishedDate();

}
