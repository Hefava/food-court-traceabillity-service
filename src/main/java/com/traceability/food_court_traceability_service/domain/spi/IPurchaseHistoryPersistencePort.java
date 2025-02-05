package com.traceability.food_court_traceability_service.domain.spi;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;

public interface IPurchaseHistoryPersistencePort {
    void generatePurchaseHistory(PurchaseHistory purchaseHistory);
}
