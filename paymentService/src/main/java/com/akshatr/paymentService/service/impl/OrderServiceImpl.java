package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.model.dto.GeneralAPIResponse;
import com.akshatr.paymentService.model.dto.user.UserResponseDto;
import com.akshatr.paymentService.service.OrderService;
import com.akshatr.paymentService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;

    @Override
    public UserResponseDto findById(Long id) {
        if(id == null){
            throw new RuntimeException("Invalid user.");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://userService/api/users/" + id,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null || !apiResponse.getSuccess()){
            throw new RuntimeException("Failed to fetch user.");
        }

        UserResponseDto userResponseDto = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                UserResponseDto.class
        );

        if(userResponseDto == null){
            throw new RuntimeException("Failed to fetch user.");
        }

        return userResponseDto;
    }
}
