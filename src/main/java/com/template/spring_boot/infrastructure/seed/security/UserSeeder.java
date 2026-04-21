package com.template.spring_boot.infrastructure.seed.security;

import com.template.spring_boot.infrastructure.seed.DataSeeder;
import com.template.spring_boot.security.model.Role;
import com.template.spring_boot.security.model.User;
import com.template.spring_boot.security.repository.RoleRepository;
import com.template.spring_boot.security.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSeeder implements DataSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public String getName() {
        return "UserSeeder";
    }

    @Override
    public void seed() {
        SecuritySeedCatalog.USERS.forEach(seed -> userRepository.findByUsername(seed.username())
            .orElseGet(() -> userRepository.save(
                User.builder()
                    .username(seed.username())
                    .email(seed.email())
                    .firstName(seed.firstName())
                    .lastName(seed.lastName())
                    .password(passwordEncoder.encode(seed.rawPassword()))
                    .enabled(true)
                    .roles(new HashSet<>(seed.roles().stream()
                        .map(this::getRequiredRole)
                        .collect(Collectors.toSet())))
                    .build()
            )));

        log.info("Security users seeded: {}", SecuritySeedCatalog.USERS.size());
    }

    private Role getRequiredRole(String roleName) {
        return roleRepository.findByName(roleName)
            .orElseThrow(() -> new IllegalStateException("Rol no encontrado en BD: " + roleName));
    }
}