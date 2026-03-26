package com.akshatr.orderService.repository;

import com.akshatr.orderService.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT O" +
            " FROM Order O" +
            " WHERE (:orderBy = 0 OR O.orderById = :orderBy)")
    public List<Order> search(@Param("orderBy") Long orderBy);
}
