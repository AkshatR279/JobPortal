package com.akshatr.paymentService.service;

import com.akshatr.paymentService.model.dto.payment.PaymentLinkDto;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;

public interface PaymentService {
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request);
}
