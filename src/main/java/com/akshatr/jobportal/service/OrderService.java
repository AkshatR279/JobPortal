package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;
import com.akshatr.jobportal.model.entity.Order;

import java.util.List;

public interface OrderService {
    public List<Order> listOrders(OrderSearchDto request);
    public Order save(OrderRequestDto request);
}
