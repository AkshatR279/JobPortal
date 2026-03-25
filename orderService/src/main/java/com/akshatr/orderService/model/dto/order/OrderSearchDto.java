package com.akshatr.orderService.model.dto.order;

import com.akshatr.orderService.model.enums.OrderStatus;
import lombok.Data;

import java.util.Date;

@Data
public class OrderSearchDto {
    private Long userId;
    private Date from;
    private Date to;
    private OrderStatus status;
}
