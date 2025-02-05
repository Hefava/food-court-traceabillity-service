package com.traceability.food_court_traceability_service.ports.application.http.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryRequest {
    private String orderId;
    private String clientId;
    private String clientEmail;
    private String lastStatus;
    private String newStatus;
    private String employeeId;
    private String employeeEmail;
}
