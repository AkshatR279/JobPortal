package com.akshatr.paymentService.model.dto.integration;

import com.akshatr.paymentService.model.enums.PaymentGateway;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentGatewayWebhook {
    private PaymentGateway paymentGateway;
    private Object webhookData;
}
