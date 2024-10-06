package com.chessrating.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ValidateActionAllowedService {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateActionAllowedService.class);

    public boolean validateActionAllowed(String role, String playerId) {
        boolean isUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role::equals);
        if (isUserRole) {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!playerId.equals(userId)) {
                LOG.error("[ValidateActionAllowed::validateActionAllowed] Cannot update user rating with id {}", playerId);
                return false;
            }
        }
        return true;
    }
}
