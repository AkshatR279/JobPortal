package com.akshatr.paymentService.service;

import com.akshatr.paymentService.model.dto.payment.PaymentLinkDto;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.model.dto.payment.PaymentSearchDto;
import com.akshatr.paymentService.model.entity.Payment;

import java.util.List;

public interface PaymentService {
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request);
    public Payment getPayment(Long id);
    public List<Payment> listAllPayments();
    public List<Payment> searchPayments(PaymentSearchDto request);
}
