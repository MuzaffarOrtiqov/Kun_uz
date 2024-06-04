package com.example.Kun_uz.dto.auth;

import com.example.Kun_uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {

    private Integer id;
    private String username;
    private ProfileRole role;

    public JwtDTO(Integer id,String username, ProfileRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public JwtDTO(Integer id) {
        this.id = id;
    }




}
