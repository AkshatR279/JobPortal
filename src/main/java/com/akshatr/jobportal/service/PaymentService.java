package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.payment.PaymentLinkDto;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentResponseDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;

import java.util.List;

public interface PaymentService {
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request);
    public PaymentResponseDto getPayment(Long id);
    public List<PaymentResponseDto> listAllPayments();
    public List<PaymentResponseDto> searchPayments(PaymentSearchDto request);
}
