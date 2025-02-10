package com.traceability.food_court_traceability_service.ports.feign;

import com.traceability.food_court_traceability_service.domain.utils.TokenHolder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.traceability.food_court_traceability_service.domain.utils.TraceabilityUtils.AUTHORIZATION;

@Configuration
public class FeingClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = TokenHolder.getToken();
            if (token != null && !token.isEmpty()) {
                requestTemplate.header(AUTHORIZATION, token);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}

