package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.exceptions.BadRequestException;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayStatus;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayWebhook;
import com.akshatr.paymentService.model.dto.payment.PaymentEvent;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import com.akshatr.paymentService.paymentgateway.factory.PaymentGatewayStrategyFactory;
import com.akshatr.paymentService.paymentgateway.strategy.PaymentGatewayStrategy;
import com.akshatr.paymentService.service.IntegrationService;
import com.akshatr.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {
    private final PaymentGatewayStrategyFactory paymentGatewayStrategyFactory;
    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafka;

    @Override
    public void confirmPaymentStatus(PaymentGatewayWebhook webhookRequest) {
        PaymentGatewayStrategy paymentGatewayStrategy = paymentGatewayStrategyFactory.getStrategy(webhookRequest.getPaymentGateway());

        if (!paymentGatewayStrategy.verifyWebhook(webhookRequest)) {
            throw new BadRequestException("Invalid signature.");
        }

        PaymentGatewayStatus paymentGatewayStatus = paymentGatewayStrategy.processWebhook(webhookRequest);

        Payment payment = paymentService.getPaymentByNo(paymentGatewayStatus.getPaymentNo());
        payment.setStatus(paymentGatewayStatus.getStatus());
        paymentService.save(payment);

        PaymentEvent event = PaymentEvent.builder()
                .id(payment.getId())
                .name(payment.getName())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .userId(payment.getUserId())
                .status(payment.getStatus())
                .build();

        kafka.send("PAYMENT_STATUS_CONF", event);
    }
}
