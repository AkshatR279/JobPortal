package com.akshat.userService.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class UserAuthResponse {
    private Boolean authenticated;
    private String message;
    private UserDetails userDetails;
}
