package com.traceability.food_court_traceability_service.ports.persistency.mongo.adapter;

import com.traceability.food_court_traceability_service.domain.spi.IRestaurantPersistencePort;
import com.traceability.food_court_traceability_service.ports.persistency.mongo.repository.IRestaurantFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IRestaurantFeignAdapter implements IRestaurantPersistencePort {
    private final IRestaurantFeign restaurantFeign;

    @Override
    public List<Long> getEmployeesByOwner(String ownerId) {
        return restaurantFeign.getEmployeesByOwner(ownerId);
    }
}