package com.akshatr.jobportal.model.dto.user;

import com.akshatr.jobportal.model.enums.UserRole;
import com.akshatr.jobportal.model.enums.UserStatus;
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
