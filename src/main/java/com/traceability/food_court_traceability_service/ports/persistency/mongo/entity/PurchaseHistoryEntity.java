package com.traceability.food_court_traceability_service.ports.persistency.mongo.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("PurchaseHistory")
public class PurchaseHistoryEntity {
    @Id
    private String orderId;
    private String clientId;
    private String clientEmail;
    private LocalDateTime statusDate;
    private String lastStatus;
    private String newStatus;
    private String employeeId;
    private String employeeEmail;
}