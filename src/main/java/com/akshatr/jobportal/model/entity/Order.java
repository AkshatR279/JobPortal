package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.OrderStatus;
import com.akshatr.jobportal.model.enums.OrderType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @ManyToOne
    private User orderBy;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;
}
