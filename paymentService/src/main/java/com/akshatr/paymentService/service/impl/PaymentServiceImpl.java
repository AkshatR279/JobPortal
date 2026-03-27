package com.akshatr.paymentService.service.impl;

import com.akshatr.paymentService.model.dto.payment.PaymentLinkDto;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request) {
        return null;
    }
}
