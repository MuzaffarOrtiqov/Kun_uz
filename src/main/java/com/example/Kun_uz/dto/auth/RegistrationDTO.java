package com.example.Kun_uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    
}
