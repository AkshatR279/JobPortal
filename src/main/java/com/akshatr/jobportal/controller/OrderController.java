package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.order.OrderRequestDto;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.order.OrderSearchDto;
import com.akshatr.jobportal.model.entity.Order;
import com.akshatr.jobportal.service.OrderService;
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

    @PostMapping
    public List<OrderResponseDto> listOrders(@RequestBody OrderSearchDto request){
        return orderService.listOrders(request).stream()
                .map(this::convertOrderToResponseDto)
                .toList();
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
                .orderBy(dtoConvertor.convertToUserShort(order.getOrderBy()))
                .build();
    }
}
