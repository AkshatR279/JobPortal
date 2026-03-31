package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.payment.PaymentLinkDto;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;

public interface PaymentService {
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request);
}
