package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;
import com.akshatr.jobportal.model.entity.Payment;

import java.util.List;

public interface PaymentService {
    public List<Payment> listPayments(PaymentSearchDto request);
    public String create(PaymentRequestDto request);
}
