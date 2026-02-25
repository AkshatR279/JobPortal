package com.akshatr.jobportal.repository;

import com.akshatr.jobportal.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query("SELECT P FROM Payment P" +
            "WHERE (:orderId = 0 OR P.order.id = :orderId) " +
            "AND (:userId = 0 OR P.user.id = :userId)")
    public List<Payment> listPayments(@Param("orderId") Long orderId, @Param("userId") Long userId);
}
