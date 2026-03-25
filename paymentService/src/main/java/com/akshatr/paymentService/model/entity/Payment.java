package com.akshatr.paymentService.model.entity;

import com.akshatr.paymentService.model.enums.PaymentMethod;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_Payment")
@Getter
@Setter
public class Payment extends BaseEntity{
    private Double amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private Long orderId;
    private Long userId;
}
