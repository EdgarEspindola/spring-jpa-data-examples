package com.examples.spring_jpa.examples;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTests {
    private OrderService underTest;
    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {
        paymentProcessor = mock();
        underTest = new OrderService(paymentProcessor);
    }

    @Test
    void shouldChargeSuccessfully() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        when(paymentProcessor.charge(amount)).thenReturn(true);

        // When
        boolean actual = underTest.processOrder(amount);

        // Then
        verify(paymentProcessor).charge(amount);
        assertThat(actual).isTrue();
    }
}
