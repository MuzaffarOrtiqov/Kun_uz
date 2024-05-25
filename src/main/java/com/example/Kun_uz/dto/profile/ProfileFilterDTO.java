package com.example.Kun_uz.dto.profile;

import com.example.Kun_uz.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDTO {
    //name,surname,phone,role,created_date_from,created_date_to
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDateTime createdDateFrom ;
    private LocalDateTime createdDateTo ;
}
