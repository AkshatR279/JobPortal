package com.akshat.userService.service;

import com.akshat.userService.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthService {
    public UserDetails validateToken(String token);
    public UserDetails loadUserAuth(User user);
}
