package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.user.LoginRequestDto;
import com.akshatr.jobportal.model.dto.user.UserRequestDto;
import com.akshatr.jobportal.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> findById(Long id);
    public List<User> getUsers();
    public User saveUser(UserRequestDto request);
    public User login(LoginRequestDto request);
}
