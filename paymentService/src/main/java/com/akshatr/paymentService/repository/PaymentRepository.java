package com.akshatr.paymentService.repository;

import com.akshatr.paymentService.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
