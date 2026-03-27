package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.exceptions.BadRequestException;
import com.akshatr.paymentService.model.dto.payment.PaymentLinkDto;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import com.akshatr.paymentService.paymentgateway.factory.PaymentGatewayStrategyFactory;
import com.akshatr.paymentService.paymentgateway.strategy.PaymentGatewayStrategy;
import com.akshatr.paymentService.repository.PaymentRepository;
import com.akshatr.paymentService.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayStrategyFactory paymentGatewayStrategyFactory;

    @Override
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request) {
        Payment payment = new Payment();
        Optional<Payment> existingPayment = paymentRepository.findById(request.getId());
        if(existingPayment.isPresent()){
            throw new BadRequestException("Failed to update payment.");
        }

        payment.setName("TXN-" + request.getOrderId() + "-I-" + System.currentTimeMillis());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        PaymentGatewayStrategy paymentGatewayStrategy = paymentGatewayStrategyFactory.getStrategy(PaymentGateway.RAZORPAY);
        String paymentLink = paymentGatewayStrategy.generatePaymentLink(payment);

        if(paymentLink == null || paymentLink.isBlank()){
            throw new RuntimeException("Failed to generate payment link.");
        }

        return PaymentLinkDto.builder()
                .paymentGatewayLink(paymentLink)
                .build();
    }
}
