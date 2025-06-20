package com.examples.spring_jpa.examples;

import java.math.BigDecimal;

public class OrderService {
    private final PaymentProcessor paymentProcessor;

    public OrderService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public boolean processOrder(BigDecimal amount) {
        boolean isCharged = paymentProcessor.charge(amount);
        return isCharged; 
    }
}
