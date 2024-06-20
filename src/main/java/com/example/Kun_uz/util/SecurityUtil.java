package com.example.Kun_uz.util;

import com.example.Kun_uz.config.CustomUserDetail;
import com.example.Kun_uz.dto.auth.JwtDTO;
import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.exp.AppForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public static Integer getProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile().getId();
    }

    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile();
    }



}
