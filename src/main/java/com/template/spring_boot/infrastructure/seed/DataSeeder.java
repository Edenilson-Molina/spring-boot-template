package com.template.spring_boot.infrastructure.seed;

public interface DataSeeder {
    int getOrder();
    String getName();

    void seed();
}