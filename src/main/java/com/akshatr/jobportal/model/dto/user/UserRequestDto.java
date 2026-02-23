package com.akshatr.jobportal.model.dto.user;

import com.akshatr.jobportal.model.enums.UserRole;
import com.akshatr.jobportal.model.enums.UserStatus;
import lombok.Data;

@Data
public class UserRequestDto {
    private Long id;
    private String name;
    private String password;
    private UserRole role;
    private UserStatus status;
}
