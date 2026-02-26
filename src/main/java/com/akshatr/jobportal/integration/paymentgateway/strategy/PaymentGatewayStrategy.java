package com.akshatr.jobportal.integration.paymentgateway.strategy;

import com.akshatr.jobportal.model.entity.Payment;
import com.akshatr.jobportal.model.enums.PaymentGateway;

public abstract class PaymentGatewayStrategy {
    public abstract PaymentGateway getPaymentGateway();
    public abstract String generatePaymentLink(Payment payment);
}
