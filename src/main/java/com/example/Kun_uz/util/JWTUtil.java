package com.example.Kun_uz.util;


import com.example.Kun_uz.dto.auth.JwtDTO;
import com.example.Kun_uz.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JWTUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "very_long_mazgiskjdh2skjdhjbksdfkjsdbvjckDSVCkjdscvkjSCVXKJCVKJdcvk,jdbsfkVDBCDVSHLFHDSKBFVdsadasdasg7fgdfgdfd";

    public static String encode(Integer profileId, String username, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("username", username);
        jwtBuilder.claim("role", role);


        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("KunUzTest");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Integer id = (Integer) claims.get("id");
        String username = (String) claims.get("username");
        String role = (String) claims.get("role");

        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, username, profileRole);
        }
        return new JwtDTO(id);
    }


}
