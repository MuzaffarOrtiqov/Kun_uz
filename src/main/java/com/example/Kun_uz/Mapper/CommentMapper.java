package com.example.Kun_uz.Mapper;

import java.time.LocalDateTime;

public interface CommentMapper {
    /*        (id,created_date,update_date,profile(id,name,surname),content,article(id,title),reply_id,)
     */
    Integer getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
    Integer getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getContent();
    String getArticleId();
    String getArticleTitle();
    Integer getReplyId();


}
