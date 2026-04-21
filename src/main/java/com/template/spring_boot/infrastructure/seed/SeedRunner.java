package com.template.spring_boot.infrastructure.seed;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SeedRunner {

    private final List<DataSeeder> seeders;

    @Bean
    public CommandLineRunner runSeeders(
        @Value("${app.security.seed.enabled:true}") boolean seedEnabled
    ) {
        return args -> {
            if (!seedEnabled) {
                log.info("Data seed is disabled by property app.security.seed.enabled=false");
                return;
            }

            seeders.stream()
                .sorted(Comparator.comparingInt(DataSeeder::getOrder))
                .forEach(seeder -> {
                    log.info("Running seeder: {}", seeder.getName());
                    seeder.seed();
                });

            log.info("All seeders completed successfully");
        };
    }
}