package com.akshatr.orderService.model.entity;

import com.akshatr.orderService.model.enums.OrderStatus;
import com.akshatr.orderService.model.enums.OrderType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Entity
@Table(name = "tbl_Orders")
@Getter
@Setter
public class Order extends BaseEntity{
    private OrderType orderType;
    private Date orderDate;
    private Double cost;
    private Double tax;
    private Double paid;
    private OrderStatus status;
    private Long orderById;
}
