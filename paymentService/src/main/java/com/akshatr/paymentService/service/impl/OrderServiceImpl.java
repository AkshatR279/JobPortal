package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.model.dto.GeneralAPIResponse;
import com.akshatr.paymentService.model.dto.order.OrderResponseDto;
import com.akshatr.paymentService.service.OrderService;
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
    public OrderResponseDto getOrder(Long id) {
        if(id == null){
            throw new RuntimeException("Invalid user.");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://orderService/api/orders/" + id,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null || !apiResponse.getSuccess()){
            throw new RuntimeException("Failed to fetch order.");
        }

        OrderResponseDto order = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                OrderResponseDto.class
        );

        if(order == null){
            throw new RuntimeException("Failed to fetch order.");
        }

        return order;
    }
}
