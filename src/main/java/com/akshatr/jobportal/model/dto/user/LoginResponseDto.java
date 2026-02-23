package com.akshatr.jobportal.model.dto.user;

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
