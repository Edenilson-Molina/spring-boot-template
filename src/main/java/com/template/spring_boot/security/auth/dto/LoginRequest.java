package com.template.spring_boot.security.auth.dto;

public record LoginRequest(
    String username,
    String password
) {
}
