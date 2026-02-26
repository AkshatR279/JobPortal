package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.integration.paymentgateway.factory.PaymentGatewayStrategyFactory;
import com.akshatr.jobportal.integration.paymentgateway.strategy.PaymentGatewayStrategy;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;
import com.akshatr.jobportal.model.entity.Order;
import com.akshatr.jobportal.model.entity.Payment;
import com.akshatr.jobportal.model.enums.PaymentGateway;
import com.akshatr.jobportal.model.enums.PaymentStatus;
import com.akshatr.jobportal.repository.OrderRepository;
import com.akshatr.jobportal.repository.PaymentRepository;
import com.akshatr.jobportal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentGatewayStrategyFactory paymentGatewayStrategyFactory;

    @Override
    public List<Payment> listPayments(PaymentSearchDto request) {
        Long orderId = request.getOrderId() != null ? request.getOrderId() : 0;
        Long userId = request.getUserId() != null ? request.getUserId() : 0;

        return paymentRepository.listPayments(orderId, userId);
    }

    @Override
    public String create(PaymentRequestDto request) {
        Optional<Order> order = orderRepository.findById(request.getOrderId());
        if(order.isEmpty()){
            throw new RuntimeException("Order not found.");
        }

        Payment payment = new Payment();
        Optional<Payment> existingPayment = paymentRepository.findById(request.getId());
        if(existingPayment.isPresent()){
            payment = existingPayment.get();
        }

        payment.setName("TXN" + request.getOrderId() + "I" + System.currentTimeMillis());
        payment.setOrder(order.get());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        PaymentGatewayStrategy paymentGatewayStrategy = paymentGatewayStrategyFactory.getStrategy(PaymentGateway.RAZORPAY);
        String paymentLink = paymentGatewayStrategy.generatePaymentLink(payment);

        return paymentLink;
    }
}
