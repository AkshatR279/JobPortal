package com.akshatr.jobportal.util;

import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.model.enums.UserRole;
import com.akshatr.jobportal.model.utilmodel.JWTClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private final Long expiration;

    public JWTUtil(@Value("${jwt.secret.key}") String secretKey, @Value("${jwt.token.expiration}") Long expiration){
        this.secretKey =  Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expiration = expiration;
    }

    public String generateToken(User user){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("Id", user.getId());
        claims.put("Role", user.getRole());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public JWTClaims decodeToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long id = claims.get("Id", Long.class);
        String roleName = claims.get("Role", String.class);
        UserRole role = UserRole.valueOf(roleName);

        return JWTClaims.builder()
                .id(id)
                .role(role)
                .issuedAt(claims.getIssuedAt())
                .expiration(claims.getExpiration())
                .build();
    }

}
