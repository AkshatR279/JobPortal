package com.akshatr.orderService.controller;

import com.akshatr.orderService.model.dto.GeneralAPIResponse;
import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.dto.order.OrderResponseDto;
import com.akshatr.orderService.model.entity.Order;
import com.akshatr.orderService.service.OrderService;
import com.akshatr.orderService.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ExceptionHandler exceptionHandler;

    @PostMapping("/save")
    public ResponseEntity<GeneralAPIResponse> saveOrder(@RequestBody OrderRequestDto request){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertOrderToResponseDto(orderService.saveOrder(request)))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
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
