package com.traceability.food_court_traceability_service.ports.persistency.mongo.repository;

import com.traceability.food_court_traceability_service.ports.feign.FeingClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "place-service", url = "http://localhost:8083", configuration = FeingClientConfiguration.class)
public interface IRestaurantFeign {
    @GetMapping("/restaurants/owner-employees")
    List<Long> getEmployeesByOwner(@RequestParam String ownerId);
}
