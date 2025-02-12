package com.traceability.food_court_traceability_service.domain.spi;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;

import java.util.List;

public interface IPurchaseHistoryPersistencePort {
    void generatePurchaseHistory(PurchaseHistory purchaseHistory);
    List<PurchaseHistory> findByClientId(String clientId);
    List<PurchaseHistory> findAll();
}
