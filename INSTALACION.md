# Manual de Instalacion

Guia visual y rapida para levantar el proyecto en Windows con Oracle XE en Docker.

---

## Vista general

| Paso | Objetivo |
| --- | --- |
| 1 | Instalar JDK 21 |
| 2 | Instalar Maven |
| 3 | Configurar variables de entorno |
| 4 | Levantar Oracle XE en Docker |
| 5 | Crear usuario de aplicacion en Oracle |
| 6 | Verificar configuracion de Spring Boot |
| 7 | Compilar y ejecutar la aplicacion |

---

## Requisitos previos

- Windows con permisos para instalar software.
- Docker Desktop iniciado.
- Cuenta Oracle para autenticar en Oracle Container Registry.

> Nota: la imagen oficial de Oracle requiere autenticacion y aceptacion de licencia.

---

## 1. Instalar JDK 21

Valida que Java 21 este disponible:

```powershell
java --version
```

---

## 2. Instalar Apache Maven

Verifica la instalacion:

```powershell
mvn --version
```

---

## 3. Configurar variables de entorno en Windows

Configura estas variables en el sistema (ajusta rutas segun tu instalacion):

```text
JAVA_HOME = C:\Program Files\Java\jdk-21
MAVEN_HOME = C:\maven\apache-maven-3.9.14
PATH += %JAVA_HOME%\bin;%MAVEN_HOME%\bin
```

> Recomendacion: cierra y abre una nueva terminal despues de guardar variables.

---

## 4. Instalar y ejecutar Oracle XE en Docker

### 4.1 Iniciar sesion en Oracle Container Registry

```powershell
docker login container-registry.oracle.com
```

### 4.2 Descargar imagen

```powershell
docker pull container-registry.oracle.com/database/express
```

### 4.3 Ejecutar contenedor

```powershell
docker run -d `
  --name oracle-xe `
  -p 1521:1521 -p 5500:5500 `
  -e ORACLE_PWD=MyStrongPassword123 `
  container-registry.oracle.com/database/express
```

---

## 5. Crear usuario de aplicacion en Oracle

Cuando la base de datos este lista, crea el usuario y sus permisos:

```powershell
# Conectar con SQL*Plus dentro del contenedor
docker exec -it oracle-xe sqlplus system/MyStrongPassword123@localhost:1521/XEPDB1

# Ejecutar en SQL*Plus
CREATE USER app_user IDENTIFIED BY app_pass123;
GRANT CONNECT, RESOURCE TO app_user;
ALTER USER app_user QUOTA UNLIMITED ON USERS;

# Salir
EXIT
```

---

## 6. Configuracion de la aplicacion

La configuracion de datasource ya esta definida en [src/main/resources/application.properties](src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=app_user
spring.datasource.password=app_pass123

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
```

Si cambias usuario o clave en Oracle, actualiza esos valores.

---

## 7. Compilar y ejecutar

### 7.1 Generar CSS con Tailwind

```powershell
# Ejecutar en la carpeta frontend
npm i
npm run build
```

### 7.2 Compilar y correr Spring Boot

```powershell
mvn clean install
mvn spring-boot:run
```

---

## Checklist final

- Java 21 detectado.
- Maven detectado.
- Oracle XE corriendo en Docker.
- Usuario app_user creado con permisos.
- Aplicacion inicia sin errores.