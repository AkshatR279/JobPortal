package com.akshatr.orderService.service;

import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.entity.Order;

public interface OrderService {
    public Order saveOrder(OrderRequestDto request);
}
