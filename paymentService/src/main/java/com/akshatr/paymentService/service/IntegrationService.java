package com.akshatr.paymentService.service;

import com.akshatr.paymentService.model.dto.integration.PaymentGatewayWebhook;

public interface IntegrationService {
    public void confirmPaymentStatus(PaymentGatewayWebhook webhookRequest);
}
