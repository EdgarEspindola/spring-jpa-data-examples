package com.examples.spring_jpa.examples;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class VerificationCodeServiceTests {
    
    private final int VERIFICATION_CODE_DURATION_IN_MINUTES = 10;

    @Mock
    private Clock clock;

    private VerificationCodeService underTest; 

    private final ZoneId zoneId = ZoneId.of("Mexico/General");

    private final ZonedDateTime verificationCodeCreationTime = ZonedDateTime.now(zoneId);

    private VerificationCode verificationCode = new VerificationCode("test", verificationCodeCreationTime);

    @BeforeEach
    void setUp() {
        given(clock.getZone()).willReturn(zoneId);

        Duration duration = Duration.ofMinutes(VERIFICATION_CODE_DURATION_IN_MINUTES);
        underTest = new VerificationCodeService(clock, duration);
    }

    @Test
    void isExpired_shouldReturnFalse_whenCodeIsJustCreated() {
        // Given
        given(clock.instant()).willReturn(verificationCodeCreationTime.toInstant());

        // When
        boolean expired = underTest.isExpired(verificationCode);

        // Then 
        assertThat(expired).isFalse();
    }

    @Test
    void isExpired_shouldReturnFalse_whenCodeIsWithinExpiration() {
        // Given
        given(clock.instant()).willReturn(verificationCodeCreationTime.plusMinutes(5).toInstant());

        // When
        boolean expired = underTest.isExpired(verificationCode);

        // Then 
        assertThat(expired).isFalse();
    }

    @Test
    void isExpired_shouldReturnTrue_whenCodeIsExpired() {
         // Given
        given(clock.instant()).willReturn(verificationCodeCreationTime.plusMinutes(11).toInstant());

        // When
        boolean expired = underTest.isExpired(verificationCode);

        // Then 
        assertThat(expired).isTrue();


    }

    @Test
    void isExpired_shouldReturnFalse_whenCodeIsExactlyAtExpirationBoundary() {
         // Given
        given(clock.instant()).willReturn(verificationCodeCreationTime.plusMinutes(VERIFICATION_CODE_DURATION_IN_MINUTES).toInstant());

        // When
        boolean expired = underTest.isExpired(verificationCode);

        // Then 
        assertThat(expired).isFalse();
    }
}
