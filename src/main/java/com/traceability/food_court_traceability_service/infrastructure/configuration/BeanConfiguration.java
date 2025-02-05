package com.traceability.food_court_traceability_service.infrastructure.configuration;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.api.usecasse.PurchaseHistoryUseCase;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort;

    @Bean
    public IPurchaseHistoryServicePort purchaseHistoryServicePort() {
        return new PurchaseHistoryUseCase(purchaseHistoryPersistencePort);
    }
}
