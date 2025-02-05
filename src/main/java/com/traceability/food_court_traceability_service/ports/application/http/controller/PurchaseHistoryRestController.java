package com.traceability.food_court_traceability_service.ports.application.http.controller;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryRequest;
import com.traceability.food_court_traceability_service.ports.application.http.mapper.PurchaseHistoryRequestMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryRestController {
    private final IPurchaseHistoryServicePort purchaseHistoryServicePort;
    private final PurchaseHistoryRequestMapper purchaseHistoryRequestMapper;

    @PostMapping("/generate-report")
    public ResponseEntity<Void> generateReport(
            @RequestBody @Parameter(required = true) PurchaseHistoryRequest request) {
        PurchaseHistory purchaseHistory = purchaseHistoryRequestMapper.toModel(request);
        purchaseHistoryServicePort.generatePurchaseHistory(purchaseHistory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
