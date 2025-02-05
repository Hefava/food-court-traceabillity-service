package com.traceability.food_court_traceability_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoodCourtTraceabilityServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodCourtTraceabilityServiceApplication.class, args);
	}
}
