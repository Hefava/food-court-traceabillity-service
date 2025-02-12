package com.traceability.food_court_traceability_service.ports.application.http.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryStatusResponse {
    private String orderId;
    private LocalDateTime statusDate;
    private String lastStatus;
    private String newStatus;
    private String timeDifference;
}