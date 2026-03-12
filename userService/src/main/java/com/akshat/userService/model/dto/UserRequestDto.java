package com.akshat.userService.model.dto;

import com.akshat.userService.model.enums.UserRole;
import com.akshat.userService.model.enums.UserStatus;
import lombok.Data;

@Data
public class UserRequestDto {
    private Long id;
    private String name;
    private String password;
    private UserRole role;
    private UserStatus status;
}
