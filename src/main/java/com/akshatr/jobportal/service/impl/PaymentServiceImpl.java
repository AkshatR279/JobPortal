package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.order.OrderResponseDto;
import com.akshatr.jobportal.model.dto.payment.PaymentLinkDto;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.service.OrderService;
import com.akshatr.jobportal.service.PaymentService;
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
}
