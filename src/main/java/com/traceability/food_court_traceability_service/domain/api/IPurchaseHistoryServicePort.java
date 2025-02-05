package com.traceability.food_court_traceability_service.domain.api;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;

public interface IPurchaseHistoryServicePort {
    void generatePurchaseHistory(PurchaseHistory purchaseHistory);
}
