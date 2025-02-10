package com.traceability.food_court_traceability_service.ports.persistency.mongo.adapter;

import com.traceability.food_court_traceability_service.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserPersistencePort {

    @Override
    public String getUserId() {
        UserDetails userId = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return String.valueOf(userId.getUsername());
    }
}
