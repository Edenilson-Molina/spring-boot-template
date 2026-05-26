package com.template.spring_boot.security.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Size(max = 150, message = "El email no puede tener mas de 150 caracteres")
    String email,
    @NotBlank(message = "El password es obligatorio")
    @Size(min = 6, max = 120, message = "El password debe tener entre 6 y 120 caracteres")
    String password
) {
}
