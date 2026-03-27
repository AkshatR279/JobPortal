package com.akshatr.paymentService.model.dto.payment;

import com.akshatr.paymentService.model.dto.order.OrderResponseShortDto;
import com.akshatr.paymentService.model.enums.PaymentMethod;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentResponseDto {
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdatedOn;
    private Double amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private Long orderId;
    private Long userId;
}
