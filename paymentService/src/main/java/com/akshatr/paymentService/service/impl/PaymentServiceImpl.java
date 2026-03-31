package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.exceptions.BadRequestException;
import com.akshatr.paymentService.model.dto.order.OrderResponseDto;
import com.akshatr.paymentService.model.dto.payment.PaymentLinkDto;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.model.dto.payment.PaymentSearchDto;
import com.akshatr.paymentService.model.dto.user.UserResponseDto;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import com.akshatr.paymentService.paymentgateway.factory.PaymentGatewayStrategyFactory;
import com.akshatr.paymentService.paymentgateway.strategy.PaymentGatewayStrategy;
import com.akshatr.paymentService.repository.PaymentRepository;
import com.akshatr.paymentService.service.OrderService;
import com.akshatr.paymentService.service.PaymentService;
import com.akshatr.paymentService.service.UserService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayStrategyFactory paymentGatewayStrategyFactory;
    private final UserService userService;
    private final OrderService orderService;

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

    @Override
    public Payment getPayment(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            throw new BadRequestException("Payment not found.");
        }

        return payment.get();
    }

    @Override
    public List<Payment> listAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> searchPayments(PaymentSearchDto request) {
        if(request.getUserId() == null){
            request.setUserId(0L);
        }

        if(request.getOrderId() == null){
            request.setOrderId(0L);
        }

        if(request.getUserId() > 0) {
            UserResponseDto user = userService.findById(request.userId);
            if (user == null) {
                throw new BadRequestException("User not found.");
            }
        }

        if(request.getOrderId() > 0) {
            OrderResponseDto order = orderService.getOrder(request.orderId);
            if (order == null) {
                throw new BadRequestException("Order not found.");
            }
        }

        return paymentRepository.search(request.getOrderId(), request.getUserId());
    }

    @Override
    public Payment getPaymentByNo(String paymentNo){
        Optional<Payment> payment = paymentRepository.findByName(paymentNo);
        if(payment.isEmpty()){
            throw new BadRequestException("Payment not found.");
        }

        return payment.get();
    }

    @Override
    public Payment save(Payment payment){
        return paymentRepository.save(payment);
    }
}
