## 🎯 OBJETIVO PRINCIPAL

Completar el desarrollo del proyecto MediTrack de manera **AUTÓNOMA**, siguiendo una metodología incremental y documentando cada paso en Git.

---

## 📋 CONTEXTO DEL PROYECTO

**Proyecto:** MediTrack - Sistema de gestión de tareas clínicas hospitalarias  
**Tipo:** TFG (Trabajo Fin de Grado)  
**Stack:** Spring Boot 4.0.3, Java 21, PostgreSQL, Maven, Lombok  
**Repositorio:** https://github.com/pvelaalm/MediTrack.git  
**Rama principal:** main

---

## ⚙️ INFORMACIÓN TÉCNICA

### Base de Datos
Host: localhost:5432
Database: meditrack_db
User: postgres
Password: admin123

### Stack Completo
- Spring Boot 4.0.3
- Java 21 (Eclipse Adoptium)
- PostgreSQL
- Maven
- Lombok 1.18.42
- Spring Data JPA / Hibernate
- Spring Security (configurar en Fase 7)
- Thymeleaf (configurar en Fase 8)
- WebFlux (para Dashboard en Fase 9)
- JUnit 5 + Mockito

---

## 🗂️ ESTRUCTURA DE PAQUETES
com.hospital.meditrack/
├── controller/      # REST Controllers (@RestController)
├── service/         # Lógica de negocio (@Service + @Transactional)
├── repository/      # Spring Data JPA (interfaces)
├── model/
│   ├── entity/      # Entidades JPA (@Entity)
│   ├── dto/         # DTOs (crear según necesidad)
│   └── enums/       # Enumeraciones
├── config/          # Configuraciones (SecurityConfig, etc.)
├── event/           # Eventos de dominio (opcional)
└── exception/       # Manejo de excepciones personalizadas

---

## 📊 MODELO DE DATOS COMPLETO

### Enumeraciones (YA CREADAS ✅)
1. **Rol**: ENFERMERIA, MEDICINA, SUPERVISOR
2. **TipoTarea**: MEDICACION, CONTROL_SIGNOS_VITALES, CURA, HIGIENE, MOVILIZACION, OTRO
3. **Prioridad**: BAJA, MEDIA, ALTA, URGENTE
4. **EstadoTarea**: PENDIENTE, EN_CURSO, REALIZADA, CANCELADA

### Entidades

#### 1. Turno (YA COMPLETA ✅)
```java
@Entity
public class Turno {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nombre;
    
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
```
- Repository, Service, Controller, Tests: COMPLETOS ✅
- data.sql: 3 turnos (Mañana, Tarde, Noche)

#### 2. Paciente (CÓDIGO COMPLETO, TESTS PENDIENTES ⏳)
```java
@Entity
public class Paciente {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellidos;
    
    private LocalDate fechaNacimiento;
    
    @Column(unique = true, nullable = false)
    private String numeroHistoriaClinica;
    
    private String habitacion;
}
```
- **ACCIÓN INMEDIATA:** Crear tests (Repository, Service, Controller)

#### 3. Usuario (PENDIENTE - FASE 5 ⏳)
```java
@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellidos;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;  // Encriptado con BCrypt
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}
```
- **IMPORTANTE:** Usar BCryptPasswordEncoder para password
- Crear PasswordEncoderConfig en config/

#### 4. TareaClinica (PENDIENTE - FASE 6 ⏳)
```java
@Entity
public class TareaClinica {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTarea tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridad prioridad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTarea estado;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(length = 1000)
    private String observaciones;
    
    // RELACIONES
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario asignadoA;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;
}
```
- Esta es la entidad MÁS COMPLEJA (3 relaciones @ManyToOne)

---

## 🔄 METODOLOGÍA DE DESARROLLO

### Ciclo por Entidad (REPETIR PARA CADA UNA)

1. **Entity**
    - Crear clase con @Entity
    - Usar Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
    - Definir @Id con @GeneratedValue(strategy = IDENTITY)
    - Añadir constraints (@Column unique, nullable, length)

2. **Repository**
    - SIEMPRE interface (nunca clase)
    - Extends JpaRepository<Entity, Long>
    - Añadir métodos custom (findByNombre, findByNumeroHistoriaClinica, etc.)

3. **Service**
    - Métodos CRUD completos:
        - obtenerTodos() - @Transactional(readOnly=true)
        - obtenerPorId(Long id) - @Transactional(readOnly=true)
        - crear(Entity) - @Transactional + validaciones
        - actualizar(Long id, Entity) - @Transactional
        - eliminar(Long id) - @Transactional
    - **VALIDAR duplicados** antes de crear
    - Lanzar IllegalArgumentException con mensajes descriptivos

4. **Controller**
    - @RestController
    - @RequestMapping("/api/entidades")
    - Endpoints:
        - GET /api/entidades → obtenerTodos()
        - GET /api/entidades/{id} → obtenerPorId()
        - POST /api/entidades → crear()
        - PUT /api/entidades/{id} → actualizar()
        - DELETE /api/entidades/{id} → eliminar()
    - Usar ResponseEntity con códigos HTTP correctos (200, 201, 204, 400, 404)

5. **Tests**
    - **Repository Test** (2 tests mínimo):
        - deberiaGuardarYRecuperar
        - deberiaBuscarPorCampoUnico
    - **Service Test** (4 tests con Mockito):
        - deberiaObtenerTodos
        - deberiaObtenerPorId
        - deberiaCrear
        - noDeberiaCrearDuplicado
    - **Controller Test** (7 tests integración):
        - deberiaObtenerTodos
        - deberiaObtenerPorId
        - deberiaRetornarVacioSiNoExiste
        - deberiaCrear
        - noDeberiaCrearDuplicado
        - deberiaActualizar
        - deberiaEliminar
    - Usar @TestPropertySource(locations = "classpath:application-test.properties")
    - Usar @Transactional para rollback automático

6. **Commit**
    - Mensaje conventional commits:
      feat: Crear entidad [Nombre] con ciclo completo

- Entity con mapeo JPA
- Repository con métodos custom
- Service con validaciones
- Controller REST API
- Tests completos (Repository, Service, Controller)

Tests: X/X pasando

---

## 📦 GIT WORKFLOW

### Commits Atómicos por Fase

**Cada fase debe tener commits separados:**

```bash
# Ejemplo Fase 4 (Paciente)
git add src/test/java/com/hospital/meditrack/PacienteRepositoryTest.java
git commit -m "test: Añadir tests de PacienteRepository

- deberiaGuardarYRecuperarPaciente
- deberiaBuscarPorNumeroHistoriaClinica

Tests: 2/2 pasando"

git add src/test/java/com/hospital/meditrack/PacienteServiceTest.java
git commit -m "test: Añadir tests de PacienteService con Mockito

- deberiaObtenerTodos
- deberiaObtenerPorId
- deberiaCrearPaciente
- noDeberiaCrearPacienteDuplicado

Tests: 4/4 pasando"

git add src/test/java/com/hospital/meditrack/PacienteControllerTest.java
git commit -m "test: Añadir tests de integración de PacienteController

- deberiaObtenerTodosLosPacientes
- deberiaObtenerPorId
- deberiaRetornarVacioSiPacienteNoExiste
- deberiaCrearNuevoPaciente
- noDeberiaCrearPacienteDuplicado
- deberiaActualizarPacienteExistente
- deberiaEliminarPaciente

Tests: 7/7 pasando
Total Paciente: 13/13 tests pasando"
```

### Resumen de Fase

Al terminar cada fase completa:

```bash
git commit -m "feat: Completar Fase X - Entidad [Nombre]

Implementación completa:
✅ Entity con mapeo JPA
✅ Repository con Spring Data JPA
✅ Service con @Transactional
✅ Controller REST API
✅ Tests completos (13/13 pasando)

API disponible en: /api/[entidades]
Próxima fase: [descripción]"
```

---

## 🧪 CONFIGURACIÓN DE TESTS

### application-test.properties
Ya existe en: `src/test/resources/application-test.properties`

```properties
spring.sql.init.mode=never
spring.datasource.url=jdbc:postgresql://localhost:5432/meditrack_db
spring.datasource.username=postgres
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=create-drop
```

**USAR EN TODOS LOS TESTS:**
```java
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MiTest { ... }
```

---

## 🎯 PLAN DE TRABAJO COMPLETO

### ✅ FASE 1-3: COMPLETADAS
- Enumeraciones (Rol, TipoTarea, Prioridad, EstadoTarea)
- Turno (Entity, Repo, Service, Controller, Tests)
- data.sql con 3 turnos

### 🔄 FASE 4: EN PROGRESO (SIGUIENTE PASO INMEDIATO)
**Entidad Paciente - TESTS PENDIENTES**

**ACCIÓN INMEDIATA:**
1. Crear `PacienteRepositoryTest.java` (2 tests)
2. Crear `PacienteServiceTest.java` (4 tests con Mockito)
3. Crear `PacienteControllerTest.java` (7 tests)
4. Ejecutar tests: `mvn test`
5. Verificar: 13/13 tests pasando
6. Commits atómicos (3 commits separados)
7. Commit resumen de fase

**Archivos de referencia:**
- `TurnoRepositoryTest.java`
- `TurnoServiceTest.java`
- `TurnoControllerTest.java`

**Adaptar a Paciente:**
- Campo único: `numeroHistoriaClinica` (en lugar de `nombre`)
- Campos: nombre, apellidos, fechaNacimiento, habitacion

---

### ⏳ FASE 5: USUARIO CON BCRYPT
1. Crear `PasswordEncoderConfig.java` en config/
```java
   @Configuration
   public class PasswordEncoderConfig {
       @Bean
       public PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
       }
   }
```
2. Entity Usuario con password encriptado
3. Repository, Service (encriptar en crear/actualizar), Controller
4. Tests (13 tests)
5. Commits atómicos

---

### ⏳ FASE 6: TAREACLINICA (LA MÁS COMPLEJA)
1. Entity con 3 relaciones @ManyToOne
2. Repository con métodos:
    - findByPacienteId
    - findByAsignadoAId
    - findByTurnoId
    - findByEstado
    - findByPrioridad
3. Service con validaciones de relaciones
4. Controller con endpoints adicionales para filtros
5. Tests (mínimo 15 tests por la complejidad)
6. Commits atómicos

---

### ⏳ FASE 7: SPRING SECURITY
1. Crear `SecurityConfig.java`
2. Configurar autenticación por roles (RBAC)
3. Endpoints protegidos según rol:
    - ENFERMERIA: Solo consultar y actualizar tareas asignadas
    - MEDICINA: Crear tareas y asignar
    - SUPERVISOR: Todo (reasignar, visualizar carga)
4. Login personalizado
5. Tests de seguridad
6. Commits atómicos

---

### ⏳ FASE 8: VISTAS THYMELEAF
1. Crear carpeta `templates/` con subcarpetas:
    - `enfermeria/`
    - `medicina/`
    - `supervisor/`
    - `common/`
2. Vistas por rol con Bootstrap/Tailwind
3. Formularios para CRUD
4. Controladores MVC (además de REST)
5. Commits atómicos

---

### ⏳ FASE 9: DASHBOARD SUPERVISOR CON WEBFLUX
1. Configurar WebFlux para SSE (Server-Sent Events)
2. Crear endpoint de streaming: `/api/supervisor/dashboard/stream`
3. Vista en tiempo real de carga de trabajo
4. Gráficos con Chart.js
5. Tests
6. Commits atómicos

---

## ⚠️ REGLAS CRÍTICAS

### 1. @Transactional
```java
// ✅ SOLO en Service
@Service
public class MiService {
    @Transactional(readOnly = true)
    public List<Entity> obtener() { ... }
    
    @Transactional
    public Entity crear(Entity e) { ... }
}

// ❌ NUNCA en Repository o Controller
```

### 2. Repository SIEMPRE Interface
```java
// ✅ CORRECTO
public interface MiRepository extends JpaRepository<Entity, Long> { }

// ❌ INCORRECTO
public class MiRepository { }
```

### 3. IDs Tipo Long con Sufijo L
```java
// ✅ CORRECTO
Long id = 1L;
service.obtenerPorId(1L);

// ❌ INCORRECTO
Long id = 1;
```

### 4. data.sql Solo Datos de Referencia
✅ SÍ: Turnos fijos (Mañana, Tarde, Noche)
❌ NO: Pacientes, usuarios, tareas de prueba

### 5. Validaciones en Service
```java
@Transactional
public Entity crear(Entity entity) {
    // Validar duplicados
    if (repo.findByCampoUnico(entity.getCampo()).isPresent()) {
        throw new IllegalArgumentException("Ya existe: " + entity.getCampo());
    }
    return repo.save(entity);
}
```

### 6. Tests con application-test.properties
```java
@SpringBootTest
@Transactional  // Rollback automático
@TestPropertySource(locations = "classpath:application-test.properties")
class MiTest { ... }
```

---

## 📝 FORMATO DE COMMITS

### Prefijos Conventional Commits:
- `feat:` - Nueva funcionalidad
- `test:` - Añadir/modificar tests
- `fix:` - Corrección de bug
- `refactor:` - Refactorización sin cambio funcional
- `config:` - Cambios en configuración
- `docs:` - Documentación

### Estructura:
<tipo>: <descripción corta>
<descripción detallada>

Punto 1
Punto 2

<información adicional>

---

## 🚀 EJECUCIÓN

### Comandos Útiles:
```bash
# Compilar
mvn clean install

# Tests
mvn test

# Test específico
mvn test -Dtest=PacienteRepositoryTest

# Arrancar app
mvn spring-boot:run
```

### Verificar Tests:
Después de cada fase, ejecutar:
```bash
mvn test
```
**TODOS los tests deben pasar (verde) antes de hacer commit.**

---
## 📊 PROGRESO ACTUAL

### Completado:
- ✅ Fase 1-3: Enumeraciones + Turno completo (13 tests)
- ✅ Fase 4: Paciente completo (13 tests)
- ✅ Fase 5: Usuario con BCrypt (13 tests)
- ✅ Fase 6: TareaClinica con relaciones (18 tests)
- ✅ Fase 7: Spring Security con RBAC (8 tests)
- ✅ Fase 8: Vistas Thymeleaf por Rol (sin tests adicionales)

### En Progreso:
- 🔄 Fase 9: Dashboard Supervisor con WebFlux y SSE

### Total Tests Actual:
- Turno: 13/13 ✅
- Paciente: 13/13 ✅
- Usuario: 13/13 ✅
- TareaClinica: 18/18 ✅
- Security: 8/8 ✅
- App: 1/1 ✅
- **Total: 66/66** ✅

---

## 🎯 SIGUIENTE PASO INMEDIATO

### FASE 8 - VISTAS THYMELEAF POR ROL

**Objetivo:** Crear interfaces web con Thymeleaf separadas por rol (ENFERMERIA, MEDICINA, SUPERVISOR) con formularios funcionales, navegación intuitiva y diseño responsive con Bootstrap 5.

---

### ARQUITECTURA DE VISTAS

Crear la siguiente estructura de plantillas Thymeleaf en `src/main/resources/templates/`:
templates/
├── login.html                      # Página de login con diseño moderno
├── index.html                      # Redirige según rol (opcional)
├── error.html                      # Página de error genérica
│
├── fragments/
│   ├── header.html                 # Header común con navbar Bootstrap y menú por rol
│   ├── footer.html                 # Footer común (opcional)
│   └── sidebar.html                # Sidebar común (opcional)
│
├── enfermeria/
│   ├── dashboard.html              # Dashboard con resumen de tareas asignadas
│   ├── mis-tareas.html             # Lista de tareas con filtros y actualización de estado
│   └── paciente-detalle.html       # Ver detalles de paciente (opcional)
│
├── medicina/
│   ├── dashboard.html              # Dashboard con estadísticas generales
│   ├── crear-tarea.html            # Formulario para crear tarea clínica
│   ├── pacientes.html              # Lista de pacientes
│   └── crear-paciente.html         # Formulario para crear paciente
│
└── supervisor/
├── dashboard.html              # Dashboard con vista general del sistema
├── carga-trabajo.html          # Vista de carga por turno
├── reasignar-tareas.html       # Formulario para reasignar tareas
└── usuarios.html               # Lista y gestión de usuarios

---

### FUNCIONALIDADES POR ROL

#### ROL: ENFERMERIA
**Puede:**
- Ver dashboard con sus tareas asignadas
- Ver lista completa de sus tareas (filtrar por estado, prioridad)
- Actualizar estado de tarea (PENDIENTE → EN_CURSO → REALIZADA)
- Añadir observaciones a tareas
- Ver información básica de pacientes asociados a sus tareas

**No puede:**
- Crear tareas
- Asignar tareas
- Eliminar tareas
- Gestionar usuarios

#### ROL: MEDICINA
**Puede:**
- Ver dashboard con estadísticas generales
- Crear nuevas tareas clínicas (formulario completo)
- Asignar tareas a personal de enfermería
- Ver todas las tareas del sistema
- Crear pacientes (formulario completo)
- Ver lista de pacientes

**No puede:**
- Eliminar usuarios
- Reasignar tareas ya asignadas (solo SUPERVISOR)
- Gestionar usuarios del sistema

#### ROL: SUPERVISOR
**Puede:**
- Ver dashboard con vista general del sistema
- Ver carga de trabajo por turno
- Reasignar tareas entre usuarios
- Ver lista de todos los usuarios
- Acceso completo a todas las funcionalidades

---

### ACCIÓN REQUERIDA

#### 1. **Actualizar SecurityConfig.java**

Modificar `src/main/java/com/hospital/meditrack/config/SecurityConfig.java` para añadir:

**Cambios necesarios:**
- Activar formulario de login con `.formLogin()` en lugar de solo HTTP Basic
- Definir página de login personalizada: `/login`
- Configurar logout con redirección a `/login?logout`
- Permitir acceso público a recursos estáticos (`/css/**`, `/js/**`, `/images/**`, `/webjars/**`)
- Proteger rutas web por rol:
   - `/enfermeria/**` → `hasRole("ENFERMERIA")`
   - `/medicina/**` → `hasRole("MEDICINA")`
   - `/supervisor/**` → `hasRole("SUPERVISOR")`
- Permitir acceso a `/login` y `/error` sin autenticación
- Mantener las reglas de API REST de la Fase 7 (no eliminar)
- Mantener HTTP Basic para API REST (compatibilidad)

**IMPORTANTE:** Mantener CSRF habilitado (por defecto) para formularios web.

---

#### 2. **Crear WebController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/WebController.java`

**Responsabilidad:** Controlador principal para navegación web (NO REST).

**Endpoints necesarios:**
- `GET /login` → Devuelve vista "login"
- `GET /` y `GET /index` → Redirige según rol del usuario autenticado:
   - ROLE_ENFERMERIA → `redirect:/enfermeria/dashboard`
   - ROLE_MEDICINA → `redirect:/medicina/dashboard`
   - ROLE_SUPERVISOR → `redirect:/supervisor/dashboard`

**Nota:** Usar `Authentication` para obtener el rol del usuario.

---

#### 3. **Crear EnfermeriaWebController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/EnfermeriaWebController.java`

**Responsabilidad:** Vistas para el rol ENFERMERIA.

**Endpoints necesarios:**

1. `GET /enfermeria/dashboard`
   - Obtener usuario autenticado desde `Authentication`
   - Obtener tareas asignadas al usuario (`tareaService.obtenerPorUsuario()`)
   - Pasar al modelo: `usuario`, `tareas`, `totalTareas`
   - Devolver vista: `enfermeria/dashboard`

2. `GET /enfermeria/mis-tareas`
   - Listar todas las tareas del usuario autenticado
   - Devolver vista: `enfermeria/mis-tareas`

3. `POST /enfermeria/tareas/{id}/actualizar-estado`
   - Parámetros: `nuevoEstado` (String), `observaciones` (String, opcional)
   - Actualizar estado de la tarea
   - Añadir observaciones si se proporcionan
   - Redirigir a `/enfermeria/mis-tareas`

---

#### 4. **Crear MedicinaWebController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/MedicinaWebController.java`

**Responsabilidad:** Vistas para el rol MEDICINA.

**Endpoints necesarios:**

1. `GET /medicina/dashboard`
   - Obtener estadísticas: total tareas, total pacientes
   - Obtener tareas recientes (últimas 5)
   - Devolver vista: `medicina/dashboard`

2. `GET /medicina/crear-tarea`
   - Obtener listas para selects del formulario:
      - Pacientes (todos)
      - Usuarios con rol ENFERMERIA
      - Turnos (todos)
   - Pasar objeto vacío `new TareaClinica()` para el formulario
   - Devolver vista: `medicina/crear-tarea`

3. `POST /medicina/crear-tarea`
   - Recibir `@ModelAttribute TareaClinica tarea`
   - Llamar a `tareaService.crear(tarea)`
   - Redirigir a `/medicina/dashboard`

4. `GET /medicina/pacientes`
   - Listar todos los pacientes
   - Devolver vista: `medicina/pacientes`

5. `GET /medicina/crear-paciente`
   - Pasar objeto vacío `new Paciente()`
   - Devolver vista: `medicina/crear-paciente`

6. `POST /medicina/crear-paciente`
   - Recibir `@ModelAttribute Paciente paciente`
   - Llamar a `pacienteService.crear(paciente)`
   - Redirigir a `/medicina/pacientes`

---

#### 5. **Crear SupervisorWebController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/SupervisorWebController.java`

**Responsabilidad:** Vistas para el rol SUPERVISOR.

**Endpoints necesarios:**

1. `GET /supervisor/dashboard`
   - Obtener totales: tareas, usuarios, turnos
   - Devolver vista: `supervisor/dashboard`

2. `GET /supervisor/carga-trabajo`
   - Obtener todos los turnos
   - Calcular tareas por turno (Map<String, Long>)
   - Devolver vista: `supervisor/carga-trabajo`

3. `GET /supervisor/usuarios`
   - Listar todos los usuarios
   - Devolver vista: `supervisor/usuarios`

4. `GET /supervisor/reasignar-tareas`
   - Obtener todas las tareas
   - Obtener todos los usuarios
   - Devolver vista: `supervisor/reasignar-tareas`

5. `POST /supervisor/reasignar-tarea/{id}`
   - Parámetro: `nuevoUsuarioId` (Long)
   - Obtener tarea por id
   - Obtener nuevo usuario por id
   - Actualizar tarea con nuevo usuario asignado
   - Redirigir a `/supervisor/reasignar-tareas`

---

#### 6. **Crear Vistas HTML con Thymeleaf**

**Tecnologías a usar:**
- Thymeleaf con sintaxis estándar
- Bootstrap 5 (CDN: https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css)
- Bootstrap Icons (CDN: https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css)
- Thymeleaf Security extras para `sec:authorize` (ya incluido en dependencies)

**Requisitos de diseño:**
- Diseño responsive (mobile-first)
- Colores coherentes por rol (opcional: enfermería=azul, medicina=verde, supervisor=morado)
- Navegación clara con navbar Bootstrap
- Formularios con validación HTML5 (`required`, `type`, etc.)
- Tablas con clase `table table-hover`
- Badges para estados (pendiente=warning, en_curso=primary, realizada=success)
- Badges para prioridades (urgente=danger, alta=warning, media/baja=secondary)

**Vistas a crear (MÍNIMO):**

##### A. login.html
- Formulario de login centrado
- Campo username y password
- Botón "Iniciar Sesión"
- Mostrar mensaje de error si `param.error` existe
- Mostrar mensaje de logout si `param.logout` existe
- Diseño moderno con gradiente o card Bootstrap
- Incluir usuarios de prueba como texto informativo

##### B. fragments/header.html
- Fragmento `head` con imports de Bootstrap y Bootstrap Icons
- Fragmento `navbar` con:
   - Logo/nombre "MediTrack"
   - Menú dinámico según rol usando `sec:authorize`
   - Nombre de usuario autenticado (`sec:authentication="name"`)
   - Botón de logout (formulario POST a `/logout`)

##### C. enfermeria/dashboard.html
- Header con `th:replace` del navbar
- Card con total de tareas asignadas
- Tabla con tareas recientes (máximo 5)
- Columnas: Paciente, Descripción, Estado, Prioridad
- Botón "Ver Todas las Tareas" → `/enfermeria/mis-tareas`

##### D. enfermeria/mis-tareas.html
- Tabla completa de tareas del usuario
- Por cada tarea, formulario inline para cambiar estado
- Select con estados (PENDIENTE, EN_CURSO, REALIZADA)
- Campo textarea para observaciones (opcional)
- Botón "Actualizar" por tarea

##### E. medicina/crear-tarea.html
- Formulario con `th:object="${tarea}"` y `th:field`
- Select para paciente (mostrar nombre + HC)
- Select para usuario asignado (solo rol ENFERMERIA)
- Select para turno (mostrar nombre + horario)
- Textarea para descripción
- Selects para tipo, prioridad, estado
- Input datetime-local para fecha
- Botón "Crear Tarea"

##### F. medicina/pacientes.html
- Tabla con lista de pacientes
- Columnas: Nombre, Apellidos, Fecha Nacimiento, HC, Habitación
- Botón "Crear Nuevo Paciente" → `/medicina/crear-paciente`

##### G. medicina/crear-paciente.html
- Formulario con `th:object="${paciente}"`
- Campos: nombre, apellidos, fechaNacimiento, numeroHistoriaClinica, habitacion
- Validación HTML5 (required, pattern si es necesario)
- Botón "Crear Paciente"

##### H. supervisor/dashboard.html
- Cards con totales: total tareas, total usuarios, total turnos
- Tarjetas con iconos de Bootstrap Icons
- Enlaces rápidos a: Carga de Trabajo, Usuarios, Reasignar Tareas

##### I. supervisor/carga-trabajo.html
- Tabla o cards mostrando cada turno
- Por cada turno: nombre, horario, número de tareas asignadas
- Opcional: gráfico simple con barras CSS

##### J. supervisor/reasignar-tareas.html
- Tabla de tareas
- Por cada tarea: descripción, paciente, usuario actual
- Select para cambiar usuario asignado
- Botón "Reasignar" por tarea

---

#### 7. **Añadir Dependencia de Thymeleaf Security**

**Verificar en pom.xml que existe:**

```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
```

**Si NO existe, añadirla.**

Esto permite usar `sec:authorize` en las vistas.

---

#### 8. **Crear Recursos Estáticos (Opcional)**

Si se desea personalizar CSS/JS:

**Ubicación:** `src/main/resources/static/`

Crear:
- `static/css/custom.css` (estilos personalizados)
- `static/js/scripts.js` (scripts personalizados)

**Referenciar en las vistas con:**
```html
<link th:href="@{/css/custom.css}" rel="stylesheet">
<script th:src="@{/js/scripts.js}"></script>
```

---

#### 9. **Tests (NO OBLIGATORIOS para Fase 8)**

La Fase 8 es principalmente UI. Tests opcionales:

- Tests de integración con MockMvc para verificar que las vistas se cargan
- Tests de seguridad para verificar que cada rol solo accede a sus vistas

**Si se crean tests:** Máximo 5 tests básicos de navegación.

**Si NO se crean tests:** Está aceptado para esta fase, ya que el enfoque es UI.

---

#### 10. **Commits Atómicos**

1. `config: Actualizar SecurityConfig para soporte de vistas web y formLogin`
2. `feat: Crear WebController con redirección por rol`
3. `feat: Crear EnfermeriaWebController con vistas de enfermería`
4. `feat: Crear MedicinaWebController con vistas de medicina`
5. `feat: Crear SupervisorWebController con vistas de supervisor`
6. `feat: Crear vista de login y fragments comunes (header)`
7. `feat: Crear vistas de enfermería (dashboard, mis-tareas)`
8. `feat: Crear vistas de medicina (dashboard, crear-tarea, pacientes, crear-paciente)`
9. `feat: Crear vistas de supervisor (dashboard, carga-trabajo, reasignar-tareas, usuarios)`
10. `feat: Completar Fase 8 - Vistas Thymeleaf por Rol`

---

#### 11. **Verificación Manual (POST-IMPLEMENTACIÓN)**

**Arrancar aplicación:**
```bash
mvn spring-boot:run
```

**Probar flujos:**

1. **Login:**
   - Ir a http://localhost:8080/login
   - Probar usuario: `enfermera` / `enfermera123`
   - Verificar redirección a `/enfermeria/dashboard`

2. **Navegación ENFERMERIA:**
   - Ver dashboard con tareas asignadas
   - Ir a "Mis Tareas"
   - Actualizar estado de una tarea
   - Verificar que NO puede acceder a `/medicina/**` (403 Forbidden)

3. **Navegación MEDICINA:**
   - Login: `medico` / `medico123`
   - Ver dashboard
   - Crear nueva tarea (formulario completo)
   - Crear nuevo paciente
   - Verificar que la tarea aparece en enfermería

4. **Navegación SUPERVISOR:**
   - Login: `supervisor` / `supervisor123`
   - Ver dashboard general
   - Ver carga de trabajo por turno
   - Reasignar una tarea entre usuarios
   - Verificar acceso a todas las secciones

5. **Logout:**
   - Click en botón "Salir"
   - Verificar redirección a `/login?logout`

---

### ⚠️ CONSIDERACIONES ESPECIALES

**1. CSRF Token en Formularios:**

Spring Security activa CSRF por defecto. Thymeleaf lo incluye automáticamente en formularios con `th:action`.

**Ejemplo correcto:**
```html
<form th:action="@{/medicina/crear-tarea}" method="post">
    <!-- Thymeleaf añade automáticamente el CSRF token -->
</form>
```

**2. Formato de Fechas:**

Para `LocalDateTime` en formularios, usar input type `datetime-local`:
```html
<input type="datetime-local" th:field="*{fecha}" class="form-control" required>
```

**3. Enums en Selects:**

Para selects de enums, usar valores exactos:
```html
<select th:field="*{tipo}">
    <option value="MEDICACION">Medicación</option>
    <option value="CONTROL_SIGNOS_VITALES">Control Signos Vitales</option>
    <!-- etc -->
</select>
```

**4. Relaciones en Formularios:**

Para asignar relaciones (@ManyToOne), usar el ID:
```html
<select th:field="*{paciente.id}">
    <option th:each="p : ${pacientes}" th:value="${p.id}" th:text="${p.nombre}"></option>
</select>
```

**5. Fragments de Thymeleaf:**

Usar `th:replace` para incluir fragments:
```html
<!-- En header.html -->
<nav th:fragment="navbar">...</nav>

<!-- En otras vistas -->
<nav th:replace="~{fragments/header :: navbar}"></nav>
```

---

### 📋 CHECKLIST FASE 8

**Backend (Controllers):**
- [ ] SecurityConfig actualizado con formLogin
- [ ] WebController creado
- [ ] EnfermeriaWebController creado
- [ ] MedicinaWebController creado
- [ ] SupervisorWebController creado

**Frontend (Vistas):**
- [ ] login.html creado
- [ ] fragments/header.html creado
- [ ] enfermeria/dashboard.html creado
- [ ] enfermeria/mis-tareas.html creado
- [ ] medicina/dashboard.html creado
- [ ] medicina/crear-tarea.html creado
- [ ] medicina/pacientes.html creado
- [ ] medicina/crear-paciente.html creado
- [ ] supervisor/dashboard.html creado
- [ ] supervisor/carga-trabajo.html creado
- [ ] supervisor/reasignar-tareas.html creado
- [ ] supervisor/usuarios.html creado

**Verificación:**
- [ ] Login funciona con usuarios de prueba
- [ ] Redirección por rol funciona
- [ ] ENFERMERIA puede ver y actualizar sus tareas
- [ ] MEDICINA puede crear tareas y pacientes
- [ ] SUPERVISOR puede reasignar tareas
- [ ] Logout funciona correctamente
- [ ] No hay accesos no autorizados (403)

**Commits:**
- [ ] 10 commits atómicos realizados
- [ ] Push a GitHub completado

---

### 🎯 RESULTADO ESPERADO

**Al terminar Fase 8:**
- ✅ Sistema completo con interfaz web funcional
- ✅ 3 dashboards separados por rol
- ✅ Formularios funcionales para crear tareas y pacientes
- ✅ Actualización de estado de tareas desde web
- ✅ Navegación protegida por Spring Security
- ✅ Diseño responsive con Bootstrap 5

**Total del Proyecto:**
- Backend: 100% completo
- Frontend: 100% completo (web)
- Tests: 65/65 ✅
- Documentación: Actualizada

---

**PRÓXIMA FASE:** Fase 9 - Dashboard Supervisor con WebFlux y SSE (Server-Sent Events) para actualización en tiempo real.

---

**ÚLTIMA ACTUALIZACIÓN:** [Fecha actual]
**ESTADO:** Fase 8 en progreso - Vistas Thymeleaf
**PRÓXIMA ACCIÓN:** Crear controladores web y vistas Thymeleaf

Guarda el archivo y haz commit:
bashgit add CLAUDE.md
git commit -m "docs: Actualizar CLAUDE.md para Fase 8 - Vistas Thymeleaf

- Especificación completa de arquitectura de vistas
- Controladores web por rol (Enfermería, Medicina, Supervisor)
- Plantillas Thymeleaf con Bootstrap 5
- Funcionalidades detalladas por rol
- 10 commits atómicos planificados"

git push origin main




## 🎯 INSTRUCCIÓN PRINCIPAL

**AL ABRIR INTELLIJ:**

1. Lee este archivo (CLAUDE.md)
2. Identifica la fase actual en "PROGRESO ACTUAL"
3. Ejecuta el "Siguiente Paso"
4. Crea el código necesario
5. Ejecuta `mvn test` para verificar
6. Haz commits atómicos con mensajes conventional
7. Actualiza "PROGRESO ACTUAL" en este archivo
8. Continúa con la siguiente tarea

**AUTONOMÍA TOTAL:** No esperes instrucciones adicionales. Sigue el plan fase por fase.

---

## 📞 INFORMACIÓN TÉCNICA ADICIONAL

**IDE:** IntelliJ IDEA 2026.1  
**Java:** Eclipse Adoptium JDK 21.0.10.7  
**Maven:** Integrado en IntelliJ  
**Git:** SourceTree (UI) + línea de comandos

---

**ÚLTIMA ACTUALIZACIÓN:** 29 Abril 2026
**ESTADO:** Fase 8 COMPLETADA - 66/66 tests pasando | 12 vistas Thymeleaf + 4 controladores web
**PRÓXIMA ACCIÓN:** Fase 9 - Dashboard Supervisor con WebFlux y SSE (Server-Sent Events)