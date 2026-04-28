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
- ✅ Fase 6: TareaClinica con 3 relaciones @ManyToOne (18 tests)

### Siguiente Paso:
**Fase 7: Spring Security con RBAC**
1. Crear `SecurityConfig.java` con roles: ENFERMERIA, MEDICINA, SUPERVISOR
2. Endpoint protegidos según rol (ver CLAUDE.md Fase 7)
3. Login personalizado
4. Tests de seguridad (5+ tests)
5. Commits atómicos

### Total Tests Actual:
- Turno: 13/13 ✅
- Paciente: 13/13 ✅
- Usuario: 13/13 ✅
- TareaClinica: 18/18 ✅
- **Total: 58/58** ✅ (incluyendo App: 1)

---

## 🎯 SIGUIENTE PASO INMEDIATO

### FASE 6 - TAREACLINICA CON RELACIONES @ManyToOne

**ESTA ES LA ENTIDAD MÁS COMPLEJA DEL PROYECTO**

**Complejidad:**
- 3 relaciones @ManyToOne (Usuario, Paciente, Turno)
- Validaciones de existencia de relaciones
- Métodos de búsqueda con filtros
- Tests más extensos (mínimo 18 tests)

---

### ACCIÓN REQUERIDA:

#### 1. **Crear Entity TareaClinica.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/model/entity/TareaClinica.java`

**Estructura:**

```java
package com.hospital.meditrack.model.entity;

import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.model.enums.TipoTarea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarea_clinica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaClinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

**IMPORTANTE:**
- Las 3 relaciones son `nullable = false` (obligatorias)
- Los enums se mapean como STRING (no ORDINAL)
- LocalDateTime para fecha (no LocalDate)

---

#### 2. **Crear TareaClinicaRepository.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/repository/TareaClinicaRepository.java`

**Métodos custom necesarios:**

```java
package com.hospital.meditrack.repository;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaClinicaRepository extends JpaRepository<TareaClinica, Long> {
    
    // Buscar por relaciones
    List<TareaClinica> findByPacienteId(Long pacienteId);
    List<TareaClinica> findByAsignadoAId(Long usuarioId);
    List<TareaClinica> findByTurnoId(Long turnoId);
    
    // Buscar por enums
    List<TareaClinica> findByEstado(EstadoTarea estado);
    List<TareaClinica> findByPrioridad(Prioridad prioridad);
    
    // Combinaciones útiles
    List<TareaClinica> findByAsignadoAIdAndEstado(Long usuarioId, EstadoTarea estado);
    List<TareaClinica> findByTurnoIdAndEstado(Long turnoId, EstadoTarea estado);
}
```

---

#### 3. **Crear TareaClinicaService.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/service/TareaClinicaService.java`

**VALIDACIONES CRÍTICAS:**

```java
package com.hospital.meditrack.service;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.repository.TareaClinicaRepository;
import com.hospital.meditrack.repository.UsuarioRepository;
import com.hospital.meditrack.repository.PacienteRepository;
import com.hospital.meditrack.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaClinicaService {
    
    @Autowired
    private TareaClinicaRepository tareaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private TurnoRepository turnoRepository;
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerTodas() {
        return tareaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<TareaClinica> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorPaciente(Long pacienteId) {
        return tareaRepository.findByPacienteId(pacienteId);
    }
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorUsuario(Long usuarioId) {
        return tareaRepository.findByAsignadoAId(usuarioId);
    }
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorTurno(Long turnoId) {
        return tareaRepository.findByTurnoId(turnoId);
    }
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado);
    }
    
    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorPrioridad(Prioridad prioridad) {
        return tareaRepository.findByPrioridad(prioridad);
    }
    
    @Transactional
    public TareaClinica crear(TareaClinica tarea) {
        // VALIDAR que existan las relaciones
        if (tarea.getAsignadoA() == null || tarea.getAsignadoA().getId() == null) {
            throw new IllegalArgumentException("Debe asignar la tarea a un usuario");
        }
        if (tarea.getPaciente() == null || tarea.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Debe asociar la tarea a un paciente");
        }
        if (tarea.getTurno() == null || tarea.getTurno().getId() == null) {
            throw new IllegalArgumentException("Debe asociar la tarea a un turno");
        }
        
        // Verificar que existan en BD
        if (!usuarioRepository.existsById(tarea.getAsignadoA().getId())) {
            throw new IllegalArgumentException("El usuario asignado no existe");
        }
        if (!pacienteRepository.existsById(tarea.getPaciente().getId())) {
            throw new IllegalArgumentException("El paciente no existe");
        }
        if (!turnoRepository.existsById(tarea.getTurno().getId())) {
            throw new IllegalArgumentException("El turno no existe");
        }
        
        return tareaRepository.save(tarea);
    }
    
    @Transactional
    public TareaClinica actualizar(Long id, TareaClinica tareaActualizada) {
        return tareaRepository.findById(id)
            .map(tarea -> {
                tarea.setDescripcion(tareaActualizada.getDescripcion());
                tarea.setTipo(tareaActualizada.getTipo());
                tarea.setPrioridad(tareaActualizada.getPrioridad());
                tarea.setEstado(tareaActualizada.getEstado());
                tarea.setFecha(tareaActualizada.getFecha());
                tarea.setObservaciones(tareaActualizada.getObservaciones());
                
                // Actualizar relaciones si se proporcionan
                if (tareaActualizada.getAsignadoA() != null) {
                    if (!usuarioRepository.existsById(tareaActualizada.getAsignadoA().getId())) {
                        throw new IllegalArgumentException("El usuario asignado no existe");
                    }
                    tarea.setAsignadoA(tareaActualizada.getAsignadoA());
                }
                if (tareaActualizada.getPaciente() != null) {
                    if (!pacienteRepository.existsById(tareaActualizada.getPaciente().getId())) {
                        throw new IllegalArgumentException("El paciente no existe");
                    }
                    tarea.setPaciente(tareaActualizada.getPaciente());
                }
                if (tareaActualizada.getTurno() != null) {
                    if (!turnoRepository.existsById(tareaActualizada.getTurno().getId())) {
                        throw new IllegalArgumentException("El turno no existe");
                    }
                    tarea.setTurno(tareaActualizada.getTurno());
                }
                
                return tareaRepository.save(tarea);
            })
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la tarea con ID: " + id));
    }
    
    @Transactional
    public void eliminar(Long id) {
        tareaRepository.deleteById(id);
    }
}
```

**NOTAS IMPORTANTES:**
- Validar existencia de Usuario, Paciente y Turno ANTES de guardar
- Usar `existsById()` para verificar
- Mensajes de error descriptivos

---

#### 4. **Crear TareaClinicaController.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/controller/TareaClinicaController.java`

**Endpoints necesarios:**

```java
package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.service.TareaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaClinicaController {
    
    @Autowired
    private TareaClinicaService tareaService;
    
    // GET /api/tareas
    @GetMapping
    public ResponseEntity<List<TareaClinica>> obtenerTodas() {
        List<TareaClinica> tareas = tareaService.obtenerTodas();
        return ResponseEntity.ok(tareas);
    }
    
    // GET /api/tareas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TareaClinica> obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /api/tareas/paciente/{pacienteId}
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        List<TareaClinica> tareas = tareaService.obtenerPorPaciente(pacienteId);
        return ResponseEntity.ok(tareas);
    }
    
    // GET /api/tareas/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<TareaClinica> tareas = tareaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(tareas);
    }
    
    // GET /api/tareas/turno/{turnoId}
    @GetMapping("/turno/{turnoId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorTurno(@PathVariable Long turnoId) {
        List<TareaClinica> tareas = tareaService.obtenerPorTurno(turnoId);
        return ResponseEntity.ok(tareas);
    }
    
    // GET /api/tareas/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaClinica>> obtenerPorEstado(@PathVariable EstadoTarea estado) {
        List<TareaClinica> tareas = tareaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(tareas);
    }
    
    // GET /api/tareas/prioridad/{prioridad}
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<TareaClinica>> obtenerPorPrioridad(@PathVariable Prioridad prioridad) {
        List<TareaClinica> tareas = tareaService.obtenerPorPrioridad(prioridad);
        return ResponseEntity.ok(tareas);
    }
    
    // POST /api/tareas
    @PostMapping
    public ResponseEntity<TareaClinica> crear(@RequestBody TareaClinica tarea) {
        try {
            TareaClinica tareaCreada = tareaService.crear(tarea);
            return ResponseEntity.status(HttpStatus.CREATED).body(tareaCreada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /api/tareas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TareaClinica> actualizar(
            @PathVariable Long id, 
            @RequestBody TareaClinica tarea) {
        try {
            TareaClinica tareaActualizada = tareaService.actualizar(id, tarea);
            return ResponseEntity.ok(tareaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/tareas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

#### 5. **Crear Tests (18 tests total)**

**Tests más complejos por las relaciones:**

##### A. TareaClinicaRepositoryTest.java (3 tests)

```java
package com.hospital.meditrack;

import com.hospital.meditrack.model.entity.*;
import com.hospital.meditrack.model.enums.*;
import com.hospital.meditrack.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class TareaClinicaRepositoryTest {
    
    @Autowired
    private TareaClinicaRepository tareaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private TurnoRepository turnoRepository;
    
    @Test
    void deberiaGuardarYRecuperarTareaConRelaciones() {
        // Arrange - Crear datos relacionados
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario();
        Paciente paciente = crearPaciente();
        
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Administrar medicación");
        tarea.setTipo(TipoTarea.MEDICACION);
        tarea.setPrioridad(Prioridad.ALTA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        tarea.setAsignadoA(usuario);
        tarea.setPaciente(paciente);
        tarea.setTurno(turno);
        
        // Act
        TareaClinica tareaGuardada = tareaRepository.save(tarea);
        
        // Assert
        assertNotNull(tareaGuardada.getId());
        assertEquals("Administrar medicación", tareaGuardada.getDescripcion());
        assertEquals(usuario.getId(), tareaGuardada.getAsignadoA().getId());
        assertEquals(paciente.getId(), tareaGuardada.getPaciente().getId());
        assertEquals(turno.getId(), tareaGuardada.getTurno().getId());
    }
    
    @Test
    void deberiaBuscarTareasPorPaciente() {
        // Arrange
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario();
        Paciente paciente = crearPaciente();
        
        TareaClinica tarea1 = crearTarea(usuario, paciente, turno, "Tarea 1");
        TareaClinica tarea2 = crearTarea(usuario, paciente, turno, "Tarea 2");
        
        // Act
        List<TareaClinica> tareas = tareaRepository.findByPacienteId(paciente.getId());
        
        // Assert
        assertEquals(2, tareas.size());
    }
    
    @Test
    void deberiaBuscarTareasPorEstado() {
        // Arrange
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario();
        Paciente paciente = crearPaciente();
        
        TareaClinica tarea = crearTarea(usuario, paciente, turno, "Tarea pendiente");
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tareaRepository.save(tarea);
        
        // Act
        List<TareaClinica> tareas = tareaRepository.findByEstado(EstadoTarea.PENDIENTE);
        
        // Assert
        assertTrue(tareas.size() >= 1);
        assertTrue(tareas.stream().anyMatch(t -> t.getDescripcion().equals("Tarea pendiente")));
    }
    
    // Métodos helper
    private Turno crearTurno() {
        Turno turno = new Turno();
        turno.setNombre("Mañana-Test-" + System.currentTimeMillis());
        turno.setHoraInicio(LocalTime.of(7, 0));
        turno.setHoraFin(LocalTime.of(15, 0));
        return turnoRepository.save(turno);
    }
    
    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellidos("Usuario");
        usuario.setUsername("test-" + System.currentTimeMillis());
        usuario.setPassword("password");
        usuario.setRol(Rol.ENFERMERIA);
        return usuarioRepository.save(usuario);
    }
    
    private Paciente crearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellidos("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setNumeroHistoriaClinica("HC-" + System.currentTimeMillis());
        paciente.setHabitacion("101");
        return pacienteRepository.save(paciente);
    }
    
    private TareaClinica crearTarea(Usuario usuario, Paciente paciente, Turno turno, String descripcion) {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion(descripcion);
        tarea.setTipo(TipoTarea.MEDICACION);
        tarea.setPrioridad(Prioridad.MEDIA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        tarea.setAsignadoA(usuario);
        tarea.setPaciente(paciente);
        tarea.setTurno(turno);
        return tareaRepository.save(tarea);
    }
}
```

##### B. TareaClinicaServiceTest.java (7 tests con Mockito)

Incluir tests:
1. `deberiaObtenerTodas`
2. `deberiaObtenerPorId`
3. `deberiaCrearTareaConRelacionesValidas`
4. `noDeberiaCrearTareaSinUsuario`
5. `noDeberiaCrearTareaSinPaciente`
6. `noDeberiaCrearTareaSinTurno`
7. `deberiaActualizarTarea`

##### C. TareaClinicaControllerTest.java (8 tests integración)

Incluir tests:
1. `deberiaObtenerTodasLasTareas`
2. `deberiaObtenerTareaPorId`
3. `deberiaObtenerTareasPorPaciente`
4. `deberiaObtenerTareasPorUsuario`
5. `deberiaCrearNuevaTarea`
6. `noDeberiaCrearTareaSinRelaciones`
7. `deberiaActualizarTarea`
8. `deberiaEliminarTarea`

---

#### 6. **Commits Atómicos**

1. `feat: Crear entidad TareaClinica con relaciones @ManyToOne`
2. `feat: Crear TareaClinicaRepository con métodos de búsqueda`
3. `feat: Crear TareaClinicaService con validaciones de relaciones`
4. `feat: Crear TareaClinicaController con endpoints de filtros`
5. `test: Añadir tests de TareaClinicaRepository (3 tests)`
6. `test: Añadir tests de TareaClinicaService (7 tests)`
7. `test: Añadir tests de TareaClinicaController (8 tests)`
8. `feat: Completar Fase 6 - TareaClinica con relaciones`

---

#### 7. **Ejecutar al Terminar**

```bash
mvn test
```

**Resultado esperado:**
- Tests TareaClinica: 18/18 ✅
- **TOTAL PROYECTO: 57/57** ✅
   - Turno: 13
   - Paciente: 13
   - Usuario: 13
   - TareaClinica: 18

---

### ⚠️ CONSIDERACIONES ESPECIALES

**1. Referencias Circulares en JSON:**

Las relaciones bidireccionales pueden causar loops infinitos al serializar JSON.

**Solución:** Usar `@JsonIgnoreProperties` en las relaciones:

```java
@ManyToOne
@JoinColumn(name = "usuario_id")
@JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
private Usuario asignadoA;
```

**2. Lazy Loading en Tests:**

Si hay errores de lazy loading:
```java
@ManyToOne(fetch = FetchType.EAGER)  // Solo si es necesario
```

**3. Validaciones en Service:**

SIEMPRE validar que las relaciones existan antes de guardar:
```java
if (!usuarioRepository.existsById(tarea.getAsignadoA().getId())) {
    throw new IllegalArgumentException("El usuario no existe");
}
```

---

### 📋 CHECKLIST FASE 6

Antes de ejecutar:
- [ ] CLAUDE.md actualizado
- [ ] Commit de CLAUDE.md realizado

Después de ejecutar:
- [ ] 18 tests de TareaClinica pasando
- [ ] Total proyecto: 57/57 tests
- [ ] 8 commits atómicos en GitHub
- [ ] API funcional en `/api/tareas`

---

**¿LISTO PARA EJECUTAR?**

Una vez actualizado `CLAUDE.md`, haz:

```bash
git add CLAUDE.md
git commit -m "docs: Actualizar CLAUDE.md para Fase 6 - TareaClinica con relaciones

- Definir entidad con 3 relaciones @ManyToOne
- Especificar validaciones de existencia de relaciones
- Detallar endpoints con filtros múltiples
- Definir 18 tests (Repository 3, Service 7, Controller 8)
- Total esperado: 57/57 tests"

git push origin main
```

Luego en **Claude Code**:

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
**ESTADO:** Fase 6 COMPLETADA - 58/58 tests pasando (Turno: 13 + Paciente: 13 + Usuario: 13 + TareaClinica: 18 + App: 1)
**PRÓXIMA ACCIÓN:** Fase 7 - Spring Security con RBAC (ENFERMERIA, MEDICINA, SUPERVISOR)