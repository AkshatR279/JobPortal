package com.akshatr.jobportal.model.dto.user;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
