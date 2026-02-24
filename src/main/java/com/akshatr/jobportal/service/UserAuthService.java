package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthService {
    public UserDetails validateToken(String token);
    public UserDetails loadUserAuth(User user);
}
