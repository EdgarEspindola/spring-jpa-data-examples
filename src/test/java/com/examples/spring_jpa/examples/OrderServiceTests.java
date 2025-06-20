package com.examples.spring_jpa.examples;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.inOrder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private PaymentProcessor paymentProcessor;

    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private OrderService underTest;

    @BeforeEach
    void setUp() {
       // underTest = new OrderService(paymentProcessor);
    }

    @Test
    void shouldChargeSuccessfully() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        // When
        boolean actual = underTest.processOrder(null, amount);

        // Then
        verify(paymentProcessor).charge(amount);
        assertThat(actual).isTrue();
    }

    @Test
    void shouldChargeSuccessfullyWithAssertArg() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        User user = new User(1, "John Doe");
        
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);

        // When
        boolean actual = underTest.processOrder(user, amount);

        // Then
        InOrder paymentProcessorOrderVerifier = inOrder(paymentProcessor, orderRepository);
        
        paymentProcessorOrderVerifier.verify(paymentProcessor).charge(amount);
        
        paymentProcessorOrderVerifier.verify(orderRepository).save(assertArg(order -> {
            assertThat(order.id()).isNotNull();
            assertThat(order.amount()).isEqualTo(amount);
            assertThat(order.user()).isEqualTo(user);
            assertThat(order.orderCreatedAt())
                .isBefore(ZonedDateTime.now())
                .isNotNull();
        }));

        assertThat(actual).isTrue();
    }

    @Test
    void shouldChargeSuccessfullyWithArgCaptor() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        User user = new User(1, "John Doe");
        
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);

        // When
        boolean actual = underTest.processOrder(user, amount);

        // Then
        // ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        InOrder paymentProcessorOrderVerifier = inOrder(paymentProcessor, orderRepository);

        paymentProcessorOrderVerifier.verify(paymentProcessor).charge(amount);
        paymentProcessorOrderVerifier.verify(orderRepository).save(orderArgumentCaptor.capture());

        Order orderFromCaptor = orderArgumentCaptor.getValue();
        assertThat(orderFromCaptor.id()).isNotNull();
            assertThat(orderFromCaptor.amount()).isEqualTo(amount);
            assertThat(orderFromCaptor.user()).isEqualTo(user);
            assertThat(orderFromCaptor.orderCreatedAt())
                .isBefore(ZonedDateTime.now())
                .isNotNull();

        assertThat(actual).isTrue();
    }

    @Test
    void shouldThrownWhenChargeFails() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        when(paymentProcessor.charge(amount)).thenReturn(false);
        // When
        assertThatThrownBy(() -> {
            underTest.processOrder(null, amount);
        })
        .hasMessageContaining("Payment failed")
        .isInstanceOf(IllegalStateException.class);

        // Then
        verify(paymentProcessor).charge(amount);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void testAnyMatcher() {
        Map<String, String> mockMap = mock();
        when(mockMap.get(anyString())).thenReturn("hello");
        assertThat(mockMap.get("0")).isEqualTo("hello");
        assertThat(mockMap.get("1")).isEqualTo("hello");
        verify(mockMap, times(2)).get(anyString());
    }

    @Test
    void testEqMatcher() {
        Map<String, String> mockMap = mock();
        when(mockMap.put(anyString(), eq("1"))).thenReturn("hello");

        String actual = mockMap.put("hello", "1");

        assertThat(actual).isEqualTo("hello");
        verify(mockMap).put(eq("hello"), eq("1"));
    }

    @Test
    void shouldVerifyNoInteractions() {
        // Given
        List<String> mockList = mock();
        // When
        //mockList.clear();
        // Then
        verifyNoInteractions(mockList);
    }

    @Test
    void shouldVerifyNoMoreInteractions() {
        // Given
        List<String> mockList = mock();
        // When
        mockList.clear();
        mockList.add("hello");
        // Then
        verify(mockList).clear();
        verify(mockList).add("hello");
        verifyNoMoreInteractions(mockList);
    }

    @Test
    void shouldVerifyInteractionMode() {
        // Given
        List<String> mockList = mock();
        // When
        mockList.clear();
        mockList.clear();
        // Then
        verify(mockList, times(2)).clear();
        verify(mockList, never()).reversed();
        verifyNoMoreInteractions(mockList);
    }

    @Test
    void mockitoDbb() {
        // Given
        List<String> mockList = mock();

        // when(mockList.get(0)).thenReturn("hello");
        given(mockList.get(0)).willReturn("hello");
       
        // When
        String actual = mockList.get(0);

        // Then

        // verify(mockList).get(0);
        then(mockList).should().get(0);

        assertThat(actual).isEqualTo("hello");
    }

    @Test
    void shouldThrownWhenChargeFailsWithMockitoBDD() {
        // Given
        BigDecimal amount = new BigDecimal("100.00");
        given(paymentProcessor.charge(amount)).willReturn(false);
        // When
        assertThatThrownBy(() -> {
            underTest.processOrder(null, amount);
        })
        .hasMessageContaining("Payment failed")
        .isInstanceOf(IllegalStateException.class);

        // Then
        then(paymentProcessor).should().charge(amount);
        then(orderRepository).shouldHaveNoInteractions();
    }

    @Test
    void chainedStubbing() {
        // Given
        List<String> mockList = mock();

        // When
        given(mockList.size()).willReturn(1, 2, 3, 4);
        // when(mockList.size()).thenReturn(1, 2, 3, 4);

        // Then
        assertThat(mockList.size()).isEqualTo(1);
        assertThat(mockList.size()).isEqualTo(2);
        assertThat(mockList.size()).isEqualTo(3);
        assertThat(mockList.size()).isEqualTo(4); // moving forward will always return 4
        assertThat(mockList.size()).isEqualTo(4);
    }

    @Test
    void shouldReturnCustomAnswer() {
        // Given
        List<String> mockList = mock();

        // When
        given(mockList.get(anyInt())).will(invocation -> {
            int index = invocation.getArgument(0);
            return "Hello index " + index;
        });

        // Then
        assertThat(mockList.get(0)).isEqualTo("Hello index 0");
        assertThat(mockList.get(1)).isEqualTo("Hello index 1");
        assertThat(mockList.get(2)).isEqualTo("Hello index 2");
    }

    @Test 
    void async() {
        // Given
        Runnable mockRunnable = mock();

        // When
        Executors
            .newSingleThreadScheduledExecutor()
            .schedule(mockRunnable, 200, TimeUnit.SECONDS);

        // Then
        then(mockRunnable).should(timeout(500).times(1)).run();
    }
}
