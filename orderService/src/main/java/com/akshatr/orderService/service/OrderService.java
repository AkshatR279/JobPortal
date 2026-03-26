package com.akshatr.orderService.service;

import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.dto.order.OrderSearchDto;
import com.akshatr.orderService.model.entity.Order;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequestDto request);
    public Order getOrder(Long id);
    public List<Order> listOrders();
    public List<Order> search(OrderSearchDto request);
}
