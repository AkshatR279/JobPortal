package com.akshatr.paymentService.model.dto.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentLinkDto {
    private String paymentGatewayLink;
}
