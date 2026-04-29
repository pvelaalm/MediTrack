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

### En Progreso:
- 🔄 Fase 8: Vistas Thymeleaf

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

### FASE 7 - SPRING SECURITY CON RBAC (Role-Based Access Control)

**Objetivo:** Configurar autenticación y autorización por roles para proteger endpoints según el rol del usuario.

---

### ROLES Y PERMISOS

#### Rol: ENFERMERIA
- ✅ Ver tareas asignadas a ellos (GET /api/tareas/usuario/{id})
- ✅ Actualizar estado de sus tareas (PUT /api/tareas/{id})
- ✅ Ver información de pacientes (GET /api/pacientes)
- ❌ NO puede crear tareas
- ❌ NO puede asignar tareas a otros
- ❌ NO puede eliminar tareas

#### Rol: MEDICINA
- ✅ Crear tareas clínicas (POST /api/tareas)
- ✅ Asignar tareas a enfermería (POST /api/tareas con usuario asignado)
- ✅ Ver todas las tareas (GET /api/tareas)
- ✅ Ver pacientes (GET /api/pacientes)
- ✅ Crear pacientes (POST /api/pacientes)
- ❌ NO puede eliminar usuarios
- ❌ NO puede reasignar tareas ya asignadas

#### Rol: SUPERVISOR
- ✅ TODO: acceso completo a todos los endpoints
- ✅ Ver carga de trabajo (GET /api/tareas/turno/{id})
- ✅ Reasignar tareas (PUT /api/tareas/{id})
- ✅ Gestionar usuarios (CRUD /api/usuarios)
- ✅ Eliminar tareas si es necesario

---

### ACCIÓN REQUERIDA:

#### 1. **Crear SecurityConfig.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/config/SecurityConfig.java`

**Estructura:**

```java
package com.hospital.meditrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactivar CSRF para API REST
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (ninguno por ahora)
                
                // TURNOS - Todos los roles pueden ver
                .requestMatchers(HttpMethod.GET, "/api/turnos/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/turnos/**").hasRole("SUPERVISOR")
                
                // PACIENTES
                .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("SUPERVISOR")
                
                // USUARIOS - Solo SUPERVISOR puede gestionar
                .requestMatchers("/api/usuarios/**").hasRole("SUPERVISOR")
                
                // TAREAS
                .requestMatchers(HttpMethod.GET, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/tareas/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/tareas/**").hasRole("SUPERVISOR")
                
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());  // Autenticación HTTP Basic para API
        
        return http.build();
    }
}
```

**NOTAS IMPORTANTES:**
- CSRF desactivado (API REST stateless)
- HTTP Basic para autenticación (username:password en headers)
- Roles SIN prefijo "ROLE_" en hasRole() (Spring lo añade automáticamente)
- `.hasAnyRole()` para múltiples roles
- `.hasRole()` para un solo rol

---

#### 2. **Crear CustomUserDetailsService.java**

**Ubicación:** `src/main/java/com/hospital/meditrack/config/CustomUserDetailsService.java`

**Implementación:**

```java
package com.hospital.meditrack.config;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Servicio personalizado para cargar usuarios desde la base de datos.
 * Spring Security lo usa para autenticación.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        // Convertir el rol del enum a GrantedAuthority
        // Spring Security añade automáticamente el prefijo "ROLE_"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());
        
        return new User(
            usuario.getUsername(),
            usuario.getPassword(),  // Ya está encriptado con BCrypt
            Collections.singletonList(authority)
        );
    }
}
```

**IMPORTANTE:**
- El password ya está encriptado (BCrypt desde Fase 5)
- El prefijo "ROLE_" se añade aquí manualmente
- Devuelve un objeto `UserDetails` de Spring Security

---

#### 3. **Actualizar PasswordEncoderConfig.java**

**Ya existe desde Fase 5, NO modificar.**

Verificar que existe:
```java
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

#### 4. **Crear DataLoader.java (Datos de Prueba)**

**Ubicación:** `src/main/java/com/hospital/meditrack/config/DataLoader.java`

**Para crear usuarios de prueba al arrancar la app:**

```java
package com.hospital.meditrack.config;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Carga usuarios de prueba al arrancar la aplicación.
 * SOLO para desarrollo/pruebas.
 */
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Solo crear si no existen usuarios
        if (usuarioRepository.count() == 0) {
            
            // Usuario ENFERMERIA
            Usuario enfermera = new Usuario();
            enfermera.setNombre("Ana");
            enfermera.setApellidos("García López");
            enfermera.setUsername("enfermera");
            enfermera.setPassword(passwordEncoder.encode("enfermera123"));
            enfermera.setRol(Rol.ENFERMERIA);
            usuarioRepository.save(enfermera);
            
            // Usuario MEDICINA
            Usuario medico = new Usuario();
            medico.setNombre("Carlos");
            medico.setApellidos("Martínez Ruiz");
            medico.setUsername("medico");
            medico.setPassword(passwordEncoder.encode("medico123"));
            medico.setRol(Rol.MEDICINA);
            usuarioRepository.save(medico);
            
            // Usuario SUPERVISOR
            Usuario supervisor = new Usuario();
            supervisor.setNombre("Laura");
            supervisor.setApellidos("Fernández Sánchez");
            supervisor.setUsername("supervisor");
            supervisor.setPassword(passwordEncoder.encode("supervisor123"));
            supervisor.setRol(Rol.SUPERVISOR);
            usuarioRepository.save(supervisor);
            
            System.out.println("✅ Usuarios de prueba creados:");
            System.out.println("   - enfermera / enfermera123 (ENFERMERIA)");
            System.out.println("   - medico / medico123 (MEDICINA)");
            System.out.println("   - supervisor / supervisor123 (SUPERVISOR)");
        }
    }
}
```

**NOTAS:**
- Solo crea usuarios si la tabla está vacía
- Passwords encriptados con BCrypt
- Imprime credenciales en consola para pruebas

---

#### 5. **Crear Tests de Seguridad (8 tests)**

##### A. SecurityConfigTest.java (8 tests)

**Ubicación:** `src/test/java/com/hospital/meditrack/SecurityConfigTest.java`

```java
package com.hospital.meditrack;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityConfigTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @BeforeEach
    void setUp() {
        // Crear usuario de prueba si no existe
        if (usuarioRepository.findByUsername("testuser").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Test");
            usuario.setApellidos("User");
            usuario.setUsername("testuser");
            usuario.setPassword(passwordEncoder.encode("password"));
            usuario.setRol(Rol.SUPERVISOR);
            usuarioRepository.save(usuario);
        }
    }
    
    @Test
    void deberiaDenegarAccesoSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/turnos"))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaDeberiaVerTurnos() throws Exception {
        mockMvc.perform(get("/api/turnos"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaCrearTurnos() throws Exception {
        mockMvc.perform(post("/api/turnos"))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "medico", roles = "MEDICINA")
    void medicinaDeberiaCrearPacientes() throws Exception {
        mockMvc.perform(post("/api/pacientes"))
            .andExpect(status().isBadRequest());  // BadRequest por validación, no Forbidden
    }
    
    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaCrearPacientes() throws Exception {
        mockMvc.perform(post("/api/pacientes"))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "supervisor", roles = "SUPERVISOR")
    void supervisorDeberiaTenerAccesoCompleto() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk());
        
        mockMvc.perform(get("/api/turnos"))
            .andExpect(status().isOk());
        
        mockMvc.perform(get("/api/pacientes"))
            .andExpect(status().isOk());
        
        mockMvc.perform(get("/api/tareas"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaAccederAUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "medico", roles = "MEDICINA")
    void medicinaNoDeberiaAccederAUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isForbidden());
    }
}
```

**NOTAS:**
- `@WithMockUser` simula usuario autenticado con rol
- `status().isUnauthorized()` = 401 (sin autenticación)
- `status().isForbidden()` = 403 (autenticado pero sin permisos)
- `status().isBadRequest()` = 400 (tiene permisos pero datos inválidos)

---

#### 6. **Actualizar application.properties**

**Añadir al final de `src/main/resources/application.properties`:**

```properties
# Spring Security
spring.security.user.name=admin
spring.security.user.password=admin123
spring.security.user.roles=SUPERVISOR

# Logging de seguridad (para debug)
logging.level.org.springframework.security=DEBUG
```

**NOTA:** Estas son credenciales por defecto si no hay usuarios en BD. DataLoader las sobrescribe.

---

#### 7. **Commits Atómicos**

1. `config: Crear SecurityConfig con RBAC por roles`
2. `config: Crear CustomUserDetailsService para autenticación desde BD`
3. `config: Crear DataLoader con usuarios de prueba`
4. `test: Añadir tests de seguridad (8 tests)`
5. `docs: Actualizar README con credenciales de prueba`
6. `feat: Completar Fase 7 - Spring Security con RBAC`

---

#### 8. **Ejecutar al Terminar**

```bash
mvn test
```

**Resultado esperado:**
- Tests Security: 8/8 ✅
- **TOTAL PROYECTO: 65/65** ✅
   - Turno: 13
   - Paciente: 13
   - Usuario: 13
   - TareaClinica: 18
   - Security: 8

---

### 📋 VERIFICACIÓN MANUAL POST-IMPLEMENTACIÓN

#### Arrancar la aplicación:
```bash
mvn spring-boot:run
```

#### Probar autenticación con curl:

**1. Sin autenticación (debe fallar):**
```bash
curl http://localhost:8080/api/turnos
# Resultado esperado: 401 Unauthorized
```

**2. Con usuario ENFERMERIA:**
```bash
curl -u enfermera:enfermera123 http://localhost:8080/api/turnos
# Resultado esperado: 200 OK con lista de turnos

curl -u enfermera:enfermera123 -X POST http://localhost:8080/api/turnos
# Resultado esperado: 403 Forbidden (no tiene permiso)
```

**3. Con usuario MEDICINA:**
```bash
curl -u medico:medico123 http://localhost:8080/api/pacientes
# Resultado esperado: 200 OK

curl -u medico:medico123 http://localhost:8080/api/usuarios
# Resultado esperado: 403 Forbidden (solo SUPERVISOR)
```

**4. Con usuario SUPERVISOR:**
```bash
curl -u supervisor:supervisor123 http://localhost:8080/api/usuarios
# Resultado esperado: 200 OK

curl -u supervisor:supervisor123 -X DELETE http://localhost:8080/api/turnos/1
# Resultado esperado: 204 No Content
```

---

### ⚠️ CONSIDERACIONES ESPECIALES

**1. CSRF en Thymeleaf (Fase 8):**

Cuando se implementen vistas Thymeleaf, reactivar CSRF:
```java
.csrf(csrf -> csrf
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
)
```

**2. Logging de Seguridad:**

Si hay problemas, activar logs en `application.properties`:
```properties
logging.level.org.springframework.security=TRACE
```

**3. Excepciones Personalizadas:**

Para respuestas JSON en errores de seguridad, crear `AccessDeniedHandler` personalizado (opcional para Fase 7).

---

### 🎯 RESUMEN FASE 7

**Implementa:**
- ✅ Autenticación HTTP Basic
- ✅ Autorización RBAC (3 roles)
- ✅ UserDetailsService desde BD
- ✅ Usuarios de prueba automáticos
- ✅ Tests de seguridad (8 tests)

**Protege:**
- ✅ Endpoints por método HTTP
- ✅ Endpoints por rol
- ✅ Datos sensibles (passwords)

**Permite:**
- ✅ ENFERMERIA: Ver y actualizar sus tareas
- ✅ MEDICINA: Crear tareas y pacientes
- ✅ SUPERVISOR: Acceso completo

---

### 📦 CHECKLIST FASE 7

Antes de ejecutar:
- [ ] CLAUDE.md actualizado con instrucciones Fase 7
- [ ] Commit de CLAUDE.md realizado

Después de ejecutar:
- [ ] SecurityConfig.java creado
- [ ] CustomUserDetailsService.java creado
- [ ] DataLoader.java creado
- [ ] 8 tests de seguridad pasando
- [ ] Total proyecto: 65/65 tests
- [ ] 6 commits atómicos en GitHub
- [ ] Usuarios de prueba funcionando

---

## PASO 3: Hacer Commit del CLAUDE.md Actualizado

```bash
git add CLAUDE.md
git commit -m "docs: Actualizar CLAUDE.md para Fase 7 - Spring Security con RBAC

- Configuración de autenticación HTTP Basic
- Autorización por roles (ENFERMERIA, MEDICINA, SUPERVISOR)
- UserDetailsService personalizado desde BD
- DataLoader con usuarios de prueba
- Tests de seguridad (8 tests)
- Total esperado: 65/65 tests"

git push origin main
```

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
**ESTADO:** Fase 7 COMPLETADA - 66/66 tests pasando (Turno: 13 + Paciente: 13 + Usuario: 13 + TareaClinica: 18 + Security: 8 + App: 1)
**PRÓXIMA ACCIÓN:** Fase 8 - Vistas Thymeleaf (enfermeria/, medicina/, supervisor/, common/)