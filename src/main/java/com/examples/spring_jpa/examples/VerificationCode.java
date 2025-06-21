package com.examples.spring_jpa.examples;

import java.time.ZonedDateTime;

public record VerificationCode(
    String code,
    ZonedDateTime createdAt
) {

}
