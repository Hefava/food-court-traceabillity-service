package com.traceability.food_court_traceability_service.ports.application.http.mapper;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryStatusResponseMapper {
    @Mapping(target = "timeDifference", ignore = true)
    PurchaseHistoryStatusResponse toDto(PurchaseHistory purchaseHistory);
}