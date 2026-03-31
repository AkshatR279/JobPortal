package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.exceptions.BadRequestException;
import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.payment.PaymentLinkDto;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentResponseDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;
import com.akshatr.jobportal.service.OrderService;
import com.akshatr.jobportal.service.PaymentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    @Override
    public PaymentLinkDto generatePaymentLink(PaymentRequestDto request) {
        OrderResponseDto order = orderService.getOrder(request.getId());
        if(order == null){
            throw new RuntimeException("Invalid Order");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.postForEntity(
                "http://paymentService/api/payments/pay",
                request,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to generate payment link.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        PaymentLinkDto paymentLinkDto = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                PaymentLinkDto.class
        );

        if(paymentLinkDto == null){
            throw new RuntimeException("Failed to generate payment link.");
        }

        return paymentLinkDto;
    }

    @Override
    public PaymentResponseDto getPayment(Long id) {
        if(id == null){
            throw new BadRequestException("Payment not found.");
        }

        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://paymentService/api/payments/" + id,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to fetch payment.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        PaymentResponseDto payment = new ObjectMapper().convertValue(
                apiResponse.getData(),
                PaymentResponseDto.class
        );

        if(payment == null){
            throw new RuntimeException("Failed to fetch payment.");
        }

        return payment;
    }

    @Override
    public List<PaymentResponseDto> listAllPayments() {
        ResponseEntity<GeneralAPIResponse> response = restTemplate.getForEntity(
                "http://paymentService/api/payments",
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to fetch payments.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        List<PaymentResponseDto> payments = new ObjectMapper().convertValue(
                apiResponse.getData(),
                new TypeReference<List<PaymentResponseDto>>() {}
        );

        if(payments == null){
            throw new RuntimeException("Failed to fetch payments.");
        }

        return payments;
    }

    @Override
    public List<PaymentResponseDto> searchPayments(PaymentSearchDto request) {
        ResponseEntity<GeneralAPIResponse> response = restTemplate.postForEntity(
                "http://paymentService/api/payments/search",
                request,
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();
        if(apiResponse == null){
            throw new RuntimeException("Failed to fetch payments.");
        }

        if(!apiResponse.getSuccess()){
            throw new RuntimeException(apiResponse.getMessage());
        }

        List<PaymentResponseDto> payments = new ObjectMapper().convertValue(
                apiResponse.getData(),
                new TypeReference<List<PaymentResponseDto>>() {}
        );

        if(payments == null){
            throw new RuntimeException("Failed to fetch payments.");
        }

        return payments;
    }
}
