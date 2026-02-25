package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.PaymentMethod;
import com.akshatr.jobportal.model.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private Order order;

    @ManyToOne
    private User user;
}
