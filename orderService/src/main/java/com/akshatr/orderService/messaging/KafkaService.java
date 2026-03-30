package com.akshatr.orderService.messaging;

import com.akshatr.orderService.model.dto.payment.PaymentEvent;
import com.akshatr.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final OrderService orderService;

    @KafkaListener(topics = "PAYMENT_STATUS_CONF")
    public void paymentListener(PaymentEvent event){
        orderService.updatePaymentStatus(event);
    }
}
