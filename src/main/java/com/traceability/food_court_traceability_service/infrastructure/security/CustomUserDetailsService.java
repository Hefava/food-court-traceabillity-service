package com.traceability.food_court_traceability_service.infrastructure.security;

import com.traceability.food_court_traceability_service.ports.application.http.dto.UserResponse;
import com.traceability.food_court_traceability_service.ports.persistency.mongo.repository.IUserFeign;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserFeign userFeign;

    public CustomUserDetailsService(IUserFeign userFeign) {
        this.userFeign = userFeign;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserResponse userResponse = userFeign.validateToken(token);

        if (userResponse == null || userResponse.getUsername() == null) {
            throw new UsernameNotFoundException("Token inválido o usuario no encontrado");
        }

        return new org.springframework.security.core.userdetails.User(
                userResponse.getUsername(),
                "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userResponse.getRole()))
        );
    }
}