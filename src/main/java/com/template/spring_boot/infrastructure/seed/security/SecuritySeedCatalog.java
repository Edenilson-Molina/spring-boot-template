package com.template.spring_boot.infrastructure.seed.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SecuritySeedCatalog {

    private SecuritySeedCatalog() {
    }

    public static final List<PermissionSeed> PERMISSIONS = List.of(
        new PermissionSeed("READ_PUBLIC", "Permite lectura de recursos publicos"),
        new PermissionSeed("MANAGE_USERS", "Permite administrar usuarios")
    );

    public static final List<RoleSeed> ROLES = List.of(
        new RoleSeed("ROLE_ADMIN", Set.of("READ_PUBLIC", "MANAGE_USERS")),
        new RoleSeed("ROLE_GUEST", Set.of("READ_PUBLIC"))
    );

    public static final List<UserSeed> USERS = List.of(
        new UserSeed("admin", "admin@local.dev", "Admin", "Principal", "Admin123*", Set.of("ROLE_ADMIN")),
        new UserSeed("guest", "guest@local.dev", "Usuario", "Invitado", "Guest123*", Set.of("ROLE_GUEST"))
    );

    static {
        validateCatalogReferences();
    }

    private static void validateCatalogReferences() {
        Set<String> permissionNames = PERMISSIONS.stream().map(PermissionSeed::name)
            .collect(Collectors.toCollection(HashSet::new));

        Set<String> roleNames = ROLES.stream().map(RoleSeed::name)
            .collect(Collectors.toCollection(HashSet::new));

        ROLES.forEach(role -> role.permissions().forEach(permissionName -> {
            if (!permissionNames.contains(permissionName)) {
                throw new IllegalStateException(
                    "Referencia invalida en catalogo: el rol "
                        + role.name()
                        + " usa el permiso inexistente "
                        + permissionName
                );
            }
        }));

        USERS.forEach(user -> user.roles().forEach(roleName -> {
            if (!roleNames.contains(roleName)) {
                throw new IllegalStateException(
                    "Referencia invalida en catalogo: el usuario "
                        + user.username()
                        + " usa el rol inexistente "
                        + roleName
                );
            }
        }));
    }

    public record PermissionSeed(String name, String description) {
    }

    public record RoleSeed(String name, Set<String> permissions) {
    }

    public record UserSeed(String username, String email, String firstName,
                           String lastName, String rawPassword, Set<String> roles) {
    }
}