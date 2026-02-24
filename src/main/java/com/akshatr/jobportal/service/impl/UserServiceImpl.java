package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.user.LoginRequestDto;
import com.akshatr.jobportal.model.dto.user.UserRequestDto;
import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.repository.UserRepository;
import com.akshatr.jobportal.service.UserService;
import com.akshatr.jobportal.util.JWTUtil;
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
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequestDto request) {
        Optional<User> loginUser = userRepository.findByUsername(request.getUsername());
        if(loginUser.isEmpty()){
            throw new RuntimeException("No user found with the provided username.");
        }

        User user = loginUser.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password.");
        }

        user.setToken(jwtUtil.generateToken(user));
        return userRepository.save(user);
    }
}
