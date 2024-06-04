package com.example.Kun_uz.util;

import com.example.Kun_uz.dto.auth.JwtDTO;
import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.exp.AppForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityUtil {
    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        JwtDTO dto = JWTUtil.decode(jwt);
        return dto;
    }
    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if(!dto.getRole().equals(requiredRole)){
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }



}
