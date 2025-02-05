package com.traceability.food_court_traceability_service.ports.repository.mongo.persistency;

import com.traceability.food_court_traceability_service.ports.repository.mongo.entity.PurchaseHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseHistoryMongoRepository extends MongoRepository<PurchaseHistoryEntity, String> {
}