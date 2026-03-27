package com.akshatr.paymentService.paymentgateway.factory;

import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.paymentgateway.strategy.PaymentGatewayStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentGatewayStrategyFactory {
    private final Map<PaymentGateway, PaymentGatewayStrategy> strategyMap;

    public PaymentGatewayStrategyFactory(List<PaymentGatewayStrategy> strategies){
        strategyMap = new EnumMap<>(PaymentGateway.class);

        for(PaymentGatewayStrategy strategy : strategies){
            strategyMap.put(strategy.getPaymentGateway(), strategy);
        }
    }

    public PaymentGatewayStrategy getStrategy(PaymentGateway paymentGateway){
        PaymentGatewayStrategy strategy = strategyMap.get(paymentGateway);
        if(strategy==null){
            throw new RuntimeException("Invalid Payment Gateway strategy.");
        }

        return strategy;
    }
}
