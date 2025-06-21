package com.examples.spring_jpa.examples;

import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;

public class VerificationCodeService {
    private final Clock clock;
    private final Duration codeExpirationDuration;

    public VerificationCodeService(Clock clock, Duration codeExpirationDuration) {
        this.clock = clock;
        this.codeExpirationDuration = codeExpirationDuration;
    }

    public boolean isExpired(VerificationCode verificationCode) {
        ZonedDateTime now = ZonedDateTime.now(clock);
        Duration timeElapsed = Duration.between(verificationCode.createdAt(), now);
        return timeElapsed.compareTo(codeExpirationDuration) > 0;
    }
}
