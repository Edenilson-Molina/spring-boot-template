package com.template.spring_boot.security.auth.dto;

import java.util.List;

public record UserPayload(
    String username,
    String firstName,
    String lastName,
    List<String> roles,
    List<String> permissions
) {
}
