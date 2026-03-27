package com.akshatr.paymentService.model.dto.integration;

import com.akshatr.paymentService.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentGatewayStatus {
    private String paymentNo;
    private PaymentStatus status;
}
