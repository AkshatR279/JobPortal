package com.akshatr.jobportal.model.dto.payment;

import com.akshatr.jobportal.model.dto.order.OrderResponseShortDto;
import com.akshatr.jobportal.model.entity.Order;
import com.akshatr.jobportal.model.enums.PaymentMethod;
import com.akshatr.jobportal.model.enums.PaymentStatus;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentResponseDto {
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdatedOn;
    private Double amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private OrderResponseShortDto order;
}
