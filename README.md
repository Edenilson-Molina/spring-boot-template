[![Java 21](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white)](#)
[![Spring Boot 4](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](#)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-2E7D32?style=for-the-badge&logo=springsecurity&logoColor=white)](#)
[![Oracle XE](https://img.shields.io/badge/Oracle-XE-F80000?style=for-the-badge&logo=oracle&logoColor=white)](#)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](#)

# Plantilla Spring Boot
Plantilla base para APIs REST con Spring Boot y Oracle lista para iniciar proyectos reales con seguridad JWT, seed inicial y arquitectura por capas.

## Lo que incluye
- Java 21
- Spring Boot 4
- Spring Data JPA + Hibernate
- Spring Security + JWT (login/logout)
- Manejo global de errores (formato `code`, `message`)
- Validación de entrada en requests
- Seed de seguridad configurable (permisos, roles, usuarios)
- Base de datos Oracle (XE / XEPDB1)

## Inicio rapido
1. Configura tu conexion en `src/main/resources/application.properties`.
2. Ejecuta:
```powershell
    ./mvnw clean install
    ./mvnw spring-boot:run
```

3. Login de prueba:
```http
    POST /api/auth/login
    Content-Type: application/json

    {
        "email": "admin@example.com",
        "password": "pass123"
    }
```

## Enfoque
Codigo simple, estructura mantenible y arranque rapido para reutilizar la plantilla en nuevos proyectos.
