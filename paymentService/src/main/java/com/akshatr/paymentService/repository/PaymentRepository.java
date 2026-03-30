package com.akshatr.paymentService.repository;

import com.akshatr.paymentService.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    public Optional<Payment> findByName(String paymentNo);
}
