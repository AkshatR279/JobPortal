package com.akshat.userService.service.impl;

import com.akshat.userService.exceptions.BadRequestException;
import com.akshat.userService.model.dto.LoginRequestDto;
import com.akshat.userService.model.dto.UserRequestDto;
import com.akshat.userService.model.entity.User;
import com.akshat.userService.repository.UserRepository;
import com.akshat.userService.service.UserService;
import com.akshat.userService.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found.");
        }

        return user.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(UserRequestDto request) {
        Optional<User> duplicateUser = userRepository.findByUsername(request.getName());
        if(duplicateUser.isPresent()){
            throw new BadRequestException("User with provided username already exists.");
        }

        User user = new User();
        Optional<User> existingUser = userRepository.findById(request.getId());
        if(existingUser.isPresent()){
            user = existingUser.get();
        }

        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequestDto request) {
        Optional<User> loginUser = userRepository.findByUsername(request.getUsername());
        if(loginUser.isEmpty()){
            throw new BadRequestException("No user found with the provided username.");
        }

        User user = loginUser.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid password.");
        }

        user.setToken(jwtUtil.generateToken(user));
        return userRepository.save(user);
    }
}
