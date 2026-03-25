package com.akshatr.paymentService.model.dto.payment;

import com.akshatr.paymentService.model.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentSearchDto {
    public Long orderId;
    public Long userId;
    public PaymentStatus status;
}
