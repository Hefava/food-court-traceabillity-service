package com.traceability.food_court_traceability_service.domain.utils;

public class TraceabilityUtils {

    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

    public static final String ROLE_OWNER = "OWNER";

    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    public static final String ROLE_CUSTOMER = "CLIENT";

    public static final String AUTHORIZATION = "Authorization";

    private TraceabilityUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}
