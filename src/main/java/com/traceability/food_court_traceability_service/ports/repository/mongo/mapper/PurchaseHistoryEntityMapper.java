package com.traceability.food_court_traceability_service.ports.repository.mongo.mapper;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.ports.application.http.dto.PurchaseHistoryRequest;
import com.traceability.food_court_traceability_service.ports.repository.mongo.entity.PurchaseHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryEntityMapper {
    PurchaseHistory toModel(PurchaseHistoryRequest purchaseHistoryRequest);

    PurchaseHistoryEntity toEntity(PurchaseHistory purchaseHistory);
}
