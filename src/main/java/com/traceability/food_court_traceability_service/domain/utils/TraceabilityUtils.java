package com.traceability.food_court_traceability_service.domain.utils;

public class TraceabilityUtils {

    public static final String ROLE_OWNER = "OWNER";

    public static final String ROLE_CUSTOMER = "CLIENT";

    public static final String AUTHORIZATION = "Authorization";

    public static final String ZERO = "0";

    public static final String PENDING = "pendiente";

    public static final String DELIVERED = "entregado";

    private TraceabilityUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}
