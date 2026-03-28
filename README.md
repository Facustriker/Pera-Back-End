# Pera — Backend
 
> Sistema de administración de bancos virtuales y cuentas bancarias.  
> Proyecto final de **Seminario Integrador** — UTN Facultad Regional Mendoza  
> Ingeniería en Sistemas de Información · 2024
 
---
 
## Descripción
 
**Pera** es un sistema de software que permite a los usuarios crear y administrar bancos virtuales, así como abrir y gestionar cuentas bancarias pertenecientes a dichos bancos. Soporta transferencias de dinero ficticio entre cuentas de un mismo banco, y entre cuentas y su banco.
 
Pensado como herramienta de apoyo para actividades grupales que requieran administrar economía ficticia: juegos de mesa, división de gastos, gestión de saldos en grupos, etc.
 
Este repositorio contiene el **Back-End** del sistema.
 
---
 
## Autores
 
| Nombre | Legajo |
|---|---|
| Sebastián Andrés Ogás | 47075 |
| Facundo Gabriel Olea | 47855 |
 
**Docentes:** Dr. Ing. Raúl Omar Moralejo · Lic. Gustavo Manino
 
---
 
## Módulos del sistema
 
| Módulo | Descripción |
|---|---|
| **Usuarios** | Registración, autenticación, autorización, modificación de datos, cambio de contraseña y suscripción a Premium. Gestión de roles y permisos por parte de administradores. |
| **Transferencias** | Generación de transferencias entre cuentas bancarias y con su banco. Visualización de movimientos y auditoría por banqueros. |
| **Bancos** | Apertura y administración de bancos (aspectos generales, banqueros, dueño). Gestión de cuentas bancarias por titulares y administradores. |
| **Reportes** | Generación de informes: bancos por rango de cuentas, bancos abiertos/cerrados por período, montos transferidos por banco, usuarios registrados y suscripciones Premium. |
| **Parámetros** | Gestión de parámetros generales: límites de bancos y cuentas, planes Premium, medios de pago, símbolo de moneda por defecto. |
 
---
 
## Arquitectura
 
El sistema sigue una arquitectura **multicapa**:
 
```
┌─────────────────────────────────────┐
│           Clases Página (GUI)       │  ← Front-End (repo separado)
├─────────────────────────────────────┤
│         Clases Servicio (HTTP)      │  ← Front-End (repo separado)
├─────────────────────────────────────┤
│        Clases Controlador (HTTP)    │  ← Back-End ✅
├─────────────────────────────────────┤
│          Clases Experto (Lógica)    │  ← Back-End ✅
├─────────────────────────────────────┤
│        Clases Repositorio (BD)      │  ← Back-End ✅
├─────────────────────────────────────┤
│          Base de Datos              │  ← Back-End ✅
└─────────────────────────────────────┘
```
 
---
 
## Cómo ejecutar el proyecto
 
### Prerrequisitos
 
- Java 17+
- Maven 3.8+
- Base de datos configurada (ver `application.properties`)
 
### Pasos
 
```bash
# 1. Clonar el repositorio
git clone https://github.com/<usuario>/pera-backend.git
cd pera-backend
 
# 2. Configurar las variables de entorno o application.properties
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Editar con los datos de tu base de datos
 
# 3. Compilar y ejecutar
mvn spring-boot:run
```
 
El servidor levanta por defecto en `http://localhost:8080`.
 
---
 
## Configuración
 
Las principales propiedades configurables en `application.properties`:
 
```properties
# Base de datos
spring.datasource.url=jdbc:...
spring.datasource.username=...
spring.datasource.password=...
 
# JWT
jwt.secret=...
jwt.expiration=...
 
# Servidor
server.port=8080
```
 
---
 
## Estructura del proyecto
 
```
pera-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/pera/
│       │       ├── controllers/      # Controladores HTTP
│       │       ├── services/         # Lógica de negocio (Expertos)
│       │       ├── repositories/     # Acceso a base de datos
│       │       ├── entities/         # Entidades persistidas
│       │       ├── dtos/             # Objetos de transferencia de datos
│       │       └── config/           # Configuración de seguridad, beans, etc.
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```
 
---
 
## Seguridad y roles
 
El sistema cuenta con un esquema de **roles y permisos** granular:
 
| Rol | Descripción |
|---|---|
| `Usuario` | Puede gestionar sus propios bancos y cuentas bancarias, y realizar transferencias. |
| `Premium` | Amplía los límites de bancos y cuentas disponibles. |
| `Banquero` | Puede auditar transferencias y emitir dinero en los bancos que administra. |
| `Administrador` | Acceso completo al sistema: usuarios, bancos, parámetros y reportes. |
 
La autenticación se maneja mediante **JWT**. Los endpoints protegidos requieren el header:
 
```
Authorization: Bearer <token>
```
 
---
 
## Plan de pruebas
 
Se ejecutaron tres tipos de pruebas:
 
### Validación de ingreso de datos
| ID | Descripción |
|---|---|
| CP-VID-1 | Creación de banco con contraseña |
| CP-VID-2 | Transferencia de dinero entre cuentas |
| CP-VID-3 | Modificación de datos de un banco |
 
### Integración entre módulos
| ID | Descripción |
|---|---|
| CP-INT-1 | Apertura de cuenta y emisión de dinero |
| CP-INT-2 | Cambio de símbolo de moneda predeterminado |
| CP-INT-3 | Reporte de montos transferidos por banco |
 
### Seguridad
| ID | Descripción |
|---|---|
| CP-SEG-1 | Inicio de sesión como administrador |
| CP-SEG-2 | Creación de usuario con datos inválidos |
| CP-SEG-3 | Acceso a rutas sin permisos suficientes |
 
---
 
## Estrategia de branches
 
```
main          ← producción (estable)
├── sebasOgas ← desarrollo de Sebastián
└── facuOlea  ← desarrollo de Facundo
```
 
El versionado se gestiona mediante **GitHub**. Los cambios pasan por la rama personal del desarrollador antes de integrarse a `main`.
 
---
 
## Reportes disponibles
 
El módulo de reportes expone los siguientes informes:
 
- Cantidad de bancos existentes por rango de cantidad de cuentas en una fecha determinada
- Cantidad de bancos abiertos y cerrados en un rango de fechas, por intervalo de días
- Suma de dinero total transferida en un banco en un rango de fechas, por intervalo de días
- Cantidad de usuarios registrados en un rango de fechas, por intervalo de días
- Cantidad de suscripciones a Premium en un rango de fechas, por plan elegido
 
---
