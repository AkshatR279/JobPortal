package com.akshat.userService.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponse {
    private Boolean authenticated;
    private String message;
    private UserDetailsDTO userDetails;
}
