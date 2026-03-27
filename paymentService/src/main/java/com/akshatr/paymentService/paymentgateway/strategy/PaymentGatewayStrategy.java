package com.akshatr.paymentService.paymentgateway.strategy;

import com.akshatr.paymentService.model.dto.integration.PaymentGatewayStatus;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayWebhook;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;

public abstract class PaymentGatewayStrategy {
    public abstract PaymentGateway getPaymentGateway();
    public abstract String generatePaymentLink(Payment payment);
    public abstract boolean verifyWebhook(PaymentGatewayWebhook webhook);
    public abstract PaymentGatewayStatus processWebhook(PaymentGatewayWebhook webhook);
}
