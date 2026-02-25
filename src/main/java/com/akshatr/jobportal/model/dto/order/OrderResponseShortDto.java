package com.akshatr.jobportal.model.dto.order;

import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.enums.OrderStatus;
import com.akshatr.jobportal.model.enums.OrderType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderResponseShortDto {
    private Long id;
    private String name;
}
