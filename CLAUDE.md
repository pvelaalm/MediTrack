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

## 📊 PROGRESO ACTUAL

### Completado:
- ✅ Fase 1-3: Enumeraciones + Turno completo (13 tests)
- ✅ Fase 4: Paciente completo (13 tests)
- ✅ Fase 5: Usuario con BCrypt (13 tests)
- ✅ Fase 6: TareaClinica con relaciones (18 tests)
- ✅ Fase 7: Spring Security con RBAC (8 tests)
- ✅ Fase 8: Vistas Thymeleaf por Rol (interfaz web completa)

### Completado (FASE FINAL):
- ✅ Fase 9: Dashboard Supervisor en Tiempo Real con WebFlux y SSE (COMPLETO)

### Total Tests Actual:
- Turno: 13/13 ✅
- Paciente: 13/13 ✅
- Usuario: 13/13 ✅
- TareaClinica: 18/18 ✅
- Security: 8/8 ✅
- Dashboard: 3/3 ✅
- **Total: 69/69** ✅

### 🎉 PROYECTO MEDITRACK 100% COMPLETADO

---

## 🎯 SIGUIENTE PASO INMEDIATO

### FASE 9 - DASHBOARD SUPERVISOR EN TIEMPO REAL CON WEBFLUX Y SSE

**🎉 ESTA ES LA FASE FINAL DEL PROYECTO 🎉**

**Objetivo:** Crear un dashboard dinámico para el supervisor que muestre estadísticas y carga de trabajo actualizándose automáticamente en tiempo real usando Server-Sent Events (SSE) con Spring WebFlux.

---

### ¿QUÉ ES SSE (Server-Sent Events)?

SSE permite al servidor enviar actualizaciones automáticas al navegador sin que el cliente tenga que hacer peticiones constantes (polling).

**Características:**
- Unidireccional: servidor → cliente
- Sobre HTTP estándar
- Reconexión automática
- Más simple que WebSockets
- Perfecto para dashboards y notificaciones

**Flujo:**
1. Navegador abre conexión HTTP al endpoint SSE
2. Servidor mantiene la conexión abierta
3. Servidor envía eventos periódicamente (cada 5 segundos)
4. Navegador recibe eventos y actualiza la vista con JavaScript

---

### DATOS A MOSTRAR EN TIEMPO REAL

El dashboard debe mostrar y actualizar cada **5 segundos**:

#### 1. Estadísticas Generales
- Total de tareas en el sistema
- Tareas pendientes
- Tareas en curso
- Tareas realizadas hoy
- Tareas canceladas
- Total de pacientes activos
- Total de usuarios (enfermería)

#### 2. Carga de Trabajo por Turno
- Mañana: X tareas asignadas
- Tarde: Y tareas asignadas
- Noche: Z tareas asignadas

#### 3. Distribución por Estado
- Pendientes: X
- En Curso: Y
- Realizadas: Z
- Canceladas: W

#### 4. Distribución por Prioridad
- Urgentes: X
- Altas: Y
- Medias: Z
- Bajas: W

#### 5. Top 5 Enfermeros
- Ranking de enfermeros con más tareas asignadas
- Mostrar nombre completo y número de tareas

#### 6. Alertas (Opcional)
- Tareas urgentes pendientes
- Turnos con sobrecarga (más de 10 tareas)

---

### ARQUITECTURA DE LA SOLUCIÓN
FRONTEND (JavaScript EventSource)
↓
Conecta a /api/supervisor/dashboard/stream
↓
BACKEND (SupervisorRealtimeController)
↓
Flux.interval(5 seconds) con WebFlux
↓
SERVICIO (DashboardService)
↓
Calcula estadísticas en tiempo real
↓
Devuelve DashboardData (DTO)
↓
FRONTEND actualiza gráficos y tarjetas

---

### ACCIÓN REQUERIDA

#### 1. **Crear DTOs para Dashboard**

**Ubicación:** `src/main/java/com/hospital/meditrack/model/dto/`

Crear los siguientes DTOs:

##### A. DashboardStats.java
- Campos: totalTareas, tareasPendientes, tareasEnCurso, tareasRealizadas, tareasCanceladas, totalPacientes, totalEnfermeros
- Usar Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor

##### B. UsuarioConTareas.java
- Campos: usuarioId (Long), nombreCompleto (String), numeroTareas (Long)
- Usar Lombok

##### C. DashboardData.java (DTO principal que envuelve todo)
- Campo: timestamp (LocalDateTime)
- Campo: stats (DashboardStats)
- Campo: cargaPorTurno (Map<String, Long>)
- Campo: tareasPorPrioridad (Map<Prioridad, Long>)
- Campo: topEnfermeros (List<UsuarioConTareas>)
- Usar Lombok

**IMPORTANTE:** Este es el objeto JSON que se enviará cada 5 segundos via SSE.

---

#### 2. **Crear DashboardService.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/service/DashboardService.java`

**Responsabilidad:** Calcular todas las estadísticas del dashboard en tiempo real.

**Métodos necesarios:**

1. `obtenerEstadisticasGenerales()` → `DashboardStats`
   - Obtener todas las tareas del sistema
   - Contar tareas por estado (PENDIENTE, EN_CURSO, REALIZADA, CANCELADA)
   - Contar total pacientes
   - Contar usuarios con rol ENFERMERIA
   - Devolver DashboardStats completo

2. `obtenerCargaPorTurno()` → `Map<String, Long>`
   - Obtener todos los turnos
   - Por cada turno, contar cuántas tareas tiene asignadas
   - Devolver Map con: clave=nombreTurno, valor=numeroTareas

3. `obtenerTareasPorPrioridad()` → `Map<Prioridad, Long>`
   - Obtener todas las tareas
   - Agrupar y contar por prioridad (URGENTE, ALTA, MEDIA, BAJA)
   - Devolver Map

4. `obtenerTop5EnfermerosConMasTareas()` → `List<UsuarioConTareas>`
   - Obtener todos los usuarios con rol ENFERMERIA
   - Por cada enfermero, contar cuántas tareas tiene asignadas
   - Crear lista de UsuarioConTareas (id, nombreCompleto, numeroTareas)
   - Ordenar descendente por numeroTareas
   - Devolver top 5

**Inyectar:** TareaClinicaService, PacienteService, UsuarioService, TurnoService

**NOTA:** Todo debe calcularse en tiempo real sin caché.

---

#### 3. **Crear SupervisorRealtimeController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/SupervisorRealtimeController.java`

**Responsabilidad:** Endpoint REST con SSE para streaming de datos.

**Endpoint:**

- **URL:** `/api/supervisor/dashboard/stream`
- **Método:** GET
- **Produces:** `MediaType.TEXT_EVENT_STREAM_VALUE`
- **Return:** `Flux<ServerSentEvent<DashboardData>>`

**Implementación:**

Usar `Flux.interval(Duration.ofSeconds(5))` para emitir cada 5 segundos.

Por cada emisión:
1. Crear objeto DashboardData vacío
2. Asignar timestamp actual: `LocalDateTime.now()`
3. Obtener stats: `dashboardService.obtenerEstadisticasGenerales()`
4. Obtener cargaPorTurno: `dashboardService.obtenerCargaPorTurno()`
5. Obtener tareasPorPrioridad: `dashboardService.obtenerTareasPorPrioridad()`
6. Obtener topEnfermeros: `dashboardService.obtenerTop5EnfermerosConMasTareas()`
7. Crear `ServerSentEvent` con:
   - id: número de secuencia
   - event: "dashboard-update"
   - data: objeto DashboardData completo
8. Devolver el evento

**IMPORTANTE:**
- Usar @RestController y @RequestMapping("/api/supervisor")
- Inyectar DashboardService
- El Flux emitirá infinitamente cada 5 segundos hasta que el navegador cierre la conexión

---

#### 4. **Actualizar SecurityConfig.java**

Añadir permiso para el endpoint SSE en las reglas de autorización:
.requestMatchers("/api/supervisor/dashboard/stream").hasRole("SUPERVISOR")

**Ubicación:** Dentro del método `filterChain()`, en la sección de autorizaciones de API, después de las reglas de `/api/tareas/**` y antes de `.anyRequest().authenticated()`.

---

#### 5. **Crear Vista dashboard-realtime.html**

**Ubicación:** `src/main/resources/templates/supervisor/dashboard-realtime.html`

**Requisitos de Diseño:**

##### A. Estructura HTML
- Usar `th:replace` para incluir navbar de fragments/header.html
- Título: "Dashboard en Tiempo Real - Supervisor"
- Mostrar badge de estado de conexión (verde=conectado, rojo=desconectado)
- Mostrar última hora de actualización

##### B. Sección de Estadísticas Generales
Crear 7 tarjetas (cards) Bootstrap con los siguientes datos:
- Total Tareas (color: primary/azul)
- Tareas Pendientes (color: warning/amarillo)
- Tareas En Curso (color: info/cyan)
- Tareas Realizadas (color: success/verde)
- Tareas Canceladas (color: danger/rojo)
- Total Pacientes (color: secondary/gris)
- Total Enfermeros (color: dark/negro)

Cada card debe tener un ID único para actualizar su valor con JavaScript.

##### C. Sección de Gráficos
Usar **Chart.js** (desde CDN: https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js)

**Gráfico 1: Carga por Turno (Barras)**
- Canvas con id: `chart-turnos`
- Tipo: bar (barras verticales)
- Labels: ["Mañana", "Tarde", "Noche"]
- Colores diferentes por barra

**Gráfico 2: Tareas por Prioridad (Donut)**
- Canvas con id: `chart-prioridades`
- Tipo: doughnut
- Labels: ["Urgente", "Alta", "Media", "Baja"]
- Colores: rojo, naranja, amarillo, gris

##### D. Sección de Top 5 Enfermeros
- Tabla Bootstrap con columnas: #, Nombre, Tareas Asignadas
- tbody con id: `top-enfermeros`
- Se actualizará dinámicamente con JavaScript

##### E. JavaScript para SSE

**Funcionalidades requeridas:**

1. **Inicializar gráficos Chart.js:**
   - Crear gráfico de barras para turnos (datos iniciales en 0)
   - Crear gráfico de donut para prioridades (datos iniciales en 0)

2. **Conectar a SSE:**
   - Crear EventSource conectado a `/api/supervisor/dashboard/stream`
   - Escuchar evento `dashboard-update`

3. **Al recibir evento:**
   - Parsear JSON del evento: `const data = JSON.parse(event.data)`
   - Actualizar timestamp de última actualización
   - Actualizar todas las tarjetas de estadísticas (con animación opcional)
   - Actualizar gráfico de turnos con nuevos datos
   - Actualizar gráfico de prioridades con nuevos datos
   - Actualizar tabla de top enfermeros

4. **Funciones helper:**
   - `updateStatCard(elementId, newValue)`: actualiza el valor de una tarjeta
   - `updateTopEnfermeros(enfermeros)`: actualiza la tabla con el ranking
   - Opcional: añadir animación a las tarjetas cuando se actualizan

5. **Manejo de conexión:**
   - Al conectar: badge verde "Conectado"
   - Al desconectar: badge rojo "Desconectado"
   - EventSource se reconecta automáticamente

**Estructura del evento SSE recibido:**
```json
{
  "timestamp": "2026-04-29T10:30:00",
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
    {"usuarioId": 2, "nombreCompleto": "Carlos Ruiz Pérez", "numeroTareas": 7},
    ...
  ]
}
```

---

#### 6. **Añadir Enlace en Navbar de Supervisor**

**Actualizar:** `src/main/resources/templates/fragments/header.html`

En la sección del menú SUPERVISOR, añadir un nuevo enlace:

```html
<li class="nav-item" sec:authorize="hasRole('SUPERVISOR')">
    <a class="nav-link" th:href="@{/supervisor/dashboard-realtime}">
        📊 Dashboard Tiempo Real
    </a>
</li>
```

---

#### 7. **Crear Controlador Web para la Vista**

**Actualizar:** `src/main/java/com/hospital/meditrack/controller/SupervisorWebController.java`

Añadir método:

```java
@GetMapping("/dashboard-realtime")
public String dashboardRealtime() {
    return "supervisor/dashboard-realtime";
}
```

---

#### 8. **Tests (Opcionales - 3 tests básicos)**

Crear tests básicos para verificar:

1. **Test de DashboardService:**
   - Verificar que `obtenerEstadisticasGenerales()` devuelve DashboardStats válido
   - Usar Mockito para mockear los servicios

2. **Test de SupervisorRealtimeController:**
   - Verificar que el endpoint `/api/supervisor/dashboard/stream` devuelve Flux
   - Verificar que emite eventos cada 5 segundos (usar StepVerifier de Reactor)

3. **Test de Seguridad:**
   - Verificar que solo SUPERVISOR puede acceder al endpoint SSE
   - Verificar que ENFERMERIA y MEDICINA reciben 403

**NOTA:** Los tests para SSE son opcionales. Si se crean, usar `reactor-test` y `StepVerifier`.

---

#### 9. **Commits Atómicos**

1. `feat: Crear DTOs para dashboard (DashboardStats, UsuarioConTareas, DashboardData)`
2. `feat: Crear DashboardService con cálculo de estadísticas en tiempo real`
3. `feat: Crear SupervisorRealtimeController con endpoint SSE`
4. `config: Actualizar SecurityConfig para endpoint SSE`
5. `feat: Crear vista dashboard-realtime.html con Chart.js y SSE`
6. `feat: Actualizar navbar con enlace a Dashboard Tiempo Real`
7. `test: Añadir tests básicos para DashboardService y SSE (opcional)`
8. `docs: Actualizar README con funcionalidades de Fase 9`
9. `feat: Completar Fase 9 - Dashboard Supervisor en Tiempo Real con SSE`

---

### ⚠️ CONSIDERACIONES ESPECIALES

#### 1. WebFlux vs Spring MVC
Este proyecto usa Spring MVC (bloqueante), pero WebFlux solo se usa para el endpoint SSE.

**IMPORTANTE:**
- Solo el endpoint `/api/supervisor/dashboard/stream` usa WebFlux (Flux)
- El resto del proyecto sigue con Spring MVC (no cambiar)
- Spring Boot puede mezclar ambos sin problemas

#### 2. Dependencias
Verificar que `spring-boot-starter-webflux` está en `pom.xml` (ya incluido desde Fase 1).

#### 3. Rendimiento
Cada conexión SSE mantiene una thread abierta. Con pocos usuarios supervisor (1-5) no hay problema.

Para producción con muchos supervisores, considerar:
- Aumentar pool de threads
- Usar caché Redis para las estadísticas
- Reducir frecuencia de actualización (cada 10-15 segundos)

#### 4. Reconexión Automática
EventSource del navegador reconecta automáticamente si se pierde la conexión.

#### 5. CORS (si fuera necesario)
Si el frontend estuviera en otro dominio, configurar CORS en SecurityConfig.

Para este proyecto (frontend y backend juntos), NO es necesario.

---

### 📋 CHECKLIST FASE 9

**Backend:**
- [ ] DashboardStats.java creado
- [ ] UsuarioConTareas.java creado
- [ ] DashboardData.java creado
- [ ] DashboardService.java creado con 4 métodos
- [ ] SupervisorRealtimeController.java creado con endpoint SSE
- [ ] SecurityConfig.java actualizado

**Frontend:**
- [ ] dashboard-realtime.html creado
- [ ] Chart.js integrado
- [ ] JavaScript SSE implementado
- [ ] Gráficos de barras y donut funcionando
- [ ] Tabla top enfermeros actualizable
- [ ] Navbar actualizado con enlace

**Testing:**
- [ ] SupervisorWebController actualizado
- [ ] Tests básicos (opcional)

**Verificación:**
- [ ] Arrancar app: `mvn spring-boot:run`
- [ ] Login como supervisor
- [ ] Ir a "Dashboard Tiempo Real"
- [ ] Verificar que las estadísticas se actualizan cada 5 segundos
- [ ] Verificar que los gráficos se actualizan
- [ ] Verificar que la tabla de enfermeros se actualiza
- [ ] Crear una tarea desde otra ventana y ver actualización en tiempo real

**Commits:**
- [ ] 9 commits atómicos realizados
- [ ] Push a GitHub completado

---

### 🎯 RESULTADO ESPERADO AL COMPLETAR FASE 9

**Sistema MediTrack 100% COMPLETADO:**

✅ Backend REST API completo (CRUD todas las entidades)
✅ Seguridad con Spring Security (RBAC por roles)
✅ Frontend web con Thymeleaf (vistas por rol)
✅ Dashboard en tiempo real con SSE y WebFlux
✅ Autenticación y autorización funcional
✅ 65+ tests pasando
✅ Interfaz responsive con Bootstrap 5
✅ Gráficos interactivos con Chart.js
✅ Actualización automática cada 5 segundos

**Funcionalidades por Rol:**

**ENFERMERIA:**
- Ver sus tareas asignadas
- Actualizar estado de tareas
- Ver información de pacientes

**MEDICINA:**
- Crear tareas clínicas
- Asignar tareas a enfermería
- Crear pacientes
- Ver todas las tareas

**SUPERVISOR:**
- Dashboard estático (Fase 8)
- Dashboard en tiempo real (Fase 9)
- Reasignar tareas
- Gestionar usuarios
- Ver carga de trabajo en vivo

---

### 📚 DOCUMENTACIÓN FINAL

Al terminar Fase 9, actualizar README.md del proyecto con:

1. **Descripción del proyecto**
2. **Tecnologías utilizadas**
3. **Instalación y configuración**
4. **Usuarios de prueba:**
   - enfermera / enfermera123
   - medico / medico123
   - supervisor / supervisor123
5. **Endpoints API REST**
6. **Capturas de pantalla (opcional)**
7. **Arquitectura del sistema**
8. **Próximos pasos (mejoras futuras)**

---

### 🎓 APRENDIZAJES DE LA FASE 9

**Conceptos nuevos aplicados:**
- Server-Sent Events (SSE)
- Spring WebFlux con Flux
- Streaming de datos en tiempo real
- Chart.js para visualización
- EventSource JavaScript API
- Reconexión automática
- Cálculo de estadísticas agregadas
- DTOs para transferencia de datos complejos

---

### 🚀 MEJORAS FUTURAS (POST-TFG)

Ideas para extender el proyecto después de la defensa:

1. **Notificaciones Push:** Alertas cuando hay tareas urgentes
2. **Exportar datos:** PDF/Excel con reportes
3. **Historial de reasignaciones:** Auditoría de cambios
4. **Chat entre usuarios:** Comunicación interna
5. **App móvil:** React Native o Flutter
6. **Análisis predictivo:** ML para predecir carga de trabajo
7. **Integración con sistemas hospitalarios:** HL7, FHIR
8. **Multi-tenant:** Múltiples hospitales en una instalación

---

**ÚLTIMA ACTUALIZACIÓN:** [Fecha actual]
**ESTADO:** Fase 9 en progreso - Dashboard en tiempo real con SSE
**PRÓXIMA ACCIÓN:** Implementar SSE con WebFlux y Chart.js

Guarda y Haz Commit:
bashgit add CLAUDE.md
git commit -m "docs: Actualizar CLAUDE.md para Fase 9 - Dashboard Tiempo Real con SSE

FASE FINAL DEL PROYECTO

- Especificación completa de SSE con WebFlux
- DashboardService con estadísticas en tiempo real
- SupervisorRealtimeController con Flux streaming
- Vista con Chart.js y EventSource JavaScript
- Actualización cada 5 segundos
- 9 commits atómicos planificados

Esta es la última fase del desarrollo del TFG MediTrack."

git push origin main



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