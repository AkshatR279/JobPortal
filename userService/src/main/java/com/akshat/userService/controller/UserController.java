package com.akshat.userService.controller;

import com.akshat.userService.exceptions.BadRequestException;
import com.akshat.userService.model.dto.*;
import com.akshat.userService.model.entity.User;
import com.akshat.userService.service.UserAuthService;
import com.akshat.userService.service.UserService;
import com.akshat.userService.util.ExceptionHandler;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAuthService userAuthService;
    private final ExceptionHandler exceptionHandler;

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertUserToResponseDto(userService.findById(id)))
                            .build()
            );
        }
        catch (RuntimeException e){
            return exceptionHandler.convertExceptionToResponse(e);
        }
    }

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> getUsers(){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(userService.getUsers().stream()
                                    .map(this::convertUserToResponseDto)
                                    .toList())
                            .build()
            );
        }
        catch (RuntimeException e){
            return exceptionHandler.convertExceptionToResponse(e);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralAPIResponse> saveUser(@RequestBody UserRequestDto request){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertUserToResponseDto(userService.saveUser(request)))
                            .build()
            );
        }
        catch (RuntimeException e){
            return exceptionHandler.convertExceptionToResponse(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralAPIResponse> login(@RequestBody LoginRequestDto request){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertUserToLoginResponseDto(userService.login(request)))
                            .build()
            );
        }
        catch (RuntimeException e){
            return exceptionHandler.convertExceptionToResponse(e);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<GeneralAPIResponse> validateToken(@RequestBody UserAuthRequest request){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(UserAuthResponse.builder()
                                    .authenticated(true)
                                    .message("Token validated successfully")
                                    .userDetails(userAuthService.validateToken(request.getToken()))
                                    .build())
                            .build()
            );
        }
        catch (RuntimeException e){
            return exceptionHandler.convertExceptionToResponse(e);
        }
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
