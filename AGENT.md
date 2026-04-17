# AGENT.md

Version: 1.0
------------------------------------------------------------------------

## 1. Propósito

Guía de operación para un agente de código en un proyecto Spring Boot
REST. El objetivo es generar código consistente, seguro y alineado a la
arquitectura definida.
------------------------------------------------------------------------

## 2. Base del Proyecto

-   Java 21
-   Spring Boot
-   Maven (wrapper disponible)
-   Paquete raíz: `com.template.spring_boot`
-   Spring Data JPA
-   Spring Security
-   Base de datos: Oracle (XE / XEPDB1)
-   API REST (JSON únicamente)
------------------------------------------------------------------------

## 3. Alcance

Incluye: 
- Controllers, Services, Repositories
- Entities, DTOs
- Validación y manejo de excepciones
- Seguridad de endpoints
- Tests básicos

Excluye por defecto: 
- UI (Thymeleaf, frontend)
- Lógica de negocio no definida por el usuario
------------------------------------------------------------------------

## 4. Prioridad de Contexto

Leer solo lo necesario, en este orden:

1.  `pom.xml`
2.  `application.properties`
3.  Código en `src/main/java/com/template/spring_boot/**`
4.  Tests si son relevantes

Reglas: 
- No escanear todo el repositorio innecesariamente
- Reutilizar convenciones existentes
- Preguntar solo si falta información crítica
------------------------------------------------------------------------

## 5. Arquitectura (OBLIGATORIO)

Separación estricta por capas:

-   Controller → maneja request/response
-   Service → lógica de negocio
-   Repository → acceso a datos

Reglas: 
- No lógica de negocio en Controllers
- No acceso directo a DB desde Controllers
- No exponer Entities en responses (usar DTOs)
------------------------------------------------------------------------

## 6. Convenciones de Paquetes

-   `entity`
-   `repository`
-   `service`
-   `controller`
-   `dto`
-   `exception`
-   `config`

Naming:

-   `UserEntity`
-   `UserRepository`
-   `UserService`
-   `UserController`
-   `UserRequest`, `UserResponse` o `UserDto`
------------------------------------------------------------------------

## 7. Reglas REST

-   Usar rutas en plural (`/users`, `/orders`)
-   Métodos HTTP correctos: GET, POST, PUT, DELETE
-   Respuestas:
    -   HTTP status adecuados
    -   JSON estructurado y consistente
------------------------------------------------------------------------

## 8. Base de Datos (Oracle)

-   Usar JPA/Hibernate
-   Evitar SQL nativo innecesario
-   Usar secuencias para IDs
-   Mapear correctamente (`@Entity`, `@Table`, `@Column`)
-   Evitar queries ineficientes
------------------------------------------------------------------------

## 9. Seguridad y Validación

-   Definir endpoints públicos vs protegidos
-   Proteger operaciones de escritura
-   Validar inputs con anotaciones (`@Valid`, etc.)
-   Manejo centralizado de errores (`@ControllerAdvice`)
------------------------------------------------------------------------

## 10. Formato de Datos

### Fechas

-   Usar ISO 8601 internamente (`LocalDate`, `LocalDateTime`)
-   No usar `Date`
-   Mantener consistencia en toda la API

### Zona Horaria

-   Definir zona horaria explícita (preferiblemente UTC)
-   No depender de la zona del servidor

### Decimales

-   Usar `BigDecimal` para valores numéricos críticos
-   No usar `double` o `float` en lógica de negocio
-   Definir escala (ej: 2 decimales)
-   Usar `RoundingMode.HALF_UP`
------------------------------------------------------------------------

## 11. Nomenclatura

### Java

-   Variables: `camelCase` → `userId`, `createdAt`
-   Clases: `PascalCase` → `UserEntity`
-   Constantes: `UPPER_CASE`
-   Booleanos: `isActive`, `hasPermission`

### Oracle

-   Tablas: `USERS`, `ORDERS`
-   Columnas: `USER_ID`, `CREATED_AT`

### IDs

-   PK: `ID`
-   FK: `<ENTITY>_ID`
-   Usar secuencias:
    ``` java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ```

### Mapeo

``` java
@Column(name = "CREATED_AT")
private LocalDateTime createdAt;
```

Reglas: 
- Mantener consistencia entre DB → Entity → DTO → API
- No mezclar estilos de nombres
- No usar abreviaciones ambiguas
------------------------------------------------------------------------

## 12. Flujo del Agente

1.  Confirmar alcance brevemente
2.  Leer contexto mínimo necesario
3.  Aplicar el cambio más pequeño posible
4.  Verificar:

    -   `mvn clean install`
    -   `mvn test` (si aplica)
5.  Reportar:

    -   Archivos modificados
    -   Resultado de validación
------------------------------------------------------------------------

## 13. Criterio de Finalización

Una tarea está completa si:
-   El proyecto compila
-   Tests pasan (si existen)
-   Se respeta la arquitectura
-   No se modifican archivos no relacionados
-   Seguridad y validación se mantienen o mejoran
------------------------------------------------------------------------

## 14. Inputs Requeridos

Antes de implementar:
-   Endpoint (ruta + método HTTP)
-   Request/Response
-   Modelo de datos
-   Validaciones
-   Seguridad (roles/permisos)

Si falta algo crítico, hacer una sola pregunta clara.
------------------------------------------------------------------------

## 15. Restricciones

-   No inventar lógica de negocio
-   No refactorizar código no relacionado
-   No ejecutar operaciones destructivas
-   No romper la estructura del proyecto
