package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;
import com.akshatr.jobportal.model.dto.user.UserResponseDto;
import com.akshatr.jobportal.model.entity.Order;
import com.akshatr.jobportal.model.enums.OrderStatus;
import com.akshatr.jobportal.repository.OrderRepository;
import com.akshatr.jobportal.service.OrderService;
import com.akshatr.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    public List<Order> listOrders(OrderSearchDto request) {
        Long orderBy = request.getUserId() != null ? request.getUserId() : 0;
        return orderRepository.listOrders(orderBy);
    }

    @Override
    public Order save(OrderRequestDto request) {
        UserResponseDto existingUser = userService.findById(request.getOrderById());
        if(existingUser == null){
            throw new RuntimeException("Invalid user.");
        }


    }
}
