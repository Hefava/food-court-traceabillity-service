package com.traceability.food_court_traceability_service.ports.application.http.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String role;
    private Boolean authorized;
}
