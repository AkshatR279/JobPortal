package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;
import com.akshatr.jobportal.model.entity.Order;
import com.akshatr.jobportal.service.OrderService;
import com.akshatr.jobportal.service.cache.CacheService;
import com.akshatr.jobportal.util.DtoConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final DtoConvertor dtoConvertor;
    private final CacheService cache;

    private static final String CACHE_NAME = "ORDER";

    @PostMapping
    public List<OrderResponseDto> listOrders(@RequestBody OrderSearchDto request){
        List<OrderResponseDto> orders = (List<OrderResponseDto>) cache.get(CACHE_NAME, "ALL:" + request.getUserId().toString());
        if(orders == null){
            orders = orderService.listOrders(request).stream()
                .map(this::convertOrderToResponseDto)
                .toList();
            cache.add(CACHE_NAME, "ALL:" + request.getUserId(), orders);
        }

        return orders;
    }

    @PostMapping("/save")
    public OrderResponseDto save(@RequestBody OrderRequestDto request){
        return convertOrderToResponseDto(orderService.save(request));
    }

    private OrderResponseDto convertOrderToResponseDto(Order order){
        return OrderResponseDto.builder()
                .id(order.getId())
                .name(order.getName())
                .createdOn(order.getCreatedOn())
                .lastUpdatedOn(order.getLastUpdatedOn())
                .orderDate(order.getOrderDate())
                .orderType(order.getOrderType())
                .cost(order.getCost())
                .tax(order.getTax())
                .paid(order.getPaid())
                .status(order.getStatus())
                .orderById(order.getOrderById())
                .build();
    }
}
