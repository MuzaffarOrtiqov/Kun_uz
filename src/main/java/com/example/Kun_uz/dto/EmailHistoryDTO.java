package com.example.Kun_uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    //    id, message, email, created_data
    private Integer id;
    private String message;
    private String email;
    private LocalDateTime createdDate;


}
