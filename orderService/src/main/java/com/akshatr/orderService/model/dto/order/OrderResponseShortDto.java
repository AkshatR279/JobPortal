package com.akshatr.orderService.model.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseShortDto {
    private Long id;
    private String name;
}
