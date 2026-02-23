package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.user.LoginRequestDto;
import com.akshatr.jobportal.model.dto.user.UserRequestDto;
import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.repository.UserRepository;
import com.akshatr.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(UserRequestDto request) {
        User user = new User();
        Optional<User> existingUser = userRepository.findById(request.getId());
        if(existingUser.isPresent()){
            user = existingUser.get();
        }

        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequestDto request) {
        Optional<User> loginUser = userRepository.findByUsernamePassword(request.getUsername(), request.getPassword());
        if(loginUser.isEmpty()){
            throw new RuntimeException("Invalid username or password.");
        }

        User user = loginUser.get();
        return user;
    }
}
