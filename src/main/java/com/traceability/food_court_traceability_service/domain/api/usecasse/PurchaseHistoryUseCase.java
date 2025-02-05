package com.traceability.food_court_traceability_service.domain.api.usecasse;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;

public class PurchaseHistoryUseCase implements IPurchaseHistoryServicePort {
    IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort;

    public PurchaseHistoryUseCase(IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort) {
        this.purchaseHistoryPersistencePort = purchaseHistoryPersistencePort;
    }

    @Override
    public void generatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryPersistencePort.generatePurchaseHistory(purchaseHistory);
    }
}
