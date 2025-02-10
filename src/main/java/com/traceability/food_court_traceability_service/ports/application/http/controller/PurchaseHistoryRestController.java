package com.traceability.food_court_traceability_service.ports.application.http.controller;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.utils.TimeUtils;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryRequest;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryStatusResponse;
import com.traceability.food_court_traceability_service.ports.application.http.mapper.PurchaseHistoryRequestMapper;
import com.traceability.food_court_traceability_service.ports.application.http.mapper.PurchaseHistoryStatusResponseMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryRestController {
    private final IPurchaseHistoryServicePort purchaseHistoryServicePort;
    private final PurchaseHistoryRequestMapper purchaseHistoryRequestMapper;
    private final PurchaseHistoryStatusResponseMapper purchaseHistoryStatusResponseMapper;

    @PostMapping("/generate-report")
    public ResponseEntity<Void> generateReport(
            @RequestBody @Parameter(required = true) PurchaseHistoryRequest request) {

        PurchaseHistory purchaseHistory = purchaseHistoryRequestMapper.toModel(request);
        purchaseHistoryServicePort.generatePurchaseHistory(purchaseHistory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-status-history")
    public ResponseEntity<List<PurchaseHistoryStatusResponse>> getStatusHistory() {
        List<PurchaseHistory> purchaseHistories = purchaseHistoryServicePort.getStatusHistoryByClientId();

        List<PurchaseHistoryStatusResponse> response = calculateTimeDifferences(purchaseHistories);

        return ResponseEntity.ok(response);
    }

    private List<PurchaseHistoryStatusResponse> calculateTimeDifferences(List<PurchaseHistory> purchaseHistories) {
        LocalDateTime[] previousStatusDate = { null };

        return purchaseHistories.stream()
                .map(history -> {
                    PurchaseHistoryStatusResponse response = purchaseHistoryStatusResponseMapper.toDto(history);
                    response.setTimeDifference(TimeUtils.calculateTimeDifference(previousStatusDate[0], history.getStatusDate()));

                    previousStatusDate[0] = history.getStatusDate();

                    return response;
                })
                .toList();
    }

}
