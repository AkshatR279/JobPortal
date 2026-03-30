package com.akshatr.paymentService.model.dto.payment;

import com.akshatr.paymentService.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentEvent {
    public Long id;
    public String name;
    private Double amount;
    private PaymentStatus status;
    private Long orderId;
    private Long userId;
}
