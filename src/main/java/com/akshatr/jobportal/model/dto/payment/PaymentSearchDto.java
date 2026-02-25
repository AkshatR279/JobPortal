package com.akshatr.jobportal.model.dto.payment;

import com.akshatr.jobportal.model.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentSearchDto {
    public Long orderId;
    public Long userId;
    public PaymentStatus status;
}
