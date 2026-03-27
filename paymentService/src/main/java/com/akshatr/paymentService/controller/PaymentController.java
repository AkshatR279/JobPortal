package com.akshatr.paymentService.controller;

import com.akshatr.paymentService.model.dto.GeneralAPIResponse;
import com.akshatr.paymentService.model.dto.payment.PaymentRequestDto;
import com.akshatr.paymentService.model.dto.payment.PaymentResponseDto;
import com.akshatr.paymentService.model.dto.payment.PaymentSearchDto;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.service.PaymentService;
import com.akshatr.paymentService.service.cache.CacheService;
import com.akshatr.paymentService.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final ExceptionHandler exceptionHandler;
    private final CacheService cache;

    private static final String CACHE_NAME = "PAYMENT";

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

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getPayment(@PathVariable Long id){
        try{
            PaymentResponseDto payment = (PaymentResponseDto) cache.get(CACHE_NAME, id.toString());
            if(payment==null){
                payment = convertPaymentToResponseDto(paymentService.getPayment(id));
                cache.add(CACHE_NAME, id.toString(), payment);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(payment)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> listPayments(){
        try{
            List<PaymentResponseDto> payments = (List<PaymentResponseDto>) cache.get(CACHE_NAME, "ALL");
            if(payments==null){
                payments = paymentService.listAllPayments().stream()
                        .map(this::convertPaymentToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, "ALL", payments);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(payments)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<GeneralAPIResponse> search(@RequestBody PaymentSearchDto request){
        try{
            StringBuilder searchString = new StringBuilder();
            searchString.append("SEARCH?");
            if(request.getOrderId() > 0){
                searchString.append("O=").append(request.getOrderId().toString());
            }
            if(request.getUserId() > 0){
                searchString.append("U=").append(request.getUserId().toString());
            }
            if(request.getStatus() != null){
                searchString.append("S=").append(request.getStatus().toString());
            }

            List<PaymentResponseDto> payments = (List<PaymentResponseDto>) cache.get(CACHE_NAME, searchString.toString());
            if(payments==null){
                payments = paymentService.searchPayments(request).stream()
                        .map(this::convertPaymentToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, searchString.toString(), payments);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(payments)
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
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}
