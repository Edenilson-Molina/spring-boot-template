package com.template.spring_boot.security.auth.controller;

import com.template.spring_boot.security.auth.dto.AuthResponse;
import com.template.spring_boot.security.auth.dto.LoginRequest;
import com.template.spring_boot.security.auth.dto.MessageResponse;
import com.template.spring_boot.security.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Header Authorization invalido");
        }

        String token = authorization.substring(7);
        String message = authService.logout(token);
        return ResponseEntity.ok(new MessageResponse(message));
    }
}
