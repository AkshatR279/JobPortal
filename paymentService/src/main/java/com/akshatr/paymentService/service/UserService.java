package com.akshatr.paymentService.service;

import com.akshatr.paymentService.model.dto.user.UserResponseDto;

public interface UserService {
    public UserResponseDto findById(Long id);
}
