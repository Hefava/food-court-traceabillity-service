package com.traceability.food_court_traceability_service.domain.api.usecasse;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;
import com.traceability.food_court_traceability_service.domain.spi.IUserPersistencePort;

import java.util.List;

public class PurchaseHistoryUseCase implements IPurchaseHistoryServicePort {
    IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort;
    IUserPersistencePort userPersistencePort;

    public PurchaseHistoryUseCase(IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort, IUserPersistencePort userPersistencePort) {
        this.purchaseHistoryPersistencePort = purchaseHistoryPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void generatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryPersistencePort.generatePurchaseHistory(purchaseHistory);
    }

    @Override
    public List<PurchaseHistory> getStatusHistoryByClientId() {
        String clientId = userPersistencePort.getUserId();
        return purchaseHistoryPersistencePort.findByClientId(clientId);
    }
}
