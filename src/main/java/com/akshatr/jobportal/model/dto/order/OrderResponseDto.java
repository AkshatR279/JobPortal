package com.akshatr.jobportal.model.dto.order;

import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.enums.OrderStatus;
import com.akshatr.jobportal.model.enums.OrderType;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class OrderResponseDto {
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdatedOn;
    private OrderType orderType;
    private Date orderDate;
    private Double cost;
    private Double tax;
    private Double paid;
    private OrderStatus status;
    private UserResponseShortDto orderBy;
}
