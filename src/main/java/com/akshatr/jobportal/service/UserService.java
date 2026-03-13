package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.user.UserResponseDto;

public interface UserService {
    public UserResponseDto findById(Long id);
}
