package com.akshatr.orderService.controller;

import com.akshatr.orderService.model.dto.GeneralAPIResponse;
import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.dto.order.OrderResponseDto;
import com.akshatr.orderService.model.dto.order.OrderSearchDto;
import com.akshatr.orderService.model.entity.Order;
import com.akshatr.orderService.service.OrderService;
import com.akshatr.orderService.service.cache.CacheService;
import com.akshatr.orderService.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ExceptionHandler exceptionHandler;
    private final CacheService cache;

    private static final String CACHE_NAME = "ORDER";

    @PostMapping("/create")
    public ResponseEntity<GeneralAPIResponse> createOrder(@RequestBody OrderRequestDto request){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertOrderToResponseDto(orderService.createOrder(request)))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getOrder(@PathVariable Long id){
        try{
            OrderResponseDto order = (OrderResponseDto) cache.get(CACHE_NAME, id.toString());
            if(order==null){
                order = convertOrderToResponseDto(orderService.getOrder(id));
                cache.add(CACHE_NAME, id.toString(), order);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(order)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> listOrders(){
        try{
            List<OrderResponseDto> orders = (List<OrderResponseDto>) cache.get(CACHE_NAME, "ALL");
            if(orders==null){
                orders = orderService.listOrders().stream()
                        .map(this::convertOrderToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, "ALL", orders);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(orders)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<GeneralAPIResponse> search(@RequestBody OrderSearchDto request){
        try{
            StringBuilder searchString = new StringBuilder();
            searchString.append("SEARCH?");
            if(request.getUserId() > 0){
                searchString.append("U=").append(request.getUserId().toString());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
            if(request.getFrom() != null){
                searchString.append("DF=").append(dateFormat.format(request.getFrom()));
            }
            if(request.getTo() != null){
                searchString.append("DT=").append(dateFormat.format(request.getTo()));
            }

            if(request.getStatus() != null){
                searchString.append("S=").append(request.getStatus().toString());
            }

            List<OrderResponseDto> orders = (List<OrderResponseDto>) cache.get(CACHE_NAME, searchString.toString());
            if(orders==null){
                orders = orderService.search(request).stream()
                        .map(this::convertOrderToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, searchString.toString(), orders);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(orders)
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
