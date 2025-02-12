package com.traceability.food_court_traceability_service.domain.api;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;

import java.util.List;
import java.util.Map;

public interface IPurchaseHistoryServicePort {
    void generatePurchaseHistory(PurchaseHistory purchaseHistory);
    List<PurchaseHistory> getStatusHistoryByClientId();
    Map<String, String> calculateProcessingTimePerOrder();
    Map<Long, String> calculateAverageProcessingTimePerEmployee();
}
