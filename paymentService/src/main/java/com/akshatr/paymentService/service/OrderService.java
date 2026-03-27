package com.akshatr.paymentService.service;

import com.akshatr.paymentService.model.dto.order.OrderResponseDto;
import com.akshatr.paymentService.model.dto.user.UserResponseDto;

public interface OrderService {
    public OrderResponseDto getOrder(Long id);
}
