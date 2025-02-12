package com.traceability.food_court_traceability_service.domain.api.usecasse;

import com.traceability.food_court_traceability_service.domain.api.IPurchaseHistoryServicePort;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;
import com.traceability.food_court_traceability_service.domain.spi.IRestaurantPersistencePort;
import com.traceability.food_court_traceability_service.domain.spi.IUserPersistencePort;
import com.traceability.food_court_traceability_service.domain.utils.TimeUtils;

import java.time.Duration;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.traceability.food_court_traceability_service.domain.utils.TimeUtils.formatSecondsToTime;
import static com.traceability.food_court_traceability_service.domain.utils.TraceabilityUtils.*;

public class PurchaseHistoryUseCase implements IPurchaseHistoryServicePort {
    IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort;
    IUserPersistencePort userPersistencePort;
    IRestaurantPersistencePort restaurantPersistencePort;

    public PurchaseHistoryUseCase(IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort, IUserPersistencePort userPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.purchaseHistoryPersistencePort = purchaseHistoryPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void generatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryPersistencePort.generatePurchaseHistory(purchaseHistory);
    }

    @Override
    public List<PurchaseHistory> getStatusHistoryByClientId() {
        String clientId = userPersistencePort.getUserId();
        return purchaseHistoryPersistencePort.findByClientId(clientId);
    }

    @Override
    public Map<String, String> calculateProcessingTimePerOrder() {
        String ownerId = userPersistencePort.getUserId();
        List<Long> employeeIds = restaurantPersistencePort.getEmployeesByOwner(ownerId);
        List<String> employeeIdsAsString = employeeIds.stream().map(String::valueOf).toList();
        List<PurchaseHistory> purchaseHistories = purchaseHistoryPersistencePort.findAll();

        return calculateOrderProcessingTimes(purchaseHistories, employeeIdsAsString);
    }

    @Override
    public Map<Long, String> calculateAverageProcessingTimePerEmployee() {
        String ownerId = userPersistencePort.getUserId();
        List<Long> employeeIds = restaurantPersistencePort.getEmployeesByOwner(ownerId);
        List<PurchaseHistory> purchaseHistories = purchaseHistoryPersistencePort.findAll();
        Map<String, List<PurchaseHistory>> historiesByOrder = groupPurchaseHistoriesByOrderId(purchaseHistories);
        Map<Long, List<Long>> employeeProcessingTimes = calculateProcessingTimesByEmployee(historiesByOrder, employeeIds);
        Map<Long, String> averageProcessingTimePerEmployee = calculateAverageProcessingTimes(employeeProcessingTimes);
        return sortByProcessingTime(averageProcessingTimePerEmployee);
    }

    private Map<String, String> calculateOrderProcessingTimes(List<PurchaseHistory> purchaseHistories, List<String> employeeIds) {
        return purchaseHistories.stream()
                .filter(history -> employeeIds.contains(history.getEmployeeId()))
                .collect(Collectors.groupingBy(PurchaseHistory::getOrderId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> calculateProcessingTime(entry.getValue())
                ));
    }

    private String calculateProcessingTime(List<PurchaseHistory> histories) {
        histories.sort(Comparator.comparing(PurchaseHistory::getStatusDate));

        if (histories.isEmpty()) {
            return ZERO;
        }

        LocalDateTime startTime = histories.get(0).getStatusDate();
        LocalDateTime endTime = histories.get(histories.size() - 1).getStatusDate();

        return TimeUtils.calculateTimeDifference(startTime, endTime);
    }

    private Map<String, List<PurchaseHistory>> groupPurchaseHistoriesByOrderId(List<PurchaseHistory> purchaseHistories) {
        return purchaseHistories.stream()
                .collect(Collectors.groupingBy(PurchaseHistory::getOrderId));
    }

    private Map<Long, List<Long>> calculateProcessingTimesByEmployee(
            Map<String, List<PurchaseHistory>> historiesByOrder, List<Long> employeeIds) {
        Map<Long, List<Long>> employeeProcessingTimes = new HashMap<>();

        for (List<PurchaseHistory> orderHistories : historiesByOrder.values()) {
            orderHistories.sort(Comparator.comparing(PurchaseHistory::getStatusDate));

            if (orderHistories.isEmpty()) {
                continue;
            }

            PurchaseHistory first = orderHistories.get(0);
            PurchaseHistory last = orderHistories.get(orderHistories.size() - 1);
            Long employeeId = parseEmployeeId(last.getEmployeeId());

            if (isValidOrder(first, last) && employeeId != null && employeeIds.contains(employeeId)) {
                long processingTimeInSeconds = calculateProcessingTimeInSeconds(first, last);
                employeeProcessingTimes.computeIfAbsent(employeeId, k -> new ArrayList<>()).add(processingTimeInSeconds);
            }
        }

        return employeeProcessingTimes;
    }

    private boolean isValidOrder(PurchaseHistory first, PurchaseHistory last) {
        return PENDING.equals(first.getNewStatus()) && DELIVERED.equals(last.getNewStatus());
    }

    private long calculateProcessingTimeInSeconds(PurchaseHistory first, PurchaseHistory last) {
        return Duration.between(first.getStatusDate(), last.getStatusDate()).getSeconds();
    }

    private Long parseEmployeeId(String employeeIdStr) {
        try {
            return Long.parseLong(employeeIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Map<Long, String> calculateAverageProcessingTimes(Map<Long, List<Long>> employeeProcessingTimes) {
        return employeeProcessingTimes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> formatSecondsToTime(
                                (long) entry.getValue().stream().mapToLong(Long::longValue).average().orElse(0)
                        )
                ));
    }

    private Map<Long, String> sortByProcessingTime(Map<Long, String> averageProcessingTimePerEmployee) {
        return averageProcessingTimePerEmployee.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
