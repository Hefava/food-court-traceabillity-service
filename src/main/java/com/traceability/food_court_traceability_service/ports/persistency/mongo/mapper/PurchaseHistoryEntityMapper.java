package com.traceability.food_court_traceability_service.ports.persistency.mongo.mapper;

import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.ports.persistency.mongo.entity.PurchaseHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryEntityMapper {
    PurchaseHistory toModel(PurchaseHistoryEntity purchaseHistoryEntity);

    PurchaseHistoryEntity toEntity(PurchaseHistory purchaseHistory);
}
