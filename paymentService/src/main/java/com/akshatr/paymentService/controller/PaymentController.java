package com.akshatr.paymentService.controller;

import com.akshatr.paymentService.model.dto.GeneralAPIResponse;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.model.dto.payment.PaymentResponseDto;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.service.PaymentService;
import com.akshatr.paymentService.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final ExceptionHandler exceptionHandler;

    @PostMapping("/pay")
    public ResponseEntity<GeneralAPIResponse> generatePaymentLink(@RequestBody PaymentRequestDto request){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(paymentService.generatePaymentLink(request))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
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
