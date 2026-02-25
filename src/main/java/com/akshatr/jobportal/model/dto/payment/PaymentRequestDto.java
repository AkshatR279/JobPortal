package com.akshatr.jobportal.model.dto.payment;

import com.akshatr.jobportal.model.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long id;
    private String name;
    private Double amount;
    private PaymentMethod paymentMethod;
    private Long orderId;
}
