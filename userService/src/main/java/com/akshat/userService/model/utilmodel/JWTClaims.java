package com.akshat.userService.model.utilmodel;

import com.akshat.userService.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JWTClaims {
    private Long id;
    private Date issuedAt;
    private Date expiration;
    private UserRole role;
}
