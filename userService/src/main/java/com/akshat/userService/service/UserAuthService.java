package com.akshat.userService.service;

import com.akshat.userService.model.dto.UserDetailsDTO;

public interface UserAuthService {
    public UserDetailsDTO validateToken(String token);
}
