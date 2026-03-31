package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.payment.PaymentRequestDto;
import com.akshatr.jobportal.model.dto.payment.PaymentSearchDto;
import com.akshatr.jobportal.service.PaymentService;
import com.akshatr.jobportal.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getPayment(@PathVariable Long id){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(paymentService.getPayment(id))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> listPayments(){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(paymentService.listAllPayments())
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<GeneralAPIResponse> search(@RequestBody PaymentSearchDto request){
        try{
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(paymentService.searchPayments(request))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }
}
