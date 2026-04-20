package com.template.spring_boot.security.auth.service;

import com.template.spring_boot.security.auth.dto.AuthResponse;
import com.template.spring_boot.security.auth.dto.LoginRequest;
import com.template.spring_boot.security.auth.dto.UserPayload;
import com.template.spring_boot.security.jwt.JwtService;
import com.template.spring_boot.security.jwt.JwtTokenBlacklistService;
import com.template.spring_boot.security.model.User;
import com.template.spring_boot.security.repository.UserRepository;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtTokenBlacklistService tokenBlacklistService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService,
            JwtTokenBlacklistService tokenBlacklistService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        Instant expiresAt = jwtService.extractExpiration(token).toInstant();

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .sorted()
                .toList();

        List<String> permissions = user.getRoles().stream()
                .map(role -> role.getPermissions())
                .flatMap(Collection::stream)
                .map(permission -> permission.getName())
                .collect(java.util.stream.Collectors.toCollection(TreeSet::new))
                .stream()
                .toList();

        UserPayload userPayload = new UserPayload(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                roles,
                permissions
        );

        return new AuthResponse(token, "Bearer", expiresAt, userPayload);
    }

    public String logout(String token) {
        Instant expiresAt = jwtService.extractExpiration(token).toInstant();
        tokenBlacklistService.blacklist(token, expiresAt);
        return "Sesion cerrada correctamente";
    }
}
