# DOCUMENTACIÓN TÉCNICA — MEDITRACK

**Sistema de Gestión de Tareas Clínicas Hospitalarias**

---

## ÍNDICE

1. [Información General del Proyecto](#1-información-general-del-proyecto)
2. [Objetivos del Proyecto](#2-objetivos-del-proyecto)
3. [Arquitectura del Sistema](#3-arquitectura-del-sistema)
4. [Modelo de Datos](#4-modelo-de-datos)
5. [Capa de Persistencia (Repository)](#5-capa-de-persistencia-repository)
6. [Capa de Lógica de Negocio (Service)](#6-capa-de-lógica-de-negocio-service)
7. [Capa de Presentación (Controller)](#7-capa-de-presentación-controller)
8. [Seguridad](#8-seguridad)
9. [Vistas Web (Thymeleaf)](#9-vistas-web-thymeleaf)
10. [Funcionalidades en Tiempo Real](#10-funcionalidades-en-tiempo-real)
11. [Testing](#11-testing)
12. [Configuración del Proyecto](#12-configuración-del-proyecto)
13. [Base de Datos](#13-base-de-datos)
14. [Decisiones de Diseño Importantes](#14-decisiones-de-diseño-importantes)
15. [Patrones de Diseño Aplicados](#15-patrones-de-diseño-aplicados)
16. [Flujo de Datos](#16-flujo-de-datos)
17. [API REST Completa](#17-api-rest-completa)
18. [Estructura de Directorios Completa](#18-estructura-de-directorios-completa)
19. [Convenciones de Código](#19-convenciones-de-código)
20. [Despliegue y Ejecución](#20-despliegue-y-ejecución)
21. [Pruebas y Validación](#21-pruebas-y-validación)
22. [Limitaciones Conocidas](#22-limitaciones-conocidas)
23. [Trabajo Futuro](#23-trabajo-futuro)
24. [Referencias y Bibliografía](#24-referencias-y-bibliografía)
25. [Anexos](#25-anexos)

---

## 1. Información General del Proyecto

| Campo | Valor |
|---|---|
| **Nombre** | MediTrack |
| **Descripción** | Sistema de gestión de tareas clínicas en entornos hospitalarios |
| **Tipo** | TFG — Trabajo Fin de Grado |
| **Autor** | Pedro Vela |
| **Fecha de desarrollo** | 2026 |
| **Repositorio** | https://github.com/pvelaalm/MediTrack.git |
| **Rama principal** | main |
| **Estado** | 100% completado (9 fases) |

### Descripción Ampliada

MediTrack es una aplicación web Java/Spring Boot diseñada para digitalizar y organizar la asignación y el seguimiento de tareas clínicas en un hospital. El sistema permite a tres tipos de usuarios (enfermería, medicina y supervisor) interactuar con la plataforma de acuerdo a sus responsabilidades, garantizando que cada tarea clínica esté correctamente asignada, trazada y supervisada.

La aplicación cubre el ciclo de vida completo de una tarea clínica: desde su creación por parte del personal médico, pasando por su ejecución por el personal de enfermería, hasta su supervisión en tiempo real por los responsables del área. El proyecto integra tecnologías modernas como Spring Security para control de acceso basado en roles, Thymeleaf para las vistas server-side, y Spring WebFlux con Server-Sent Events para actualización de datos en tiempo real.

---

## 2. Objetivos del Proyecto

### 2.1. Objetivo Principal

Desarrollar una aplicación web completa para la gestión de tareas clínicas hospitalarias, que demuestre el dominio de las tecnologías Java/Spring Boot y sirva como Trabajo Fin de Grado en el ámbito del desarrollo de software empresarial.

### 2.2. Objetivos Específicos

1. **Modelar el dominio hospitalario** mediante entidades JPA correctamente relacionadas (Turno, Paciente, Usuario, TareaClinica).
2. **Implementar seguridad con control de acceso basado en roles (RBAC)** usando Spring Security, diferenciando tres perfiles: ENFERMERIA, MEDICINA y SUPERVISOR.
3. **Exponer una API REST completa** para todas las entidades, con los verbos HTTP correctos y los códigos de respuesta apropiados.
4. **Desarrollar una interfaz web por rol** usando Thymeleaf y Bootstrap 5, adaptada a las necesidades de cada perfil de usuario.
5. **Incorporar actualización de datos en tiempo real** mediante Server-Sent Events (SSE) y Spring WebFlux, mostrando un dashboard de estadísticas que se actualiza automáticamente cada 5 segundos.
6. **Mantener una cobertura de tests robusta** con tests unitarios (Mockito) e integración (Spring Boot Test) para todas las capas.
7. **Documentar el proceso de desarrollo** con commits atómicos y mensajes descriptivos siguiendo la convención Conventional Commits.

### 2.3. Alcance del Proyecto

- Gestión completa (CRUD) de Turnos, Pacientes, Usuarios y Tareas Clínicas.
- Autenticación con formulario de login y encriptación BCrypt.
- Autorización a nivel de endpoint (API y vistas web) por rol.
- Interfaz web responsiva con Bootstrap 5 para los tres roles.
- Dashboard supervisor con estadísticas en tiempo real (SSE + WebFlux).
- Suite de 69 tests automatizados.

### 2.4. Limitaciones Reconocidas

- No es un sistema médico certificado ni cumple normativas como HL7 o FHIR.
- No gestiona datos clínicos sensibles (diagnósticos, medicamentos, historiales médicos completos).
- No dispone de persistencia de historial de reasignaciones.
- El SSE no está optimizado para uso con decenas de supervisores concurrentes.
- No incluye módulo de notificaciones push.

---

## 3. Arquitectura del Sistema

### 3.1. Patrón Arquitectónico

MediTrack implementa una **arquitectura monolítica modular** basada en el patrón **MVC (Model-View-Controller)**. Toda la lógica reside en un único artefacto desplegable (JAR), pero está internamente organizada en capas con responsabilidades bien definidas.

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                     │
│  ┌─────────────────────────┐  ┌──────────────────────────┐  │
│  │   Web Controllers       │  │   REST Controllers       │  │
│  │   (Thymeleaf + HTML)    │  │   (JSON API)             │  │
│  └─────────────────────────┘  └──────────────────────────┘  │
│           ↕ Modelos, DTOs               ↕ ResponseEntity    │
├─────────────────────────────────────────────────────────────┤
│                   CAPA DE NEGOCIO                           │
│  ┌─────────────────────────────────────────────────────┐    │
│  │   Services (@Service + @Transactional)              │    │
│  │   TurnoService | PacienteService | UsuarioService   │    │
│  │   TareaClinicaService | DashboardService            │    │
│  └─────────────────────────────────────────────────────┘    │
│           ↕ Entidades JPA                                   │
├─────────────────────────────────────────────────────────────┤
│                  CAPA DE PERSISTENCIA                       │
│  ┌─────────────────────────────────────────────────────┐    │
│  │   Repositories (Spring Data JPA interfaces)         │    │
│  │   TurnoRepo | PacienteRepo | UsuarioRepo            │    │
│  │   TareaClinicaRepo                                  │    │
│  └─────────────────────────────────────────────────────┘    │
│           ↕ JDBC / Hibernate                                │
├─────────────────────────────────────────────────────────────┤
│                  BASE DE DATOS                              │
│              PostgreSQL 15 — meditrack_db                   │
└─────────────────────────────────────────────────────────────┘
```

**Capa transversal de seguridad:**

```
┌─────────────────────────────────────────────────────────────┐
│              SPRING SECURITY (capa transversal)             │
│  Form Login → CustomUserDetailsService → BCrypt             │
│  FilterChain → Reglas de autorización por rol y ruta        │
└─────────────────────────────────────────────────────────────┘
```

### 3.2. Tecnologías Utilizadas

| Tecnología | Versión | Propósito | Justificación |
|---|---|---|---|
| **Java** | 21 (LTS) | Lenguaje principal | Versión LTS más reciente, soporte record, sealed classes, virtual threads |
| **Spring Boot** | 4.0.3 | Framework de aplicación | Autoconfiguración, embeds Tomcat, ecosistema maduro |
| **Spring Web MVC** | 4.0.3 | Capa REST y controladores web | Estándar de facto para APIs REST en el ecosistema Spring |
| **Spring Data JPA** | 4.0.3 | Capa de persistencia | Reduce boilerplate, genera implementaciones de repositorios automáticamente |
| **Hibernate** | 6.x | ORM (implementación JPA) | Mapeo objeto-relacional, generación de DDL, queries JPQL |
| **PostgreSQL** | 15+ | Base de datos relacional | ACID, robustez, soporta tipos avanzados, amplia adopción en producción |
| **Spring Security** | 4.0.3 | Autenticación y autorización | Integración nativa con Spring, RBAC, BCrypt integrado |
| **Thymeleaf** | 3.x | Motor de plantillas HTML | Se integra con Spring MVC, permite HTML válido, soporte `sec:` dialect |
| **Bootstrap** | 5.3.0 | Framework CSS | Responsivo, componentes listos, amplia documentación |
| **Bootstrap Icons** | 1.10.0 | Librería de iconos | Complementa Bootstrap 5 sin dependencias adicionales |
| **Chart.js** | 4.4.0 | Visualización de datos | Gráficos interactivos, ligero, fácil integración con JavaScript vanilla |
| **Spring WebFlux** | 4.0.3 | Programación reactiva / SSE | Permite Flux para streaming sin bloquear threads |
| **Project Reactor** | 3.x | Librería reactiva | Flux, Mono, manejo asíncrono de datos |
| **Lombok** | 1.18.42 | Reducción de boilerplate | `@Data`, `@Getter`, `@Setter`, constructores automáticos |
| **Maven** | 3.9+ | Gestión de dependencias | Estándar en proyectos Java empresariales |
| **JUnit 5** | 5.x | Framework de tests | Tests unitarios e integración, parametrizados |
| **Mockito** | 5.x | Librería de mocks | Aislar unidades en tests de servicio |
| **Spring Boot Test** | 4.0.3 | Tests de integración | `@SpringBootTest`, `MockMvc`, contexto completo |

---

## 4. Modelo de Datos

### 4.1. Diagrama de Entidades

```
┌──────────────┐       ┌─────────────────────────┐
│    TURNO     │       │      TAREA_CLINICA       │
├──────────────┤  1:N  ├─────────────────────────┤
│ id (PK)      │◄──────│ id (PK)                 │
│ nombre       │       │ descripcion             │
│ horaInicio   │       │ tipo (enum)             │
│ horaFin      │       │ prioridad (enum)        │
└──────────────┘       │ estado (enum)           │
                       │ fecha                   │
┌──────────────┐  1:N  │ observaciones           │
│   USUARIO    │◄──────│ usuario_id (FK)         │
├──────────────┤       │ paciente_id (FK)        │
│ id (PK)      │       │ turno_id (FK)           │
│ nombre       │       └─────────────────────────┘
│ apellidos    │                   │
│ username     │                   │ N:1
│ password     │       ┌───────────▼─────────────┐
│ rol (enum)   │       │        PACIENTE         │
└──────────────┘       ├─────────────────────────┤
                       │ id (PK)                 │
                       │ nombre                  │
                       │ apellidos               │
                       │ fechaNacimiento         │
                       │ numeroHistoriaClinica   │
                       │ habitacion              │
                       └─────────────────────────┘
```

**Cardinalidades:**
- `USUARIO` 1 → N `TAREA_CLINICA`: Un usuario puede tener muchas tareas asignadas.
- `PACIENTE` 1 → N `TAREA_CLINICA`: Un paciente puede tener múltiples tareas clínicas.
- `TURNO` 1 → N `TAREA_CLINICA`: Un turno puede agrupar múltiples tareas.

### 4.2. Descripción de Entidades

#### 4.2.1. Turno

**Propósito:** Representa un turno de trabajo en el hospital (Mañana, Tarde, Noche). Las tareas clínicas se asignan a un turno específico para organizar la carga de trabajo temporal.

| Campo | Tipo Java | Tipo BD | Restricciones |
|---|---|---|---|
| `id` | `Long` | `BIGINT` | PK, auto-increment |
| `nombre` | `String` | `VARCHAR(50)` | NOT NULL, UNIQUE |
| `horaInicio` | `LocalTime` | `TIME` | NOT NULL |
| `horaFin` | `LocalTime` | `TIME` | NOT NULL |

**Anotaciones JPA relevantes:**
```java
@Entity
@Table(name = "turno")
@Column(nullable = false, unique = true, length = 50)  // nombre
```

**Datos iniciales (data.sql):** Mañana (07:00–15:00), Tarde (15:00–23:00), Noche (23:00–07:00).

---

#### 4.2.2. Paciente

**Propósito:** Almacena la información básica de identificación de un paciente hospitalizado. El `numeroHistoriaClinica` es el identificador único de negocio (distinto del ID técnico).

| Campo | Tipo Java | Tipo BD | Restricciones |
|---|---|---|---|
| `id` | `Long` | `BIGINT` | PK, auto-increment |
| `nombre` | `String` | `VARCHAR(100)` | NOT NULL |
| `apellidos` | `String` | `VARCHAR(100)` | NOT NULL |
| `fechaNacimiento` | `LocalDate` | `DATE` | nullable |
| `numeroHistoriaClinica` | `String` | `VARCHAR(20)` | NOT NULL, UNIQUE |
| `habitacion` | `String` | `VARCHAR(10)` | nullable |

**Validación de negocio:** Al crear un paciente, el servicio verifica que no exista otro con el mismo `numeroHistoriaClinica` antes de persistir.

---

#### 4.2.3. Usuario

**Propósito:** Representa a un profesional sanitario que puede autenticarse en el sistema. El campo `rol` determina qué operaciones puede realizar en cada interfaz.

| Campo | Tipo Java | Tipo BD | Restricciones |
|---|---|---|---|
| `id` | `Long` | `BIGINT` | PK, auto-increment |
| `nombre` | `String` | `VARCHAR(100)` | NOT NULL |
| `apellidos` | `String` | `VARCHAR(100)` | NOT NULL |
| `username` | `String` | `VARCHAR(50)` | NOT NULL, UNIQUE |
| `password` | `String` | `VARCHAR(255)` | NOT NULL, BCrypt hash |
| `rol` | `Rol` (enum) | `VARCHAR` | NOT NULL, EnumType.STRING |

**Consideraciones de seguridad:**
- El campo `password` está anotado con `@JsonIgnore` para que nunca se serialice en respuestas JSON.
- La entidad usa `@ToString(exclude = "password")` para evitar fugas del hash en logs.
- El password se encripta con BCrypt (factor de coste 10) antes de persistirse.

---

#### 4.2.4. TareaClinica

**Propósito:** Es la entidad central del sistema. Representa una tarea sanitaria concreta (medicación, cura, control de signos vitales, etc.) que debe realizar un enfermero para un paciente durante un turno determinado.

| Campo | Tipo Java | Tipo BD | Restricciones |
|---|---|---|---|
| `id` | `Long` | `BIGINT` | PK, auto-increment |
| `descripcion` | `String` | `VARCHAR(500)` | NOT NULL |
| `tipo` | `TipoTarea` (enum) | `VARCHAR` | NOT NULL |
| `prioridad` | `Prioridad` (enum) | `VARCHAR` | NOT NULL |
| `estado` | `EstadoTarea` (enum) | `VARCHAR` | NOT NULL |
| `fecha` | `LocalDateTime` | `TIMESTAMP` | NOT NULL |
| `observaciones` | `String` | `VARCHAR(1000)` | nullable |
| `asignadoA` | `Usuario` | FK `usuario_id` | NOT NULL, ManyToOne |
| `paciente` | `Paciente` | FK `paciente_id` | NOT NULL, ManyToOne |
| `turno` | `Turno` | FK `turno_id` | NOT NULL, ManyToOne |

**Relaciones JPA:**
```java
@ManyToOne
@JoinColumn(name = "usuario_id", nullable = false)
@JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
private Usuario asignadoA;
```

La anotación `@JsonIgnoreProperties` en las relaciones evita la serialización circular y la exposición de datos sensibles.

### 4.3. Enumeraciones

#### 4.3.1. Rol

Define el perfil de acceso de un usuario. Determina qué vistas y endpoints están disponibles.

| Valor | Descripción | Capacidades |
|---|---|---|
| `ENFERMERIA` | Personal de enfermería | Consultar y actualizar estado de sus tareas asignadas |
| `MEDICINA` | Personal médico | Crear tareas, asignarlas a enfermería, crear pacientes |
| `SUPERVISOR` | Supervisor de planta | Todo lo anterior + reasignar tareas + dashboard en tiempo real |

#### 4.3.2. TipoTarea

Clasifica la naturaleza clínica de la tarea.

| Valor | Descripción |
|---|---|
| `MEDICACION` | Administración de medicamentos |
| `CONTROL_SIGNOS_VITALES` | Medición de temperatura, pulso, presión arterial, etc. |
| `CURA` | Cura de heridas o procedimientos de curación |
| `HIGIENE` | Aseo e higiene del paciente |
| `MOVILIZACION` | Cambios posturales, fisioterapia básica |
| `OTRO` | Cualquier tarea no incluida en las anteriores |

#### 4.3.3. Prioridad

Define la urgencia de la tarea. Usada para filtrar y colorear las tareas en la interfaz y para los gráficos del dashboard.

| Valor | Color en UI | Descripción |
|---|---|---|
| `URGENTE` | Rojo (`#dc3545`) | Requiere atención inmediata |
| `ALTA` | Naranja (`#fd7e14`) | Atención prioritaria en el turno |
| `MEDIA` | Gris (`#6c757d`) | Atención normal planificada |
| `BAJA` | Gris claro (`#adb5bd`) | Puede postergarse si hay tareas más urgentes |

#### 4.3.4. EstadoTarea

Representa el ciclo de vida de una tarea clínica.

| Valor | Color en UI | Transiciones permitidas |
|---|---|---|
| `PENDIENTE` | Amarillo | → EN_CURSO, CANCELADA |
| `EN_CURSO` | Azul | → REALIZADA, CANCELADA |
| `REALIZADA` | Verde | Estado final |
| `CANCELADA` | Gris | Estado final |

---

## 5. Capa de Persistencia (Repository)

Todos los repositorios son **interfaces** que extienden `JpaRepository<Entidad, Long>`. Spring Data JPA genera automáticamente la implementación en tiempo de ejecución mediante proxies dinámicos.

### 5.1. TurnoRepository

```java
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<Turno> findByNombre(String nombre);
}
```

| Método | Tipo | Descripción |
|---|---|---|
| `findAll()` | Heredado | Devuelve todos los turnos |
| `findById(Long)` | Heredado | Busca por ID |
| `save(Turno)` | Heredado | Persiste o actualiza |
| `existsById(Long)` | Heredado | Verifica existencia |
| `deleteById(Long)` | Heredado | Elimina por ID |
| `findByNombre(String)` | Custom | Query derivada: `WHERE nombre = ?` |

### 5.2. PacienteRepository

```java
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNumeroHistoriaClinica(String numeroHistoriaClinica);
}
```

| Método | Tipo | Descripción |
|---|---|---|
| `findByNumeroHistoriaClinica(String)` | Custom | Busca por identificador único de negocio |

### 5.3. UsuarioRepository

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByRol(Rol rol);
}
```

| Método | Tipo | Descripción |
|---|---|---|
| `findByUsername(String)` | Custom | Usado por Spring Security para autenticación |
| `findByRol(Rol)` | Custom | Filtra usuarios por rol (ej: solo ENFERMERIA) |

### 5.4. TareaClinicaRepository

```java
public interface TareaClinicaRepository extends JpaRepository<TareaClinica, Long> {
    List<TareaClinica> findByPacienteId(Long pacienteId);
    List<TareaClinica> findByAsignadoAId(Long usuarioId);
    List<TareaClinica> findByTurnoId(Long turnoId);
    List<TareaClinica> findByEstado(EstadoTarea estado);
    List<TareaClinica> findByPrioridad(Prioridad prioridad);
    List<TareaClinica> findByAsignadoAIdAndEstado(Long usuarioId, EstadoTarea estado);
    List<TareaClinica> findByTurnoIdAndEstado(Long turnoId, EstadoTarea estado);
}
```

| Método | Descripción |
|---|---|
| `findByPacienteId` | Todas las tareas de un paciente |
| `findByAsignadoAId` | Tareas asignadas a un enfermero específico |
| `findByTurnoId` | Tareas de un turno específico |
| `findByEstado` | Filtro por estado (PENDIENTE, EN_CURSO, etc.) |
| `findByPrioridad` | Filtro por prioridad |
| `findByAsignadoAIdAndEstado` | Combinación enfermero + estado |
| `findByTurnoIdAndEstado` | Combinación turno + estado |

**Nota sobre queries derivadas:** Spring Data JPA traduce el nombre del método a una consulta JPQL automáticamente. Por ejemplo, `findByAsignadoAId` genera `SELECT t FROM TareaClinica t WHERE t.asignadoA.id = :id`.

---

## 6. Capa de Lógica de Negocio (Service)

### 6.1. TurnoService

**Responsabilidades:** CRUD completo de turnos. Validación de nombre único.

| Método | Transaccionalidad | Descripción |
|---|---|---|
| `obtenerTodos()` | `readOnly=true` | Lista todos los turnos |
| `obtenerPorId(Long)` | `readOnly=true` | Busca por ID, devuelve `Optional` |
| `obtenerPorNombre(String)` | `readOnly=true` | Busca por nombre, devuelve `Optional` |
| `crear(Turno)` | `@Transactional` | Valida nombre único, persiste |
| `actualizar(Long, Turno)` | `@Transactional` | Carga existente, actualiza campos, guarda |
| `eliminar(Long)` | `@Transactional` | Verifica existencia, elimina |

**Validación en `crear`:**
```java
if (turnoRepository.findByNombre(turno.getNombre()).isPresent()) {
    throw new IllegalArgumentException("Ya existe un turno con el nombre " + turno.getNombre());
}
```

---

### 6.2. PacienteService

**Responsabilidades:** CRUD completo. Validación de número de historia clínica único.

| Método | Transaccionalidad | Descripción |
|---|---|---|
| `obtenerTodos()` | `readOnly=true` | Lista todos los pacientes |
| `obtenerPorId(Long)` | `readOnly=true` | Busca por ID |
| `obtenerPorNumeroHistoriaClinica(String)` | `readOnly=true` | Busca por NHC |
| `crear(Paciente)` | `@Transactional` | Valida NHC único, persiste |
| `actualizar(Long, Paciente)` | `@Transactional` | Actualiza todos los campos |
| `eliminar(Long)` | `@Transactional` | Elimina por ID |

---

### 6.3. UsuarioService

**Responsabilidades:** CRUD de usuarios con encriptación de contraseñas. Validación de username único.

| Método | Transaccionalidad | Descripción |
|---|---|---|
| `obtenerTodos()` | `readOnly=true` | Lista todos los usuarios |
| `obtenerPorId(Long)` | `readOnly=true` | Busca por ID |
| `obtenerPorUsername(String)` | `readOnly=true` | Busca por username (usado en login) |
| `crear(Usuario)` | `@Transactional` | Valida username único, encripta password, persiste |
| `actualizar(Long, Usuario)` | `@Transactional` | Actualiza campos; re-encripta password si se proporciona |
| `eliminar(Long)` | `@Transactional` | Elimina por ID |

**Encriptación:**
```java
usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
```

En `actualizar`, el password solo se re-encripta si el campo no es nulo ni blank, lo que permite actualizar otros campos sin cambiar la contraseña.

---

### 6.4. TareaClinicaService

**Responsabilidades:** CRUD de tareas con validación de referencias a entidades relacionadas.

| Método | Transaccionalidad | Descripción |
|---|---|---|
| `obtenerTodas()` | `readOnly=true` | Lista todas las tareas |
| `obtenerPorId(Long)` | `readOnly=true` | Busca por ID |
| `obtenerPorPaciente(Long)` | `readOnly=true` | Tareas de un paciente |
| `obtenerPorUsuario(Long)` | `readOnly=true` | Tareas asignadas a un usuario |
| `obtenerPorTurno(Long)` | `readOnly=true` | Tareas de un turno |
| `obtenerPorEstado(EstadoTarea)` | `readOnly=true` | Filtro por estado |
| `obtenerPorPrioridad(Prioridad)` | `readOnly=true` | Filtro por prioridad |
| `crear(TareaClinica)` | `@Transactional` | Valida relaciones, persiste |
| `actualizar(Long, TareaClinica)` | `@Transactional` | Actualiza campos y relaciones |
| `eliminar(Long)` | `@Transactional` | Elimina por ID |

**Validaciones en `crear`:**
1. `asignadoA` no es null y su ID existe en `usuarioRepository`
2. `paciente` no es null y su ID existe en `pacienteRepository`
3. `turno` no es null y su ID existe en `turnoRepository`

---

### 6.5. DashboardService

**Responsabilidades:** Cálculo de estadísticas agregadas en tiempo real para el dashboard supervisor. No usa caché: cada llamada consulta la base de datos directamente para garantizar datos actualizados.

| Método | Descripción | Datos devueltos |
|---|---|---|
| `obtenerEstadisticasGenerales()` | Conteos globales del sistema | `DashboardStats` |
| `obtenerCargaPorTurno()` | Tareas agrupadas por turno | `Map<String, Long>` |
| `obtenerTareasPorPrioridad()` | Distribución por nivel de urgencia | `Map<Prioridad, Long>` |
| `obtenerTop5EnfermerosConMasTareas()` | Ranking de enfermeros | `List<UsuarioConTareas>` |

**Algoritmo de top 5 enfermeros:**
```
1. Filtrar usuarios con rol ENFERMERIA
2. Por cada enfermero: contar tareas asignadas vía TareaClinicaService
3. Construir UsuarioConTareas(id, nombreCompleto, numeroTareas)
4. Ordenar descendente por numeroTareas
5. Limitar a 5 resultados
```

---

## 7. Capa de Presentación (Controller)

### 7.1. REST Controllers

#### 7.1.1. TurnoController

**Ruta base:** `/api/turnos`

| Método | Ruta | Descripción | Respuesta exitosa | Errores |
|---|---|---|---|---|
| GET | `/api/turnos` | Lista todos los turnos | 200 + JSON array | — |
| GET | `/api/turnos/{id}` | Obtiene turno por ID | 200 + JSON | 404 si no existe |
| GET | `/api/turnos/nombre/{nombre}` | Obtiene turno por nombre | 200 + JSON | 404 si no existe |
| POST | `/api/turnos` | Crea nuevo turno | 201 + JSON creado | 400 si nombre duplicado |
| PUT | `/api/turnos/{id}` | Actualiza turno | 200 + JSON actualizado | 404 si no existe |
| DELETE | `/api/turnos/{id}` | Elimina turno | 204 No Content | 404 si no existe |

---

#### 7.1.2. PacienteController

**Ruta base:** `/api/pacientes`

| Método | Ruta | Descripción | Respuesta exitosa | Errores |
|---|---|---|---|---|
| GET | `/api/pacientes` | Lista todos los pacientes | 200 + JSON array | — |
| GET | `/api/pacientes/{id}` | Obtiene paciente por ID | 200 + JSON | 404 |
| GET | `/api/pacientes/historia/{nhc}` | Busca por NHC | 200 + JSON | 404 |
| POST | `/api/pacientes` | Crea nuevo paciente | 201 + JSON | 400 si NHC duplicado |
| PUT | `/api/pacientes/{id}` | Actualiza paciente | 200 + JSON | 404 |
| DELETE | `/api/pacientes/{id}` | Elimina paciente | 204 | — |

---

#### 7.1.3. UsuarioController

**Ruta base:** `/api/usuarios` — **Solo accesible por SUPERVISOR**

| Método | Ruta | Descripción | Respuesta exitosa | Errores |
|---|---|---|---|---|
| GET | `/api/usuarios` | Lista todos los usuarios | 200 + JSON array | — |
| GET | `/api/usuarios/{id}` | Obtiene usuario por ID | 200 + JSON | 404 |
| POST | `/api/usuarios` | Crea usuario | 201 + JSON | 400 si username duplicado |
| PUT | `/api/usuarios/{id}` | Actualiza usuario | 200 + JSON | 400 |
| DELETE | `/api/usuarios/{id}` | Elimina usuario | 204 | — |

**Nota de seguridad:** El campo `password` nunca aparece en las respuestas JSON gracias a `@JsonIgnore` en la entidad.

---

#### 7.1.4. TareaClinicaController

**Ruta base:** `/api/tareas`

| Método | Ruta | Descripción | Respuesta exitosa | Errores |
|---|---|---|---|---|
| GET | `/api/tareas` | Lista todas las tareas | 200 + JSON array | — |
| GET | `/api/tareas/{id}` | Obtiene por ID | 200 + JSON | 404 |
| GET | `/api/tareas/paciente/{id}` | Tareas de un paciente | 200 + JSON array | — |
| GET | `/api/tareas/usuario/{id}` | Tareas de un usuario | 200 + JSON array | — |
| GET | `/api/tareas/turno/{id}` | Tareas de un turno | 200 + JSON array | — |
| GET | `/api/tareas/estado/{estado}` | Filtro por estado | 200 + JSON array | — |
| GET | `/api/tareas/prioridad/{prioridad}` | Filtro por prioridad | 200 + JSON array | — |
| POST | `/api/tareas` | Crea tarea | 201 + JSON | 400 si relaciones inválidas |
| PUT | `/api/tareas/{id}` | Actualiza tarea | 200 + JSON | 400 / 404 |
| DELETE | `/api/tareas/{id}` | Elimina tarea | 204 | — |

---

#### 7.1.5. SupervisorRealtimeController

**Ruta base:** `/api/supervisor`

| Método | Ruta | Descripción | Content-Type | Notas |
|---|---|---|---|---|
| GET | `/api/supervisor/dashboard/stream` | Stream SSE de estadísticas | `text/event-stream` | Infinito, cada 5s |

**Formato del evento SSE:**
```
id: 42
event: dashboard-update
data: {"timestamp":"2026-04-30T10:30:00","stats":{...},"cargaPorTurno":{...},...}
```

---

### 7.2. Web Controllers (Thymeleaf)

#### 7.2.1. WebController

**Ruta base:** `/`

| Método | Ruta | Vista | Descripción |
|---|---|---|---|
| GET | `/login` | `login.html` | Formulario de login |
| GET | `/` o `/index` | (redirect) | Redirige al dashboard según el rol autenticado |

---

#### 7.2.2. EnfermeriaWebController

**Ruta base:** `/enfermeria` — Requiere rol `ENFERMERIA`

| Método | Ruta | Vista | Descripción |
|---|---|---|---|
| GET | `/enfermeria/dashboard` | `enfermeria/dashboard` | Dashboard personal con resumen de tareas |
| GET | `/enfermeria/mis-tareas` | `enfermeria/mis-tareas` | Lista completa de tareas asignadas |
| POST | `/enfermeria/tareas/{id}/actualizar-estado` | (redirect) | Actualiza estado y observaciones de una tarea |

---

#### 7.2.3. MedicinaWebController

**Ruta base:** `/medicina` — Requiere rol `MEDICINA`

| Método | Ruta | Vista | Descripción |
|---|---|---|---|
| GET | `/medicina/dashboard` | `medicina/dashboard` | Dashboard con resumen global de tareas y pacientes |
| GET | `/medicina/crear-tarea` | `medicina/crear-tarea` | Formulario de creación de tarea |
| POST | `/medicina/crear-tarea` | (redirect) | Procesa el formulario y crea la tarea |
| GET | `/medicina/pacientes` | `medicina/pacientes` | Lista de todos los pacientes |
| GET | `/medicina/crear-paciente` | `medicina/crear-paciente` | Formulario de alta de paciente |
| POST | `/medicina/crear-paciente` | (redirect) | Crea nuevo paciente |

---

#### 7.2.4. SupervisorWebController

**Ruta base:** `/supervisor` — Requiere rol `SUPERVISOR`

| Método | Ruta | Vista | Descripción |
|---|---|---|---|
| GET | `/supervisor/dashboard` | `supervisor/dashboard` | Dashboard estático con totales |
| GET | `/supervisor/carga-trabajo` | `supervisor/carga-trabajo` | Carga de tareas agrupada por turno |
| GET | `/supervisor/usuarios` | `supervisor/usuarios` | Lista de todos los usuarios del sistema |
| GET | `/supervisor/reasignar-tareas` | `supervisor/reasignar-tareas` | Interfaz para reasignar tareas entre enfermeros |
| POST | `/supervisor/reasignar-tarea/{id}` | (redirect) | Ejecuta la reasignación de una tarea |
| GET | `/supervisor/dashboard-realtime` | `supervisor/dashboard-realtime` | Dashboard en tiempo real con SSE |

---

## 8. Seguridad

### 8.1. Spring Security — Arquitectura

La seguridad se implementa en `SecurityConfig.java` mediante una `SecurityFilterChain`. Cuando llega una petición HTTP, Spring Security la intercepta antes de que llegue al controlador y aplica las reglas de autorización definidas.

**Componentes clave:**

1. **`SecurityConfig`**: Define la cadena de filtros (reglas de acceso, login, logout, manejo de errores).
2. **`CustomUserDetailsService`**: Carga los datos del usuario desde la base de datos para Spring Security. Convierte el `Rol` del usuario en un `GrantedAuthority` con el prefijo `ROLE_`.
3. **`PasswordEncoderConfig`**: Declara el bean `BCryptPasswordEncoder` disponible para toda la aplicación.
4. **`DataLoader`**: Inicializa los tres usuarios de prueba al arrancar si la base de datos está vacía.

### 8.2. Tipos de Autenticación

- **Form Login**: Para el acceso web. El usuario envía usuario/contraseña en el formulario `/login`. Spring Security valida contra la BD y crea una sesión HTTP.
- **HTTP Basic**: Habilitado como fallback para compatibilidad con clientes REST (Postman, curl).

### 8.3. Roles Definidos

| Rol | Prefijo Spring | Descripción |
|---|---|---|
| `ENFERMERIA` | `ROLE_ENFERMERIA` | Personal de enfermería |
| `MEDICINA` | `ROLE_MEDICINA` | Personal médico |
| `SUPERVISOR` | `ROLE_SUPERVISOR` | Supervisor de planta |

### 8.4. Matriz de Permisos

| Recurso | ENFERMERIA | MEDICINA | SUPERVISOR |
|---|---|---|---|
| `GET /api/turnos/**` | ✅ | ✅ | ✅ |
| `POST/PUT/DELETE /api/turnos/**` | ❌ | ❌ | ✅ |
| `GET /api/pacientes/**` | ✅ | ✅ | ✅ |
| `POST/PUT /api/pacientes/**` | ❌ | ✅ | ✅ |
| `DELETE /api/pacientes/**` | ❌ | ❌ | ✅ |
| `GET /api/tareas/**` | ✅ | ✅ | ✅ |
| `POST /api/tareas/**` | ❌ | ✅ | ✅ |
| `PUT /api/tareas/**` | ✅ | ✅ | ✅ |
| `DELETE /api/tareas/**` | ❌ | ❌ | ✅ |
| `/api/usuarios/**` | ❌ | ❌ | ✅ |
| `/api/supervisor/dashboard/stream` | ❌ | ❌ | ✅ |
| `/enfermeria/**` | ✅ | ❌ | ❌ |
| `/medicina/**` | ❌ | ✅ | ❌ |
| `/supervisor/**` | ❌ | ❌ | ✅ |
| `/login`, `/error` | ✅ | ✅ | ✅ |

### 8.5. Encriptación de Contraseñas

```java
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

BCrypt genera un hash diferente cada vez (por el salt aleatorio incorporado), haciendo imposible la comparación directa de hashes. Spring Security usa `passwordEncoder.matches(rawPassword, encodedPassword)` en la autenticación.

### 8.6. CSRF

CSRF está deshabilitado para las rutas `/api/**` (los clientes REST no envían cookies de sesión típicamente), pero permanece activo para las rutas web, que usan formularios Thymeleaf con tokens CSRF automáticos.

### 8.7. Manejo de Errores de Autenticación

Las rutas `/api/**` devuelven `HTTP 401 Unauthorized` en lugar de redirigir al login (comportamiento por defecto para APIs):

```java
.exceptionHandling(ex -> ex
    .defaultAuthenticationEntryPointFor(
        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
        PathPatternRequestMatcher.pathPattern("/api/**")
    )
)
```

---

## 9. Vistas Web (Thymeleaf)

### 9.1. Estructura de Templates

```
src/main/resources/templates/
├── login.html                        # Página de autenticación
├── fragments/
│   └── header.html                   # Fragmentos reutilizables (head, navbar)
├── enfermeria/
│   ├── dashboard.html                # Resumen personal del enfermero
│   └── mis-tareas.html               # Lista y gestión de tareas propias
├── medicina/
│   ├── dashboard.html                # Resumen global médico
│   ├── crear-tarea.html              # Formulario crear tarea clínica
│   ├── pacientes.html                # Lista de pacientes
│   └── crear-paciente.html           # Formulario alta de paciente
└── supervisor/
    ├── dashboard.html                # Dashboard estático
    ├── carga-trabajo.html            # Carga por turno
    ├── reasignar-tareas.html         # Reasignación de tareas
    ├── usuarios.html                 # Gestión de usuarios
    └── dashboard-realtime.html       # Dashboard SSE en tiempo real
```

### 9.2. Fragmentos Reutilizables (`header.html`)

El archivo `fragments/header.html` define dos fragmentos reutilizados por todas las páginas:

**Fragment `head(title)`:** Importa Bootstrap 5.3.0 CSS, Bootstrap Icons 1.10.0, y define estilos globales (stat-card, badge de estado de tarea, etc.).

**Fragment `navbar(rol)`:** Barra de navegación adaptativa que muestra opciones distintas según el rol del usuario autenticado:
- Color de fondo: azul (ENFERMERIA), verde (MEDICINA), morado (SUPERVISOR).
- Items del menú condicionales usando `sec:authorize="hasRole('...')"`.
- Muestra el username del usuario autenticado con `<sec:authentication property="name"/>`.
- Botón de logout con formulario POST a `/logout`.

**Uso en las vistas:**
```html
<th:block th:replace="~{fragments/header :: head('Título')}"></th:block>
<th:block th:replace="~{fragments/header :: navbar('SUPERVISOR')}"></th:block>
```

### 9.3. Vistas por Rol

#### ENFERMERIA

| Vista | Funcionalidades |
|---|---|
| `dashboard.html` | Resumen personal: total tareas, pendientes, en curso. Lista de las 5 tareas más recientes. |
| `mis-tareas.html` | Lista completa de tareas propias. Formulario inline para actualizar estado y añadir observaciones. Badges de color por estado y prioridad. |

#### MEDICINA

| Vista | Funcionalidades |
|---|---|
| `dashboard.html` | Totales globales del sistema. Top 5 tareas más recientes. |
| `crear-tarea.html` | Formulario completo: descripción, tipo, prioridad, estado, fecha, observaciones, selección de paciente/enfermero/turno mediante `<select>`. |
| `pacientes.html` | Tabla con todos los pacientes: nombre, apellidos, NHC, habitación, fecha de nacimiento. |
| `crear-paciente.html` | Formulario: nombre, apellidos, fecha de nacimiento, NHC, habitación. |

#### SUPERVISOR

| Vista | Funcionalidades |
|---|---|
| `dashboard.html` | Totales: tareas, usuarios, turnos. |
| `carga-trabajo.html` | Tabla de carga por turno con barras visuales de progreso. |
| `reasignar-tareas.html` | Tabla con todas las tareas. Formulario `<select>` para elegir nuevo enfermero por cada tarea. |
| `usuarios.html` | Lista de todos los usuarios con su rol y datos. |
| `dashboard-realtime.html` | Dashboard con SSE (ver sección 10). |

### 9.4. Tecnologías Frontend

- **Thymeleaf 3.x**: Procesamiento server-side, integración con Spring Security (dialect `sec:`), `th:href`, `th:text`, `th:each`, `th:field`.
- **Bootstrap 5.3.0**: Grid responsivo, componentes (cards, tables, badges, buttons, forms), cargado desde CDN.
- **Bootstrap Icons 1.10.0**: Iconografía consistente (`bi-activity`, `bi-person`, `bi-speedometer2`, etc.).
- **Chart.js 4.4.0**: Gráfico de barras y donut en el dashboard en tiempo real, cargado desde CDN.
- **JavaScript (Vanilla)**: EventSource API para SSE, actualización dinámica del DOM.

---

## 10. Funcionalidades en Tiempo Real

### 10.1. Server-Sent Events (SSE)

**Propósito:** Permitir que el navegador del supervisor reciba actualizaciones automáticas del servidor cada 5 segundos sin necesidad de hacer polling manual (peticiones repetidas).

**Ventajas de SSE frente a alternativas:**

| Tecnología | Dirección | Complejidad | Caso de uso |
|---|---|---|---|
| Polling | Cliente → Servidor (repetido) | Baja, pero ineficiente | Sistemas legacy |
| SSE | Servidor → Cliente | Baja | Dashboards, notificaciones unidireccionales |
| WebSockets | Bidireccional | Alta | Chats, juegos en tiempo real |

SSE se eligió por ser el mecanismo más simple para transmisión unidireccional servidor→cliente, reconexión automática del navegador, y compatibilidad con HTTP estándar (sin necesidad de upgrade de protocolo).

**Endpoint:**
```
GET /api/supervisor/dashboard/stream
Content-Type: text/event-stream
Authorization: SUPERVISOR
```

**Formato del evento:**
```
id: 7
event: dashboard-update
data: {
  "timestamp": "2026-04-30T10:30:15",
  "stats": {
    "totalTareas": 45,
    "tareasPendientes": 12,
    "tareasEnCurso": 8,
    "tareasRealizadas": 23,
    "tareasCanceladas": 2,
    "totalPacientes": 30,
    "totalEnfermeros": 10
  },
  "cargaPorTurno": {
    "Mañana": 18,
    "Tarde": 15,
    "Noche": 12
  },
  "tareasPorPrioridad": {
    "URGENTE": 5,
    "ALTA": 12,
    "MEDIA": 20,
    "BAJA": 8
  },
  "topEnfermeros": [
    {"usuarioId": 1, "nombreCompleto": "Ana García López", "numeroTareas": 8},
    {"usuarioId": 2, "nombreCompleto": "Carlos Martínez", "numeroTareas": 7}
  ]
}
```

### 10.2. Spring WebFlux y Flux

**Por qué se usa WebFlux solo para SSE:**

MediTrack es principalmente una aplicación Spring MVC (bloqueante). Spring WebFlux se incorpora exclusivamente para el endpoint SSE porque `Flux` es la abstracción natural para una secuencia de eventos infinita en el tiempo.

Spring Boot 4.x soporta la coexistencia de Spring MVC y WebFlux en el mismo artefacto. El endpoint SSE usa WebFlux mientras el resto de la aplicación sigue con el modelo MVC bloqueante.

**Implementación del Flux:**
```java
return Flux.interval(Duration.ofSeconds(5))
    .map(tick -> {
        DashboardData data = new DashboardData();
        data.setTimestamp(LocalDateTime.now());
        data.setStats(dashboardService.obtenerEstadisticasGenerales());
        // ... más campos
        return ServerSentEvent.<DashboardData>builder()
            .id(String.valueOf(secuencia.getAndIncrement()))
            .event("dashboard-update")
            .data(data)
            .build();
    });
```

`Flux.interval` emite un `Long` (número de tick) cada 5 segundos en un scheduler de Reactor. El operador `.map` transforma ese Long en un `ServerSentEvent<DashboardData>` completo. El Flux es potencialmente infinito y solo se cancela cuando el cliente (navegador) cierra la conexión.

**JavaScript — EventSource:**
```javascript
const eventSource = new EventSource('/api/supervisor/dashboard/stream');
eventSource.addEventListener('dashboard-update', (event) => {
    const data = JSON.parse(event.data);
    // actualizar DOM...
});
```

El navegador abre una conexión HTTP persistente al endpoint SSE. Si la conexión se pierde, el `EventSource` la reestablece automáticamente, mostrando el badge "Desconectado" en la UI mientras tanto.

---

## 11. Testing

### 11.1. Estrategia de Testing

Se aplica una estrategia de testing en tres niveles por cada entidad:

1. **Repository Test** (`@DataJpaTest` o `@SpringBootTest + @Transactional`): Verifica que las queries derivadas y los métodos custom funcionan correctamente contra una base de datos real.

2. **Service Test** (`@ExtendWith(MockitoExtension.class)`): Tests unitarios aislados con Mockito. El repositorio se mockea para probar exclusivamente la lógica del servicio.

3. **Controller Test** (`@SpringBootTest + MockMvc`): Tests de integración que levantan el contexto completo de Spring y verifican el comportamiento HTTP de cada endpoint.

### 11.2. Cobertura de Tests

| Entidad | Repository | Service | Controller | Total |
|---|---|---|---|---|
| Turno | 2 | 4 | 7 | **13** |
| Paciente | 2 | 4 | 7 | **13** |
| Usuario | 2 | 4 | 7 | **13** |
| TareaClinica | 3 | 7 | 8 | **18** |
| Security | — | — | 8 | **8** |
| Dashboard | — | 3 | — | **3** |
| **TOTAL** | **9** | **22** | **37** | **69** |

### 11.3. Configuración de Tests

**`application-test.properties`** (en `src/main/resources/`):

```properties
spring.sql.init.mode=never          # No ejecutar data.sql en tests
spring.datasource.url=jdbc:postgresql://localhost:5432/meditrack_db
spring.datasource.username=postgres
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=create-drop   # Crear esquema al iniciar, borrar al terminar
```

**Diferencias clave con producción:**
- `sql.init.mode=never`: Los tests no insertan los turnos de `data.sql`, evitando conflictos entre tests.
- `ddl-auto=create-drop`: El esquema se recrea en cada ejecución de tests, garantizando un estado limpio.
- `@Transactional` en los tests: Cada test se ejecuta en una transacción que se revierte al finalizar, aislando los datos entre tests.

**Anotaciones estándar de todos los tests:**
```java
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MiEntidadControllerTest { ... }
```

### 11.4. Tests de Service con Mockito

Patrón típico de un Service Test:

```java
@ExtendWith(MockitoExtension.class)
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    void noDeberiaCrearTurnoDuplicado() {
        Turno turno = new Turno(null, "Mañana", LocalTime.of(7,0), LocalTime.of(15,0));
        when(turnoRepository.findByNombre("Mañana")).thenReturn(Optional.of(turno));

        assertThrows(IllegalArgumentException.class, () -> turnoService.crear(turno));
        verify(turnoRepository, never()).save(any());
    }
}
```

### 11.5. Tests de Seguridad

`SecurityConfigTest` verifica la matriz de permisos:

```java
@Test
@WithMockUser(roles = "ENFERMERIA")
void enfermeriaNoDeberiaAccederAApiUsuarios() throws Exception {
    mockMvc.perform(get("/api/usuarios"))
        .andExpect(status().isForbidden());
}
```

---

## 12. Configuración del Proyecto

### 12.1. application.properties

```properties
spring.application.name=MediTrack
server.port=8080

# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/meditrack_db
spring.datasource.username=postgres
spring.datasource.password=admin123
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update           # Actualiza el esquema sin borrar datos
spring.jpa.show-sql=true                        # Muestra las SQL en consola (dev)
spring.jpa.properties.hibernate.format_sql=true # Formatea las SQL para legibilidad
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Thymeleaf
spring.thymeleaf.cache=false       # Desactiva caché para ver cambios sin reiniciar
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Logging
logging.level.com.hospital.meditrack=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG

# Inicialización de datos
spring.sql.init.mode=always                      # Ejecutar data.sql en cada inicio
spring.jpa.defer-datasource-initialization=true  # Esperar a JPA antes de ejecutar SQL
```

**Por qué `defer-datasource-initialization=true`:** Hibernate crea las tablas con `ddl-auto=update`, y `data.sql` necesita que las tablas existan antes de insertar. La propiedad `defer` garantiza este orden de ejecución.

### 12.2. pom.xml — Dependencias Principales

| Dependencia | Propósito |
|---|---|
| `spring-boot-starter-webmvc` | Spring MVC: REST controllers, vistas web |
| `spring-boot-starter-data-jpa` | Spring Data JPA + Hibernate |
| `spring-boot-starter-security` | Autenticación y autorización |
| `spring-boot-starter-thymeleaf` | Motor de plantillas |
| `thymeleaf-extras-springsecurity6` | Dialect `sec:authorize` en plantillas |
| `spring-boot-starter-validation` | Validación de beans con `@Valid` |
| `spring-boot-starter-webflux` | WebFlux para SSE con Flux |
| `postgresql` | Driver JDBC de PostgreSQL |
| `lombok` | Reducción de boilerplate |
| `spring-boot-devtools` | Recarga automática en desarrollo |

### 12.3. data.sql

```sql
INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Mañana', '07:00:00', '15:00:00')
    ON CONFLICT (nombre) DO NOTHING;

INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Tarde', '15:00:00', '23:00:00')
    ON CONFLICT (nombre) DO NOTHING;

INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Noche', '23:00:00', '07:00:00')
    ON CONFLICT (nombre) DO NOTHING;
```

**Por qué solo turnos:** Los turnos (Mañana, Tarde, Noche) son datos de referencia del dominio hospitalario, inmutables en el tiempo y necesarios para que el sistema funcione. Los usuarios de prueba se insertan mediante `DataLoader` (lógica Java) en lugar de SQL, porque necesitan encriptación de passwords. Los pacientes y tareas son datos de negocio que deben crearse manualmente en la interfaz.

El `ON CONFLICT (nombre) DO NOTHING` garantiza idempotencia: si los turnos ya existen (segunda ejecución), no falla ni duplica.

---

## 13. Base de Datos

### 13.1. Configuración

| Parámetro | Valor |
|---|---|
| Motor | PostgreSQL 15+ |
| Host | localhost |
| Puerto | 5432 |
| Base de datos | `meditrack_db` |
| Usuario | `postgres` |
| Contraseña | `admin123` |

### 13.2. Esquema de Tablas (generado por Hibernate)

```sql
-- Tabla: turno
CREATE TABLE turno (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(50) NOT NULL UNIQUE,
    hora_inicio TIME NOT NULL,
    hora_fin    TIME NOT NULL
);

-- Tabla: paciente
CREATE TABLE paciente (
    id                       BIGSERIAL PRIMARY KEY,
    nombre                   VARCHAR(100) NOT NULL,
    apellidos                VARCHAR(100) NOT NULL,
    fecha_nacimiento         DATE,
    numero_historia_clinica  VARCHAR(20) NOT NULL UNIQUE,
    habitacion               VARCHAR(10)
);

-- Tabla: usuario
CREATE TABLE usuario (
    id        BIGSERIAL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    rol       VARCHAR(20) NOT NULL
);

-- Tabla: tarea_clinica
CREATE TABLE tarea_clinica (
    id            BIGSERIAL PRIMARY KEY,
    descripcion   VARCHAR(500) NOT NULL,
    tipo          VARCHAR(50) NOT NULL,
    prioridad     VARCHAR(20) NOT NULL,
    estado        VARCHAR(20) NOT NULL,
    fecha         TIMESTAMP NOT NULL,
    observaciones VARCHAR(1000),
    usuario_id    BIGINT NOT NULL REFERENCES usuario(id),
    paciente_id   BIGINT NOT NULL REFERENCES paciente(id),
    turno_id      BIGINT NOT NULL REFERENCES turno(id)
);
```

### 13.3. Estrategia de Migración

`spring.jpa.hibernate.ddl-auto=update` en producción/desarrollo: Hibernate compara el esquema actual con el modelo de entidades y aplica los cambios necesarios (ADD COLUMN, etc.) sin borrar datos existentes.

`create-drop` en tests: El esquema se crea al inicio del contexto de test y se destruye al finalizarlo, garantizando aislamiento.

---

## 14. Decisiones de Diseño Importantes

### 14.1. ¿Por qué no existe una entidad Reasignación?

Una entidad `Reasignacion` con campos `fechaReasignacion`, `usuarioAnterior`, `usuarioNuevo` habría añadido complejidad sin valor directo para el TFG. La reasignación se modela simplemente actualizando el campo `asignadoA` de `TareaClinica`. Para auditoría completa (historial de reasignaciones), se podría añadir en el futuro como mejora.

### 14.2. ¿Por qué @Transactional solo en Service y no en Controller o Repository?

- **Repository:** Spring Data JPA ya gestiona transacciones internamente en cada operación.
- **Controller:** Mezclar lógica de negocio y transaccionalidad violaría el principio de separación de responsabilidades. Si el controller fallara tras persistir, no habría rollback.
- **Service:** Es la capa correcta porque agrupa operaciones que deben ser atómicas. Si un servicio llama a varios repositorios, toda la operación se revierte si algo falla.

### 14.3. ¿Por qué Repository es interface y no clase?

Spring Data JPA genera automáticamente la implementación en tiempo de ejecución mediante proxies dinámicos. Definirlo como interfaz permite declarar el contrato (qué métodos existen) sin escribir el código de implementación (cómo se ejecutan). Reduce drásticamente el boilerplate y las posibilidades de errores.

### 14.4. ¿Por qué BCrypt para passwords?

BCrypt fue diseñado específicamente para contraseñas. Sus características clave:
- **Salt automático**: Cada hash incluye un salt aleatorio; dos hashes del mismo password son distintos.
- **Factor de coste configurable**: Se puede aumentar el coste computacional a medida que el hardware mejora.
- **Resistencia a ataques de diccionario y rainbow tables**.

Alternativas descartadas: MD5 y SHA-1 son rápidos (malos para contraseñas), sin salt intrínseco. SHA-256 es mejor pero tampoco tiene salt automático. BCrypt es el estándar recomendado por OWASP.

### 14.5. ¿Por qué SSE y no WebSockets?

El dashboard del supervisor solo necesita recibir datos del servidor, no enviar datos. Los WebSockets son bidireccionales, lo que añade complejidad de protocolo (handshake WS, mantenimiento de conexión duplex) sin beneficio. SSE es unidireccional (servidor→cliente), opera sobre HTTP estándar, y tiene reconexión automática en el navegador. Es la herramienta correcta para dashboards de monitorización.

### 14.6. ¿Por qué Thymeleaf y no una SPA (React/Angular)?

El objetivo del TFG es demostrar el stack Spring Boot completo. Thymeleaf es la solución de vistas nativa de Spring Boot, reduce la complejidad de tener un proyecto frontend separado, y permite renderizado server-side (mejor SEO y rendimiento inicial). Una SPA habría añadido una capa de complejidad (CORS, autenticación JWT, estado cliente) que no aporta valor diferencial al aprendizaje del backend.

---

## 15. Patrones de Diseño Aplicados

### 15.1. Repository Pattern

Abstrae el acceso a datos detrás de interfaces. El código de negocio no sabe si los datos vienen de PostgreSQL, H2, o cualquier otro origen. Esto es especialmente visible en los tests, donde los repositorios se reemplazan por mocks sin cambiar el código de servicio.

### 15.2. Service Layer Pattern

Centraliza la lógica de negocio en servicios anotados con `@Service`. Los controladores delegan toda la lógica al servicio correspondiente, quedando ellos como meros adaptadores HTTP. Esto permite reutilizar la misma lógica desde un REST Controller y un Web Controller (como sucede en el proyecto).

### 15.3. DTO Pattern

Los DTOs (`DashboardStats`, `UsuarioConTareas`, `DashboardData`) desacoplan la representación de datos para transferencia de la representación interna de las entidades JPA. Permiten componer estructuras de datos ad-hoc para necesidades específicas (el dashboard) sin modificar las entidades del dominio.

### 15.4. MVC Pattern

- **Model:** Entidades JPA + DTOs + datos del servicio inyectados en el Model de Spring.
- **View:** Templates Thymeleaf (renderizado server-side).
- **Controller:** Clases con `@Controller` o `@RestController` que gestionan las peticiones HTTP.

### 15.5. Dependency Injection

Toda la aplicación usa inyección de dependencias de Spring. Los controladores no instancian servicios con `new`; los servicios no instancian repositorios. Spring gestiona el ciclo de vida de los beans y los inyecta donde se necesitan mediante `@Autowired`. Esto facilita los tests (se pueden inyectar mocks) y elimina el acoplamiento fuerte.

---

## 16. Flujo de Datos

### 16.1. Flujo: Crear una Tarea Clínica (web)

```
1. MEDICINA abre /medicina/crear-tarea (GET)
   └─► MedicinaWebController.mostrarFormularioCrearTarea()
       └─► Carga pacientes, enfermeros, turnos, tipos, prioridades
       └─► Renderiza medicina/crear-tarea.html con los datos

2. MEDICINA rellena el formulario y hace submit (POST)
   └─► MedicinaWebController.crearTarea(@RequestParam ...)
       └─► Construye objeto TareaClinica con los IDs de relaciones
       └─► Llama a TareaClinicaService.crear(tarea)
           └─► Valida: asignadoA.id existe en usuarioRepository
           └─► Valida: paciente.id existe en pacienteRepository
           └─► Valida: turno.id existe en turnoRepository
           └─► tareaClinicaRepository.save(tarea) → PostgreSQL
       └─► Redirect a /medicina/dashboard

3. El supervisor ve la nueva tarea en su dashboard realtime
   └─► DashboardService.obtenerEstadisticasGenerales() detecta +1 total
   └─► El próximo tick SSE (≤5s) envía los datos actualizados
   └─► El navegador actualiza las tarjetas y gráficos automáticamente
```

### 16.2. Flujo: Login

```
1. Usuario abre /login (GET)
   └─► WebController.login() devuelve vista login.html

2. Usuario envía credenciales (POST /login)
   └─► Spring Security intercepta la petición
       └─► CustomUserDetailsService.loadUserByUsername(username)
           └─► usuarioRepository.findByUsername(username)
           └─► Construye UserDetails con ROLE_{ROL}
       └─► BCrypt.matches(rawPassword, hashedPassword)
           └─► Si OK: crea sesión HTTP, redirige a /
           └─► Si FAIL: redirige a /login?error

3. / redirige al dashboard según el rol
   └─► WebController.index(Authentication auth)
       └─► Lee rol del Authentication
       └─► Redirige a /supervisor/dashboard, /medicina/dashboard, etc.
```

### 16.3. Flujo: Dashboard Realtime (SSE)

```
1. SUPERVISOR abre /supervisor/dashboard-realtime (GET)
   └─► SupervisorWebController.dashboardRealtime()
       └─► Renderiza supervisor/dashboard-realtime.html
           └─► JavaScript inicializa Chart.js (barras + donut)

2. JavaScript crea EventSource
   └─► new EventSource('/api/supervisor/dashboard/stream')
       └─► Spring Security: verifica rol SUPERVISOR
       └─► SupervisorRealtimeController.streamDashboard()
           └─► Devuelve Flux<ServerSentEvent<DashboardData>>
           └─► Conexión HTTP queda abierta indefinidamente

3. Cada 5 segundos (Flux.interval):
   └─► DashboardService calcula estadísticas en tiempo real
   └─► Serializa DashboardData a JSON
   └─► Envía ServerSentEvent al cliente
   └─► JavaScript recibe el evento:
       └─► Parsea JSON
       └─► Actualiza 7 tarjetas de estadísticas (con animación)
       └─► Actualiza datos de Chart.js (barras y donut)
       └─► Actualiza tabla de top 5 enfermeros
```

---

## 17. API REST Completa

### 17.1. Turnos — `/api/turnos`

#### GET /api/turnos
- **Descripción:** Lista todos los turnos del sistema.
- **Roles:** ENFERMERIA, MEDICINA, SUPERVISOR
- **Respuesta 200:**
```json
[
  {"id": 1, "nombre": "Mañana", "horaInicio": "07:00:00", "horaFin": "15:00:00"},
  {"id": 2, "nombre": "Tarde",  "horaInicio": "15:00:00", "horaFin": "23:00:00"},
  {"id": 3, "nombre": "Noche",  "horaInicio": "23:00:00", "horaFin": "07:00:00"}
]
```

#### GET /api/turnos/{id}
- **Roles:** ENFERMERIA, MEDICINA, SUPERVISOR
- **Respuesta 200:** objeto Turno
- **Respuesta 404:** si no existe

#### GET /api/turnos/nombre/{nombre}
- **Parámetro path:** nombre del turno (ej: `Mañana`)
- **Respuesta 200:** objeto Turno, **404** si no existe

#### POST /api/turnos
- **Roles:** SUPERVISOR
- **Body:**
```json
{"nombre": "Extra", "horaInicio": "06:00:00", "horaFin": "07:00:00"}
```
- **Respuesta 201:** turno creado, **400** si nombre duplicado

#### PUT /api/turnos/{id}
- **Roles:** SUPERVISOR
- **Body:** mismos campos que POST
- **Respuesta 200:** turno actualizado, **404** si no existe

#### DELETE /api/turnos/{id}
- **Roles:** SUPERVISOR
- **Respuesta 204:** sin cuerpo, **404** si no existe

---

### 17.2. Pacientes — `/api/pacientes`

#### GET /api/pacientes
- **Roles:** ENFERMERIA, MEDICINA, SUPERVISOR
- **Respuesta 200:**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellidos": "Pérez García",
    "fechaNacimiento": "1975-03-15",
    "numeroHistoriaClinica": "NHC-001",
    "habitacion": "101A"
  }
]
```

#### GET /api/pacientes/{id}
- **Respuesta 200:** objeto Paciente, **404** si no existe

#### GET /api/pacientes/historia/{numeroHistoriaClinica}
- **Parámetro path:** NHC del paciente
- **Respuesta 200:** objeto Paciente, **404** si no existe

#### POST /api/pacientes
- **Roles:** MEDICINA, SUPERVISOR
- **Body:**
```json
{
  "nombre": "María",
  "apellidos": "López Ruiz",
  "fechaNacimiento": "1990-07-22",
  "numeroHistoriaClinica": "NHC-002",
  "habitacion": "205B"
}
```
- **Respuesta 201:** paciente creado, **400** si NHC duplicado

#### PUT /api/pacientes/{id}
- **Roles:** MEDICINA, SUPERVISOR
- **Respuesta 200:** paciente actualizado

#### DELETE /api/pacientes/{id}
- **Roles:** SUPERVISOR
- **Respuesta 204**

---

### 17.3. Usuarios — `/api/usuarios`

> **Acceso exclusivo para SUPERVISOR**

#### GET /api/usuarios
- **Respuesta 200:** lista de usuarios (sin campo `password`)
```json
[
  {"id": 1, "nombre": "Ana", "apellidos": "García López", "username": "enfermera", "rol": "ENFERMERIA"}
]
```

#### POST /api/usuarios
- **Body:**
```json
{
  "nombre": "Pedro",
  "apellidos": "Sánchez",
  "username": "pedro.sanchez",
  "password": "miPassword123",
  "rol": "ENFERMERIA"
}
```
- **Respuesta 201:** usuario creado (password encriptado, no visible en respuesta)
- **Respuesta 400:** si username ya existe

#### PUT /api/usuarios/{id}
- Si `password` se omite o es blank, no se cambia la contraseña.
- **Respuesta 200:** usuario actualizado

#### DELETE /api/usuarios/{id}
- **Respuesta 204**

---

### 17.4. Tareas Clínicas — `/api/tareas`

#### GET /api/tareas
- **Roles:** todos
- **Respuesta 200:** array de tareas con objetos anidados (usuario, paciente, turno)

#### GET /api/tareas/{id}
- **Respuesta 200:** tarea completa, **404** si no existe

#### GET /api/tareas/usuario/{usuarioId}
- Tareas asignadas a un enfermero específico

#### GET /api/tareas/paciente/{pacienteId}
- Tareas de un paciente específico

#### GET /api/tareas/turno/{turnoId}
- Tareas de un turno específico

#### GET /api/tareas/estado/{estado}
- Valores: `PENDIENTE`, `EN_CURSO`, `REALIZADA`, `CANCELADA`

#### GET /api/tareas/prioridad/{prioridad}
- Valores: `URGENTE`, `ALTA`, `MEDIA`, `BAJA`

#### POST /api/tareas
- **Roles:** MEDICINA, SUPERVISOR
- **Body:**
```json
{
  "descripcion": "Administrar 500mg ibuprofeno",
  "tipo": "MEDICACION",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "fecha": "2026-04-30T10:00:00",
  "observaciones": "Paciente con fiebre 38.5°C",
  "asignadoA": {"id": 1},
  "paciente": {"id": 3},
  "turno": {"id": 1}
}
```
- **Respuesta 201:** tarea creada con relaciones expandidas
- **Respuesta 400:** si alguna relación no existe

#### PUT /api/tareas/{id}
- **Roles:** ENFERMERIA, MEDICINA, SUPERVISOR
- Usado por enfermería para actualizar estado y observaciones
- **Respuesta 200:** tarea actualizada

#### DELETE /api/tareas/{id}
- **Roles:** SUPERVISOR
- **Respuesta 204**

---

### 17.5. Dashboard SSE — `/api/supervisor/dashboard/stream`

#### GET /api/supervisor/dashboard/stream
- **Roles:** SUPERVISOR
- **Content-Type:** `text/event-stream`
- **Descripción:** Streaming infinito de estadísticas del dashboard. Emite un evento cada 5 segundos.
- No hay body de request. La conexión se mantiene abierta hasta que el cliente la cierra.
- Los datos JSON se describen en la sección 10.1.

---

## 18. Estructura de Directorios Completa

```
D:\IntelliJ\
├── CLAUDE.md                                    # Instrucciones del proyecto (TFG)
├── DOCUMENTACION_TECNICA.md                     # Este documento
├── pom.xml                                      # Dependencias y build Maven
└── src/
    ├── main/
    │   ├── java/com/hospital/meditrack/
    │   │   ├── MeditrackApplication.java         # Punto de entrada (@SpringBootApplication)
    │   │   ├── config/
    │   │   │   ├── CustomUserDetailsService.java # Integración Spring Security ↔ BD
    │   │   │   ├── DataLoader.java               # Carga usuarios de prueba al iniciar
    │   │   │   ├── PasswordEncoderConfig.java    # Bean BCryptPasswordEncoder
    │   │   │   └── SecurityConfig.java           # Reglas de seguridad y autorización
    │   │   ├── controller/
    │   │   │   ├── TurnoController.java          # REST /api/turnos
    │   │   │   ├── PacienteController.java       # REST /api/pacientes
    │   │   │   ├── UsuarioController.java        # REST /api/usuarios
    │   │   │   ├── TareaClinicaController.java   # REST /api/tareas
    │   │   │   ├── SupervisorRealtimeController.java # SSE /api/supervisor/dashboard/stream
    │   │   │   ├── WebController.java            # Web / y /login
    │   │   │   ├── EnfermeriaWebController.java  # Web /enfermeria/**
    │   │   │   ├── MedicinaWebController.java    # Web /medicina/**
    │   │   │   └── SupervisorWebController.java  # Web /supervisor/**
    │   │   ├── model/
    │   │   │   ├── dto/
    │   │   │   │   ├── DashboardData.java        # DTO principal SSE
    │   │   │   │   ├── DashboardStats.java       # DTO estadísticas generales
    │   │   │   │   └── UsuarioConTareas.java     # DTO ranking enfermeros
    │   │   │   ├── entity/
    │   │   │   │   ├── Turno.java
    │   │   │   │   ├── Paciente.java
    │   │   │   │   ├── Usuario.java
    │   │   │   │   └── TareaClinica.java
    │   │   │   └── enums/
    │   │   │       ├── Rol.java
    │   │   │       ├── TipoTarea.java
    │   │   │       ├── Prioridad.java
    │   │   │       └── EstadoTarea.java
    │   │   ├── repository/
    │   │   │   ├── TurnoRepository.java
    │   │   │   ├── PacienteRepository.java
    │   │   │   ├── UsuarioRepository.java
    │   │   │   └── TareaClinicaRepository.java
    │   │   └── service/
    │   │       ├── TurnoService.java
    │   │       ├── PacienteService.java
    │   │       ├── UsuarioService.java
    │   │       ├── TareaClinicaService.java
    │   │       └── DashboardService.java
    │   └── resources/
    │       ├── application.properties            # Configuración de producción/desarrollo
    │       ├── application-test.properties       # Configuración para tests
    │       ├── data.sql                          # Datos iniciales (3 turnos)
    │       └── templates/
    │           ├── login.html
    │           ├── fragments/
    │           │   └── header.html              # head + navbar reutilizables
    │           ├── enfermeria/
    │           │   ├── dashboard.html
    │           │   └── mis-tareas.html
    │           ├── medicina/
    │           │   ├── dashboard.html
    │           │   ├── crear-tarea.html
    │           │   ├── pacientes.html
    │           │   └── crear-paciente.html
    │           └── supervisor/
    │               ├── dashboard.html
    │               ├── carga-trabajo.html
    │               ├── reasignar-tareas.html
    │               ├── usuarios.html
    │               └── dashboard-realtime.html
    └── test/
        └── java/com/hospital/meditrack/
            ├── MeditrackApplicationTests.java    # Test de carga del contexto Spring
            ├── SecurityConfigTest.java           # Tests de seguridad y permisos
            ├── Dashboard/
            │   └── DashboardServiceTest.java
            ├── Turno/
            │   ├── TurnoRepositoryTest.java
            │   ├── TurnoServiceTest.java
            │   └── TurnoControllerTest.java
            ├── Paciente/
            │   ├── PacienteRepositoryTest.java
            │   ├── PacienteServiceTest.java
            │   └── PacienteControllerTest.java
            ├── Usuario/
            │   ├── UsuarioRepositoryTest.java
            │   ├── UsuarioServiceTest.java
            │   └── UsuarioControllerTest.java
            └── TareaClinica/
                ├── TareaClinicaRepositoryTest.java
                ├── TareaClinicaServiceTest.java
                └── TareaClinicaControllerTest.java
```

---

## 19. Convenciones de Código

### 19.1. Nomenclatura

- **Español para nombres de negocio**: clases, métodos, variables usan español (`obtenerTodos`, `tareaClinica`, `numeroHistoriaClinica`). Esto facilita la lectura del código en el contexto del TFG y del dominio hospitalario.
- **CamelCase** para clases y métodos (Java estándar).
- **snake_case** para nombres de columnas y tablas en BD (`numero_historia_clinica`, `hora_inicio`).
- **SCREAMING_SNAKE_CASE** para valores de enum (`EN_CURSO`, `CONTROL_SIGNOS_VITALES`).
- **Sufijo `L`** para literales Long: `1L`, `2L` (no `1`, `2`).

### 19.2. Uso de Lombok

| Anotación | Uso |
|---|---|
| `@Data` | En entidades simples: genera getters, setters, equals, hashCode, toString |
| `@Getter` + `@Setter` | En Usuario: control fino (excluye password de toString) |
| `@NoArgsConstructor` | Requerido por JPA para instanciación |
| `@AllArgsConstructor` | Para constructores completos en tests y DTOs |
| `@ToString(exclude="password")` | Seguridad: evita loguear contraseñas |
| `@EqualsAndHashCode` | En Usuario: comparación por valor |

### 19.3. Anotaciones Spring

- `@Entity` + `@Table(name="...")`: Mapeo explícito al nombre de tabla en BD.
- `@Column(nullable=false, length=X)`: Restricciones a nivel de BD (Hibernate genera el DDL correcto) y validación.
- `@Enumerated(EnumType.STRING)`: Los enum se guardan como texto en BD (no como entero), facilitando la legibilidad de los datos.
- `@JsonIgnoreProperties`: Controla la serialización JSON en relaciones `@ManyToOne` evitando referencias circulares.
- `@Transactional(readOnly=true)`: Para operaciones de solo lectura, optimiza el contexto de persistencia (sin flush, sin dirty checking).

### 19.4. Estructura de Commits (Conventional Commits)

```
<tipo>: <descripción corta en presente>

<descripción detallada opcional>

<información adicional>
```

| Prefijo | Uso |
|---|---|
| `feat:` | Nueva funcionalidad |
| `test:` | Añadir o modificar tests |
| `fix:` | Corrección de bug |
| `refactor:` | Refactorización sin cambio funcional |
| `config:` | Cambios en configuración |
| `docs:` | Documentación |

---

## 20. Despliegue y Ejecución

### 20.1. Requisitos Previos

| Requisito | Versión | Verificación |
|---|---|---|
| Java JDK | 21 (Eclipse Adoptium recomendado) | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| PostgreSQL | 15+ | `psql --version` |
| Git | cualquiera | `git --version` |

**Preparación de la base de datos:**
```sql
-- Conectar a PostgreSQL y ejecutar:
CREATE DATABASE meditrack_db;
-- Usuario postgres con password admin123 (ya configurado por defecto)
```

### 20.2. Comandos de Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/pvelaalm/MediTrack.git
cd MediTrack

# Compilar el proyecto
mvn clean install

# Ejecutar solo los tests
mvn test

# Ejecutar un test específico
mvn test -Dtest=TurnoServiceTest

# Ejecutar tests de una clase con patrón
mvn test -Dtest=*ControllerTest

# Arrancar la aplicación
mvn spring-boot:run

# Construir JAR ejecutable
mvn clean package
java -jar target/meditrack-0.0.1-SNAPSHOT.jar
```

### 20.3. Acceso a la Aplicación

Una vez arrancada:

| URL | Descripción |
|---|---|
| `http://localhost:8080/login` | Página de login |
| `http://localhost:8080/` | Redirige al dashboard según rol |
| `http://localhost:8080/supervisor/dashboard-realtime` | Dashboard SSE (requiere SUPERVISOR) |

### 20.4. Usuarios de Prueba

Creados automáticamente por `DataLoader` al primer arranque:

| Username | Password | Rol |
|---|---|---|
| `enfermera` | `enfermera123` | ENFERMERIA |
| `medico` | `medico123` | MEDICINA |
| `supervisor` | `supervisor123` | SUPERVISOR |

---

## 21. Pruebas y Validación

### 21.1. Tests Automatizados

```bash
# Ejecutar la suite completa
mvn test

# Resultado esperado:
# Tests run: 69, Failures: 0, Errors: 0, Skipped: 0
# BUILD SUCCESS
```

### 21.2. Pruebas Manuales por Rol

**SUPERVISOR:**
1. Login con `supervisor / supervisor123`
2. Verificar redirección a `/supervisor/dashboard`
3. Ir a "Dashboard Tiempo Real" → verificar badge "Conectado" en verde
4. Esperar 5 segundos → verificar que el timestamp se actualiza
5. Ir a "Reasignar Tareas" → reasignar una tarea a otro enfermero
6. Volver al Dashboard Tiempo Real → verificar que la tabla de top enfermeros refleja el cambio

**MEDICINA:**
1. Login con `medico / medico123`
2. Ir a "Nueva Tarea" → crear una tarea para un paciente existente
3. Ir a "Pacientes" → crear un nuevo paciente
4. Verificar que las tareas creadas aparecen en "Dashboard"

**ENFERMERIA:**
1. Login con `enfermera / enfermera123`
2. Ir a "Mis Tareas" → verificar las tareas asignadas
3. Cambiar el estado de una tarea a "EN_CURSO"
4. Añadir observaciones a una tarea
5. Intentar acceder a `/medicina/dashboard` → verificar 403 Forbidden

### 21.3. Pruebas de Seguridad

Con el navegador o curl, verificar que:
- Un usuario no autenticado que accede a `/supervisor/dashboard` es redirigido a `/login`.
- Un usuario ENFERMERIA que accede a `/api/usuarios` recibe `403 Forbidden`.
- Un usuario MEDICINA que accede a `/api/supervisor/dashboard/stream` recibe `403`.

---

## 22. Limitaciones Conocidas

1. **No es un sistema médico certificado.** MediTrack no cumple normativas de software médico (CE marking, FDA 510(k), IEC 62304). No debe usarse en entornos clínicos reales.

2. **Sin historial de reasignaciones.** Cuando se reasigna una tarea, el usuario anterior no queda registrado. Para auditoría completa sería necesaria una entidad `HistoricoReasignacion`.

3. **SSE no escala horizontalmente.** Cada conexión SSE mantiene una thread activa. Con 1-5 supervisores concurrentes no hay problema, pero en un entorno con muchos usuarios sería necesario un broker de eventos (Redis Pub/Sub, Kafka) y un modelo push basado en eventos de dominio en lugar de polling periódico.

4. **Sin gestión de sesiones distribuidas.** La sesión HTTP se almacena en memoria del servidor. En un entorno con múltiples instancias (balanceo de carga), los usuarios perderían la sesión si cambian de servidor. Requeriría Spring Session + Redis.

5. **Sin rate limiting.** Los endpoints API no tienen protección contra abuso (muchas peticiones por segundo). En producción se implementaría con Spring Cloud Gateway o una solución similar.

6. **Datos de prueba limitados.** El sistema arranca solo con 3 turnos y 3 usuarios de prueba. Para demostrar el dashboard realtime con datos significativos, hay que crear manualmente pacientes y tareas.

7. **Sin paginación.** Los listados de tareas, pacientes y usuarios devuelven todos los registros. Con volúmenes grandes esto sería ineficiente.

---

## 23. Trabajo Futuro

Las siguientes mejoras se proponen para una versión post-TFG:

| Mejora | Complejidad | Impacto |
|---|---|---|
| Historial de reasignaciones | Media | Alta (auditoría) |
| Paginación en listados | Baja | Alta (rendimiento) |
| Notificaciones push (WebPush) | Alta | Alta (UX) |
| Exportar reportes a PDF/Excel | Media | Media |
| Autenticación JWT (API stateless) | Media | Alta (escalabilidad) |
| Spring Session + Redis | Media | Alta (escalabilidad) |
| Historial clínico completo del paciente | Alta | Alta (funcional) |
| App móvil (React Native / Flutter) | Muy alta | Alta (accesibilidad) |
| Integración con sistemas HL7/FHIR | Muy alta | Alta (interoperabilidad) |
| Multi-tenant (varios hospitales) | Muy alta | Alta (comercial) |
| Análisis predictivo con ML | Muy alta | Media |
| Caché Redis para estadísticas del dashboard | Media | Media (rendimiento SSE) |

---

## 24. Referencias y Bibliografía

### Documentación Oficial

- **Spring Boot Reference Documentation** (4.0.x): https://docs.spring.io/spring-boot/
- **Spring Security Reference** (6.x): https://docs.spring.io/spring-security/reference/
- **Spring Data JPA Reference**: https://docs.spring.io/spring-data/jpa/reference/
- **Spring WebFlux Documentation**: https://docs.spring.io/spring-framework/reference/web/webflux.html
- **Thymeleaf Documentation** (3.x): https://www.thymeleaf.org/documentation.html
- **Thymeleaf + Spring Security extras**: https://github.com/thymeleaf/thymeleaf-extras-springsecurity
- **Project Reactor**: https://projectreactor.io/docs/core/release/reference/

### Librerías Frontend

- **Bootstrap 5**: https://getbootstrap.com/docs/5.3/
- **Bootstrap Icons**: https://icons.getbootstrap.com/
- **Chart.js 4**: https://www.chartjs.org/docs/latest/

### Herramientas

- **Lombok**: https://projectlombok.org/features/
- **JUnit 5**: https://junit.org/junit5/docs/current/user-guide/
- **Mockito**: https://javadoc.io/doc/org.mockito/mockito-core/latest/

### Seguridad

- **OWASP: Password Storage Cheat Sheet**: https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
- **BCrypt specification**: Provos & Mazières (1999). "A Future-Adaptable Password Scheme."

### Arquitectura

- **Conventional Commits specification**: https://www.conventionalcommits.org/
- **MDN Web Docs — Server-Sent Events**: https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events

---

## 25. Anexos

### Anexo A: Glosario de Términos

| Término | Definición |
|---|---|
| **TFG** | Trabajo Fin de Grado. Proyecto académico de síntesis de competencias al finalizar el grado. |
| **CRUD** | Create, Read, Update, Delete. Las cuatro operaciones básicas sobre datos persistentes. |
| **JPA** | Jakarta Persistence API. Especificación Java para mapeo objeto-relacional. |
| **ORM** | Object-Relational Mapping. Técnica que mapea objetos Java a tablas de base de datos. |
| **RBAC** | Role-Based Access Control. Control de acceso basado en roles: los permisos se asignan a roles, no a usuarios individuales. |
| **SSE** | Server-Sent Events. Protocolo HTTP para envío unidireccional de eventos del servidor al cliente. |
| **WebFlux** | Framework reactivo de Spring para programación no bloqueante con Flux y Mono. |
| **Flux** | Tipo reactivo de Project Reactor que representa una secuencia de 0 a N elementos. |
| **BCrypt** | Función de hash para contraseñas con salt incorporado y factor de coste configurable. |
| **DTO** | Data Transfer Object. Objeto sin lógica de negocio usado para transferir datos entre capas. |
| **Bean** | Objeto gestionado por el contenedor de Spring (instanciado, configurado e inyectado automáticamente). |
| **NHC** | Número de Historia Clínica. Identificador único de un paciente en el sistema hospitalario. |
| **DDL** | Data Definition Language. Comandos SQL para crear/modificar estructuras de BD (CREATE, ALTER, DROP). |
| **Proxy dinámico** | Objeto generado en tiempo de ejecución por Spring que implementa una interfaz y añade comportamiento (transacciones, lazy loading, etc.). |
| **HQL / JPQL** | Hibernate Query Language / Jakarta Persistence Query Language. Lenguajes de consulta orientados a objetos. |
| **EventSource** | API de JavaScript nativa del navegador para consumir streams SSE. |
| **Conventional Commits** | Especificación de formato para mensajes de commit que facilita la generación automática de changelogs. |
| **Salt** | Valor aleatorio añadido a un password antes de hashearlo para prevenir ataques de rainbow table. |

---

### Anexo B: Comandos Útiles Git

```bash
# Ver estado del repositorio
git status

# Ver historial de commits (formato corto)
git log --oneline -20

# Ver diferencias no staged
git diff

# Añadir archivos al staging area
git add src/main/java/com/hospital/meditrack/service/DashboardService.java

# Crear commit con mensaje multilínea
git commit -m "feat: Crear DashboardService

- obtenerEstadisticasGenerales()
- obtenerCargaPorTurno()
- obtenerTop5EnfermerosConMasTareas()"

# Ver commits del proyecto
git log --oneline --graph

# Subir cambios a GitHub
git push origin main

# Crear y cambiar a una nueva rama
git checkout -b feature/nueva-funcionalidad

# Volver a main
git checkout main
```

---

### Anexo C: Ejemplo de Respuesta JSON — TareaClinica

```json
{
  "id": 1,
  "descripcion": "Administrar 500mg de paracetamol",
  "tipo": "MEDICACION",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "fecha": "2026-04-30T10:00:00",
  "observaciones": "Paciente con fiebre de 38.5°C",
  "asignadoA": {
    "id": 1,
    "nombre": "Ana",
    "apellidos": "García López",
    "username": "enfermera",
    "rol": "ENFERMERIA"
  },
  "paciente": {
    "id": 3,
    "nombre": "Juan",
    "apellidos": "Pérez Martínez",
    "fechaNacimiento": "1965-08-12",
    "numeroHistoriaClinica": "NHC-003",
    "habitacion": "302B"
  },
  "turno": {
    "id": 1,
    "nombre": "Mañana",
    "horaInicio": "07:00:00",
    "horaFin": "15:00:00"
  }
}
```

---

*Documentación generada el 30 de abril de 2026.*
*Proyecto MediTrack — TFG Pedro Vela — 100% completado.*
*69/69 tests pasando. 9 fases implementadas.*
