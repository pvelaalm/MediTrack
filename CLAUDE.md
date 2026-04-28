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
- ✅ Fase 4 parcial: Paciente (código completo, tests pendientes)

### Siguiente Paso:
**Crear tests de Paciente (13 tests)**

### Total Tests Esperados al Final:
- Turno: 13
- Paciente: 13
- Usuario: 13
- TareaClinica: 15+
- Security: 5+
- **Total: 60+ tests**

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

**ÚLTIMA ACTUALIZACIÓN:** 28 Abril 2026
**ESTADO:** Fase 4 en progreso - Tests de Paciente pendientes
**PRÓXIMA ACCIÓN:** Crear PacienteRepositoryTest.java