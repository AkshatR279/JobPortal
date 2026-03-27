package com.akshatr.paymentService.model.dto.payment;

import com.akshatr.paymentService.model.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long id;
    private String name;
    private Double amount;
    private PaymentMethod paymentMethod;
    private Long userId;
    private Long orderId;
}
