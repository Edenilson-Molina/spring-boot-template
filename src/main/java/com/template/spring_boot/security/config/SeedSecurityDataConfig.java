package com.template.spring_boot.security.config;

import com.template.spring_boot.security.model.Permission;
import com.template.spring_boot.security.model.Role;
import com.template.spring_boot.security.model.User;
import com.template.spring_boot.security.repository.PermissionRepository;
import com.template.spring_boot.security.repository.RoleRepository;
import com.template.spring_boot.security.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedSecurityDataConfig {

    @Bean
    public CommandLineRunner seedSecurityData(
        PermissionRepository permissionRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.security.seed.enabled:true}") boolean seedEnabled
    ) {
        return args -> {
            if (!seedEnabled) {
                return;
            }

            Permission readPublic = permissionRepository.findByName("READ_PUBLIC")
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .name("READ_PUBLIC")
                                    .description("Permite lectura de recursos publicos")
                                    .build()
                    ));

            Permission manageUsers = permissionRepository.findByName("MANAGE_USERS")
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .name("MANAGE_USERS")
                                    .description("Permite administrar usuarios")
                                    .build()
                    ));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("ROLE_ADMIN")
                                    .permissions(new HashSet<>(Set.of(readPublic, manageUsers)))
                                    .build()
                    ));

            Role guestRole = roleRepository.findByName("ROLE_GUEST")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("ROLE_GUEST")
                                    .permissions(new HashSet<>(Set.of(readPublic)))
                                    .build()
                    ));

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "admin",
                    "admin@local.dev",
                    "Admin",
                    "Principal",
                    "Admin123*",
                    Set.of(adminRole)
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "guest",
                    "guest@local.dev",
                    "Usuario",
                    "Invitado",
                    "Guest123*",
                    Set.of(guestRole)
            );
        };
    }

    private void createUserIfNotExists(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String email,
            String firstName,
            String lastName,
            String rawPassword,
            Set<Role> roles
    ) {
        userRepository.findByUsername(username).orElseGet(() -> userRepository.save(
                User.builder()
                        .username(username)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(passwordEncoder.encode(rawPassword))
                        .enabled(true)
                        .roles(new HashSet<>(roles))
                        .build()
        ));
    }
}
