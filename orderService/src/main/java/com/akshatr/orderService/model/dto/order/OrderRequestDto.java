package com.akshatr.orderService.model.dto.order;

import com.akshatr.orderService.model.enums.OrderType;
import lombok.Data;

import java.util.Date;

@Data
public class OrderRequestDto {
    private Long id;
    private String name;
    private OrderType orderType;
    private Date orderDate;
    private Double cost;
    private Double tax;
    private Long orderById;
}
