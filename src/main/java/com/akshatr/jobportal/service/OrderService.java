package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;

import java.util.List;

public interface OrderService {
    public OrderResponseDto createOrder(OrderRequestDto request);
    public OrderResponseDto getOrder(Long id);
    public List<OrderResponseDto> listOrders();
    public List<OrderResponseDto> search(OrderSearchDto request);

}
