package com.traceability.food_court_traceability_service.ports.application.http.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProcessingTimeResponse {
    private String orderId;
    private String processingTime;
}