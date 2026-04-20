package com.template.spring_boot.security.auth.dto;

import java.time.Instant;

public record AuthResponse(
    String token,
    String tokenType,
    Instant expiresAt,
    UserPayload user
) {
}
