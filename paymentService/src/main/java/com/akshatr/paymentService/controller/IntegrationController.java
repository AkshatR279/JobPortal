package com.akshatr.paymentService.controller;

import com.akshatr.paymentService.model.dto.GeneralAPIResponse;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayWebhook;
import com.akshatr.paymentService.model.dto.integration.RazorpayWebhook;
import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.service.IntegrationService;
import com.akshatr.paymentService.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrations")
@RequiredArgsConstructor
public class IntegrationController {
    private final IntegrationService integrationService;
    private final ExceptionHandler exceptionHandler;

    @PostMapping("/razorpay/confirmLinkPayment")
    public ResponseEntity<GeneralAPIResponse> confirmPaymentStatus_RZP(@RequestHeader("X-Razorpay-Signature") String signature, @RequestBody String payload) {
        try{
            PaymentGatewayWebhook webhookRequest = PaymentGatewayWebhook.builder()
                    .paymentGateway(PaymentGateway.RAZORPAY)
                    .webhookData(
                            RazorpayWebhook.builder()
                                    .signature(signature)
                                    .payload(payload)
                                    .build()
                    )
                    .build();

            integrationService.confirmPaymentStatus(webhookRequest);

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }
}
