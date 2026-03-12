package com.akshat.userService.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private Long id;
    private String username;
    private String name;
    private String token;
}
