package com.akshatr.paymentService.paymentgateway.strategy;

import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;

public abstract class PaymentGatewayStrategy {
    public abstract PaymentGateway getPaymentGateway();
    public abstract String generatePaymentLink(Payment payment);
}
