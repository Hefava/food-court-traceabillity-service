package com.traceability.food_court_traceability_service;

import com.traceability.food_court_traceability_service.domain.api.usecasse.PurchaseHistoryUseCase;
import com.traceability.food_court_traceability_service.domain.model.PurchaseHistory;
import com.traceability.food_court_traceability_service.domain.spi.IPurchaseHistoryPersistencePort;
import com.traceability.food_court_traceability_service.domain.spi.IRestaurantPersistencePort;
import com.traceability.food_court_traceability_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PurchaseHistoryUseCaseTest {

    @Mock
    private IPurchaseHistoryPersistencePort purchaseHistoryPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private PurchaseHistoryUseCase purchaseHistoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generatePurchaseHistory_ShouldCallPersistenceLayer() {
        // Given
        PurchaseHistory purchaseHistory = new PurchaseHistory(
                "order1", "123", "client@example.com", LocalDateTime.now(),
                "CREATED", "PENDING", "1", "employee@example.com"
        );

        // When
        purchaseHistoryUseCase.generatePurchaseHistory(purchaseHistory);

        // Then
        verify(purchaseHistoryPersistencePort).generatePurchaseHistory(purchaseHistory);
    }

    @Test
    void getStatusHistoryByClientId_ShouldReturnHistoryList() {
        // Given
        String clientId = "user123";
        List<PurchaseHistory> historyList = List.of(
                new PurchaseHistory("order1", clientId, "client@example.com", LocalDateTime.now(),
                        "CREATED", "PENDING", "1", "employee@example.com")
        );

        when(userPersistencePort.getUserId()).thenReturn(clientId);
        when(purchaseHistoryPersistencePort.findByClientId(clientId)).thenReturn(historyList);

        // When
        List<PurchaseHistory> result = purchaseHistoryUseCase.getStatusHistoryByClientId();

        // Then
        assertEquals(historyList, result);
        verify(purchaseHistoryPersistencePort).findByClientId(clientId);
    }

    @Test
    void calculateProcessingTimePerOrder_ShouldReturnCorrectTimes() {
        // Given
        String ownerId = "owner123";
        List<Long> employeeIds = List.of(1L, 2L);
        when(userPersistencePort.getUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.getEmployeesByOwner(ownerId)).thenReturn(employeeIds);

        List<PurchaseHistory> historyList = List.of(
                new PurchaseHistory("order1", "client1", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 12, 0), "CREATED", "PENDING", "1", "employee@example.com"),
                new PurchaseHistory("order1", "client1", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 12, 30), "PENDING", "DELIVERED", "1", "employee@example.com")
        );
        when(purchaseHistoryPersistencePort.findAll()).thenReturn(historyList);

        // When
        Map<String, String> result = purchaseHistoryUseCase.calculateProcessingTimePerOrder();

        // Then
        assertEquals(1, result.size());
        assertEquals("00:30:00", result.get("order1"));
    }

    @Test
    void calculateAverageProcessingTimePerEmployee_ShouldReturnSortedTimes() {
        // Given
        String ownerId = "owner123";
        List<Long> employeeIds = List.of(1L, 2L);
        when(userPersistencePort.getUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.getEmployeesByOwner(ownerId)).thenReturn(employeeIds); // Devuelve Longs

        List<PurchaseHistory> historyList = List.of(
                new PurchaseHistory("order1", "client1", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 12, 0), "CREATED", "PENDING", "1", "employee1@example.com"),
                new PurchaseHistory("order1", "client1", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 12, 30), "PENDING", "DELIVERED", "1", "employee1@example.com"),
                new PurchaseHistory("order2", "client2", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 13, 0), "CREATED", "PENDING", "2", "employee2@example.com"),
                new PurchaseHistory("order2", "client2", "client@example.com",
                        LocalDateTime.of(2024, 2, 10, 13, 45), "PENDING", "DELIVERED", "2", "employee2@example.com")
        );

        when(purchaseHistoryPersistencePort.findAll()).thenReturn(historyList);

        // When
        Map<Long, String> result = purchaseHistoryUseCase.calculateAverageProcessingTimePerEmployee();

        // Then
        assertEquals(2, 2);
        assertEquals("00:30:00", result.get(1L));
        assertEquals("00:45:00", result.get(2L));
    }
}