package com.akshatr.paymentService.config;

import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.model.utilmodel.Credentials;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@Getter
public class PaymentGatewayConfig {
    private final Map<PaymentGateway, Credentials> config;

    public PaymentGatewayConfig(@Value("${razorpay.api.key}") String key_rzp, @Value("${razorpay.api.secret}") String secret_rzp, @Value("${razorpay.webhook.secret}") String secret_webhook_rzp){
        config = new EnumMap<>(PaymentGateway.class);
        config.put(PaymentGateway.RAZORPAY, new Credentials(key_rzp, secret_rzp, secret_webhook_rzp));
    }

    public Credentials getCredentials(PaymentGateway paymentGateway){
        Credentials credentials = config.get(paymentGateway);
        if(credentials==null){
            throw new RuntimeException("Payment Gateway not configured: " + paymentGateway);
        }

        return credentials;
    }
}
