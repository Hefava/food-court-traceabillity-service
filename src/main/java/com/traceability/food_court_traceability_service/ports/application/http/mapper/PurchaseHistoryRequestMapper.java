package com.traceability.food_court_traceability_service.ports.application.http.mapper;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryRequestMapper {
    PurchaseHistory toModel(PurchaseHistoryRequest purchaseHistoryRequest);

    PurchaseHistoryRequest toDto(PurchaseHistory purchaseHistory);
}
