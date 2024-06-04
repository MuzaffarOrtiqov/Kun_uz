package com.example.Kun_uz.util;

import com.example.Kun_uz.dto.auth.JwtDTO;
import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.exp.AppForbiddenException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

    public static JwtDTO getJwtDTO(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        String username = (String) request.getAttribute("username");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id,username,role);
        return dto;
    }
    public static JwtDTO getJwtDTO(HttpServletRequest request, ProfileRole requiredRole) {
        Integer id = (Integer) request.getAttribute("id");
        String username = (String) request.getAttribute("username");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id,username, role);

        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }


}
