package com.codingshuttle.w4p1.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
//        get security context
//        get authentication
//        get the principle
//        get the username
        return Optional.of("Anuj Kumar Sharma");
    }
}
