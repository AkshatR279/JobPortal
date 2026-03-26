package com.akshatr.orderService.service.impl;

import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.entity.Order;
import com.akshatr.orderService.repository.OrderRepository;
import com.akshatr.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderRequestDto request) {
        Order order = new Order();
        Optional<Order> existingOrder = orderRepository.findById(request.getId());
        if(existingOrder.isPresent()){
            order = existingOrder.get();;
        }
        else{

        }



        order.setName("INV" + request.getOrderType().toString().substring(0,2) + System.currentTimeMillis());
        order.setOrderType(request.getOrderType());
        order.setOrderDate(request.getOrderDate());
        order.setCost(request.getCost());
        order.setTax(request.getTax());
        order.setOrderById(request.getOrderById());
        order.setPaid(0D);
        order.setStatus(OrderStatus.UNPAID);

        return orderRepository.save(order);
    }
}
