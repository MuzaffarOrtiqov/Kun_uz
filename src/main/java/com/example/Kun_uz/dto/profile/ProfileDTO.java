package com.example.Kun_uz.dto.profile;

import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProfileDTO {
   // id,name,surname,email,phone,password,status,role,visible,created_date,photo_id
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDateTime createdDate;
    private Integer photoId;
    private  String Jwt;

 public ProfileDTO(Integer id, String name, String surname) {
  this.id = id;
  this.name = name;
  this.surname = surname;
 }
}
