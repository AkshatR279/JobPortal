package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.user.UserResponseDto;
import com.akshatr.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    @Override
    public UserResponseDto findById(Long id) {
        if(id == null){
            throw new RuntimeException("Invalid user.");
        }
        return restTemplate.getForObject(
                "http://userService/vi/users/" + id,
                UserResponseDto.class
        );
    }
}
