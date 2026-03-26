package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.exceptions.BadRequestException;
import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;
import com.akshatr.jobportal.model.dto.user.UserResponseDto;
import com.akshatr.jobportal.service.OrderService;
import com.akshatr.jobportal.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;
    private final UserService userService;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {
        UserResponseDto existingUser = userService.findById(request.getOrderById());
        if(existingUser == null){
            throw new BadRequestException("Invalid user.");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.postForEntity(
                "http://orderService/api/orders/create",
                request,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to create order.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        OrderResponseDto orderResponseDto = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                OrderResponseDto.class
        );

        if(orderResponseDto == null){
            throw new RuntimeException("Failed to create order.");
        }

        return orderResponseDto;
    }

    @Override
    public OrderResponseDto getOrder(Long id) {
        if(id == null){
            throw new BadRequestException("Order not found.");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://orderService/api/orders/" + id.toString(),
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Order not found.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        OrderResponseDto orderResponseDto = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                OrderResponseDto.class
        );

        if(orderResponseDto == null){
            throw new RuntimeException("Order not found.");
        }

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> listOrders() {
        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://orderService/api/orders",
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to fetch orders.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        List<OrderResponseDto> orders = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                new TypeReference<List<OrderResponseDto>>() {}
        );

        if(orders == null){
            throw new RuntimeException("Failed to fetch orders.");
        }

        return orders;
    }

    @Override
    public List<OrderResponseDto> search(OrderSearchDto request) {
        ResponseEntity<GeneralAPIResponse> response = restTemplate.postForEntity(
                "http://orderService/api/orders/search",
                request,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to fetch orders.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        List<OrderResponseDto> orders = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                new TypeReference<List<OrderResponseDto>>() {}
        );

        if(orders == null){
            throw new RuntimeException("Failed to fetch orders.");
        }

        return orders;
    }

}
