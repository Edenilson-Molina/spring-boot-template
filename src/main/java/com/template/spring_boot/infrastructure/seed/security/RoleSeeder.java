package com.template.spring_boot.infrastructure.seed.security;

import com.template.spring_boot.infrastructure.seed.DataSeeder;
import com.template.spring_boot.security.model.Permission;
import com.template.spring_boot.security.model.Role;
import com.template.spring_boot.security.repository.PermissionRepository;
import com.template.spring_boot.security.repository.RoleRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleSeeder implements DataSeeder {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public String getName() {
        return "RoleSeeder";
    }

    @Override
    public void seed() {
        SecuritySeedCatalog.ROLES.forEach(seed -> roleRepository.findByName(seed.name())
            .orElseGet(() -> roleRepository.save(
                Role.builder()
                    .name(seed.name())
                    .permissions(seed.permissions().stream()
                        .map(this::getRequiredPermission)
                        .collect(Collectors.toCollection(HashSet::new)))
                    .build()
            )));

        log.info("Security roles seeded: {}", SecuritySeedCatalog.ROLES.size());
    }

    private Permission getRequiredPermission(String permissionName) {
        return permissionRepository.findByName(permissionName)
            .orElseThrow(() -> new IllegalStateException("Permiso no encontrado en BD: " + permissionName));
    }
}