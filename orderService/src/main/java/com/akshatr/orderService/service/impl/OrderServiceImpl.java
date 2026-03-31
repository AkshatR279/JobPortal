package com.akshatr.orderService.service.impl;

import com.akshatr.orderService.exceptions.BadRequestException;
import com.akshatr.orderService.model.dto.order.OrderRequestDto;
import com.akshatr.orderService.model.dto.order.OrderSearchDto;
import com.akshatr.orderService.model.dto.payment.PaymentEvent;
import com.akshatr.orderService.model.entity.Order;
import com.akshatr.orderService.model.enums.OrderStatus;
import com.akshatr.orderService.model.enums.PaymentStatus;
import com.akshatr.orderService.model.utilmodel.Email;
import com.akshatr.orderService.repository.OrderRepository;
import com.akshatr.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafka;

    @Override
    public Order createOrder(OrderRequestDto request) {
        Optional<Order> existingOrder = orderRepository.findById(request.getId());
        if(existingOrder.isPresent()){
            throw new BadRequestException("Order already created.");
        }

        Order order = new Order();
        order.setName("INV" + request.getOrderType().toString().substring(0,2) + System.currentTimeMillis());
        order.setOrderType(request.getOrderType());
        order.setOrderDate(request.getOrderDate());
        order.setCost(request.getCost());
        order.setTax(request.getTax());
        order.setOrderById(request.getOrderById());
        order.setPaid(0D);
        order.setStatus(OrderStatus.UNPAID);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new BadRequestException("Order not found.");
        }

        return order.get();
    }

    @Override
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> search(OrderSearchDto request) {
        return orderRepository.search(request.getUserId());
    }

    @Override
    public void updatePaymentStatus(PaymentEvent event){
        Optional<Order> existingOrder = orderRepository.findById(event.getOrderId());
        if(existingOrder.isEmpty()){
            throw new BadRequestException("Order not found.");
        }

        Order order = existingOrder.get();
        if(event.getStatus() == PaymentStatus.SUCCESS){
            double currentPaid = order.getPaid() + event.getAmount();

            order.setPaid(currentPaid);
            if(currentPaid < order.getCost()){
                order.setStatus(OrderStatus.PARTIALLY_PAID);
            }
            else if(order.getCost().equals(currentPaid)){
                order.setStatus(OrderStatus.PAID);
            }
        }

        orderRepository.save(order);

        if(order.getStatus() == OrderStatus.PAID)
            sentConfirmationEmail(order);
    }

    private void sentConfirmationEmail(Order order){
        Email email = Email.builder()
                .to("akshatr279.b@gmail.com")
                .subject("Order Confirmation - " + order.getName())
                .content("Order confirmed [ " + order.getName() + " ]"
                        + "\nProduct: " + order.getOrderType()
                        + "\nInvoice Date: " + order.getOrderDate()
                        + "\nCost: " + order.getCost()
                        + "\nTax Amt.:" + order.getTax())
                .build();

        kafka.send("SEND_EMAIL", email);
    }
}
