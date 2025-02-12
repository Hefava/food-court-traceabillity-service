package com.traceability.food_court_traceability_service.domain.spi;

import java.util.List;

public interface IRestaurantPersistencePort {
    List<Long> getEmployeesByOwner(String ownerId);
}