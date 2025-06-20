package com.examples.spring_jpa.examples;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record Order(
    UUID id, 
    User user,
    BigDecimal amount, 
    ZonedDateTime orderCreatedAt
) {

}
