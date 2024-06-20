package com.example.Kun_uz.dto.history;

import com.example.Kun_uz.enums.SmsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
// id, phone,message,status,type(if necessary),created_date,(used_date if necessary)
    private int id;
    private String phone;
    private String message;
    private SmsStatus status;
    private String type;
    private LocalDateTime createdDate;
}
