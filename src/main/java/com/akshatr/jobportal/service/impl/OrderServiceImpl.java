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

        Order order = new Order();
        Optional<Order> existingOrder = orderRepository.findById(request.getId());
        if(existingOrder.isPresent()){
            order = existingOrder.get();;
        }

        order.setName("INV" + request.getOrderType().toString().substring(0,2) + System.currentTimeMillis());
        order.setOrderType(request.getOrderType());
        order.setOrderDate(request.getOrderDate());
        order.setCost(request.getCost());
        order.setTax(request.getTax());
        order.setOrderById(existingUser.getId());
        order.setPaid(0D);
        order.setStatus(OrderStatus.UNPAID);

        return orderRepository.save(order);
    }
}
