package com.akshatr.paymentService.model.dto.integration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RazorpayWebhook {
    private String signature;
    private String payload;
}
