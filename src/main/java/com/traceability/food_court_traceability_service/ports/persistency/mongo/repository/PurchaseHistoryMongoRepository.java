package com.traceability.food_court_traceability_service.ports.persistency.mongo.repository;

import com.traceability.food_court_traceability_service.ports.persistency.mongo.entity.PurchaseHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseHistoryMongoRepository extends MongoRepository<PurchaseHistoryEntity, String> {
    List<PurchaseHistoryEntity> findByClientId(String clientId);
}