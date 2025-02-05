package com.traceability.food_court_traceability_service.ports.repository.mongo.adapter;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;
import com.traceability.food_court_traceability_service.ports.repository.mongo.mapper.PurchaseHistoryEntityMapper;
import com.traceability.food_court_traceability_service.ports.repository.mongo.persistency.PurchaseHistoryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseHistoryAdapter implements IPurchaseHistoryPersistencePort {
    private final PurchaseHistoryMongoRepository purchaseHistoryMongoRepository;
    private final PurchaseHistoryEntityMapper purchaseHistoryEntityMapper;

    @Override
    public void generatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryMongoRepository.save(purchaseHistoryEntityMapper.toEntity(purchaseHistory));
    }
}
