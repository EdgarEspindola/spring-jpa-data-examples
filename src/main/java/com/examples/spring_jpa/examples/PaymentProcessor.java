package com.examples.spring_jpa.examples;

import java.math.BigDecimal;

public interface PaymentProcessor {

    boolean charge(BigDecimal amount);
}
