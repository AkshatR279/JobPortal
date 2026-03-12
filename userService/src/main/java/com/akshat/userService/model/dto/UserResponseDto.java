package com.akshat.userService.model.dto;

import com.akshat.userService.model.enums.UserRole;
import com.akshat.userService.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String password;
    private UserRole role;
    private UserStatus status;
    private Date createdOn;
    private Date lastUpdatedOn;
}
