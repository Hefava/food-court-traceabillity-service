package com.traceability.food_court_traceability_service.domain.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {

    public static String calculateTimeDifference(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return "N/A";
        }

        Duration duration = Duration.between(start, end);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private TimeUtils() {throw new AssertionError("Cannot instantiate this class"); }
}
