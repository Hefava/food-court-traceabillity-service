package com.traceability.food_court_traceability_service.ports.persistency.mongo.repository;

import com.traceability.food_court_traceability_service.ports.application.http.dto.UserResponse;
import com.traceability.food_court_traceability_service.ports.feign.FeingClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8082", configuration = FeingClientConfiguration.class)
public interface IUserFeign {

    @GetMapping("/sign-up/validate-role-owner")
    Boolean validateRoleOwner(@RequestParam Long userID);

    @PostMapping("/auth/validate-token/{token}")
    UserResponse validateToken(@PathVariable String token);

    @GetMapping("/sign-up/get-phone-number")
    String getPhoneNumber(@RequestParam Long userID);

    @GetMapping("/sign-up/get-email]")
    String getEmail(@RequestParam Long userID);
}