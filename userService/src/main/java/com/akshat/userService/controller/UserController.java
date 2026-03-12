package com.akshat.userService.controller;

import com.akshat.userService.model.dto.LoginRequestDto;
import com.akshat.userService.model.dto.LoginResponseDto;
import com.akshat.userService.model.dto.UserRequestDto;
import com.akshat.userService.model.dto.UserResponseDto;
import com.akshat.userService.model.entity.User;
import com.akshat.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers(){
        return userService.getUsers().stream()
                .map(this::convertUserToResponseDto)
                .toList();
    }

    @PostMapping("/save")
    public UserResponseDto saveUser(@RequestBody UserRequestDto request){
        return convertUserToResponseDto(userService.saveUser(request));
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request){
        return convertUserToLoginResponseDto(userService.login(request));
    }

    private UserResponseDto convertUserToResponseDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .status(user.getStatus())
                .createdOn(user.getCreatedOn())
                .lastUpdatedOn(user.getLastUpdatedOn())
                .build();
    }

    private LoginResponseDto convertUserToLoginResponseDto(User user){
        return LoginResponseDto.builder()
                .id(user.getId())
                .username(user.getName())
                .name(user.getName())
                .token(user.getToken())
                .build();
    }
}
