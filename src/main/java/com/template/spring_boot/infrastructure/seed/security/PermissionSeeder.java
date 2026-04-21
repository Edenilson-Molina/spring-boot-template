package com.template.spring_boot.infrastructure.seed.security;

import com.template.spring_boot.infrastructure.seed.DataSeeder;
import com.template.spring_boot.security.model.Permission;
import com.template.spring_boot.security.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionSeeder implements DataSeeder {

    private final PermissionRepository permissionRepository;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String getName() {
        return "PermissionSeeder";
    }

    @Override
    public void seed() {
        SecuritySeedCatalog.PERMISSIONS.forEach(seed -> permissionRepository.findByName(seed.name())
            .orElseGet(() -> permissionRepository.save(
                Permission.builder()
                    .name(seed.name())
                    .description(seed.description())
                    .build()
            )));

        log.info("Security permissions seeded: {}", SecuritySeedCatalog.PERMISSIONS.size());
    }
}