package com.akshat.userService.service;

import com.akshat.userService.model.dto.LoginRequestDto;
import com.akshat.userService.model.dto.UserRequestDto;
import com.akshat.userService.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> findById(Long id);
    public List<User> getUsers();
    public User saveUser(UserRequestDto request);
    public User login(LoginRequestDto request);
}
