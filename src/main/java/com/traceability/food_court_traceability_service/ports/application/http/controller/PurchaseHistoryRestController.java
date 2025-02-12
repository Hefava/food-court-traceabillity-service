package com.traceability.food_court_traceability_service.ports.application.http.controller;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.utils.TimeUtils;
import com.traceability.food_court_traceability_service.ports.application.http.dto.OrderProcessingTimeResponse;
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
import java.util.Map;

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

    @GetMapping("/processing-time-per-order")
    public ResponseEntity<List<OrderProcessingTimeResponse>> getProcessingTimePerOrder() {
        Map<String, String> processingTimes = purchaseHistoryServicePort.calculateProcessingTimePerOrder();

        List<OrderProcessingTimeResponse> response = processingTimes.entrySet().stream()
                .map(entry -> OrderProcessingTimeResponse.builder()
                        .orderId(entry.getKey())
                        .processingTime(entry.getValue())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee-ranking")
    public ResponseEntity<Map<Long, String>> getEmployeeRanking() {
        Map<Long, String> ranking = purchaseHistoryServicePort.calculateAverageProcessingTimePerEmployee();
        return ResponseEntity.ok(ranking);
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
