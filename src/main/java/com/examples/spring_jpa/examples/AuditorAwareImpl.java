package com.examples.spring_jpa.examples;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Here you can implement logic to retrieve the current auditor, e.g., from the security context
        return Optional.of("system"); // Placeholder for demonstration
    }

}
