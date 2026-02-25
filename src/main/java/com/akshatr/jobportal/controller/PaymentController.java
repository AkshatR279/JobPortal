package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentResponseDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;
import com.akshatr.jobportal.model.entity.Payment;
import com.akshatr.jobportal.service.PaymentService;
import com.akshatr.jobportal.util.DtoConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final DtoConvertor dtoConvertor;

    @PostMapping
    public List<PaymentResponseDto> listPayments(PaymentSearchDto request){
        return paymentService.listPayments(request).stream()
                .map(this::convertPaymentToResponseDto)
                .toList();
    }

    @PostMapping("/save")
    public PaymentResponseDto save(PaymentRequestDto request){
        return convertPaymentToResponseDto(paymentService.save(request));
    }

    private PaymentResponseDto convertPaymentToResponseDto(Payment payment){
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .name(payment.getName())
                .createdOn(payment.getCreatedOn())
                .lastUpdatedOn(payment.getLastUpdatedOn())
                .order(dtoConvertor.convertOrderToResponseShort(payment.getOrder()))
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}
