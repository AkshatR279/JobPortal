package com.akshatr.paymentService.repository;

import com.akshatr.paymentService.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    public Optional<Payment> findByName(String paymentNo);

    @Query(value = "SELECT P " +
            "FROM Payment P " +
            "WHERE (:orderId = 0 OR P.orderId = :orderId) " +
            "AND (:userId = 0 OR P.userId = :userId)")
    public List<Payment> search(@Param("orderId") Long orderId, @Param("userId") Long userId);
}
