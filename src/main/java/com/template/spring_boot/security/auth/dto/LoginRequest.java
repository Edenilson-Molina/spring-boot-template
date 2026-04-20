package com.template.spring_boot.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 100, message = "El username debe tener entre 3 y 100 caracteres")
    String username,
    @NotBlank(message = "El password es obligatorio")
    @Size(min = 6, max = 120, message = "El password debe tener entre 6 y 120 caracteres")
    String password
) {
}
