package com.hospital.meditrack.TareaClinica;

import com.hospital.meditrack.model.entity.*;
import com.hospital.meditrack.model.enums.*;
import com.hospital.meditrack.repository.*;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class TareaClinicaControllerTest {

    @Autowired
    private TareaClinicaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Test
    void deberiaObtenerTodasLasTareas() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u1_ctrl");
        Paciente paciente = crearPaciente("HC-C001");

        crearTarea(usuario, paciente, turno, "Tarea A");
        crearTarea(usuario, paciente, turno, "Tarea B");

        List<TareaClinica> tareas = tareaService.obtenerTodas();

        assertNotNull(tareas);
        assertTrue(tareas.size() >= 2);
        assertTrue(tareas.stream().anyMatch(t -> t.getDescripcion().equals("Tarea A")));
        assertTrue(tareas.stream().anyMatch(t -> t.getDescripcion().equals("Tarea B")));
    }

    @Test
    void deberiaObtenerTareaPorId() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u2_ctrl");
        Paciente paciente = crearPaciente("HC-C002");

        TareaClinica tarea = crearTarea(usuario, paciente, turno, "Control de presión");

        Optional<TareaClinica> resultado = tareaService.obtenerPorId(tarea.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Control de presión", resultado.get().getDescripcion());
        assertEquals(TipoTarea.CONTROL_SIGNOS_VITALES, resultado.get().getTipo());
    }

    @Test
    void deberiaObtenerTareasPorPaciente() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u3_ctrl");
        Paciente paciente = crearPaciente("HC-C003");

        crearTarea(usuario, paciente, turno, "Cura pie derecho");
        crearTarea(usuario, paciente, turno, "Cura rodilla");

        List<TareaClinica> tareas = tareaService.obtenerPorPaciente(paciente.getId());

        assertEquals(2, tareas.size());
        assertTrue(tareas.stream().allMatch(t -> t.getPaciente().getId().equals(paciente.getId())));
    }

    @Test
    void deberiaObtenerTareasPorUsuario() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u4_ctrl");
        Paciente paciente = crearPaciente("HC-C004");

        crearTarea(usuario, paciente, turno, "Medicación mañana");

        List<TareaClinica> tareas = tareaService.obtenerPorUsuario(usuario.getId());

        assertFalse(tareas.isEmpty());
        assertTrue(tareas.stream().anyMatch(t -> t.getDescripcion().equals("Medicación mañana")));
    }

    @Test
    void deberiaCrearNuevaTarea() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u5_ctrl");
        Paciente paciente = crearPaciente("HC-C005");

        TareaClinica nueva = new TareaClinica();
        nueva.setDescripcion("Higiene personal");
        nueva.setTipo(TipoTarea.HIGIENE);
        nueva.setPrioridad(Prioridad.BAJA);
        nueva.setEstado(EstadoTarea.PENDIENTE);
        nueva.setFecha(LocalDateTime.now());
        nueva.setObservaciones("Sin novedades");
        nueva.setAsignadoA(usuario);
        nueva.setPaciente(paciente);
        nueva.setTurno(turno);

        TareaClinica creada = tareaService.crear(nueva);

        assertNotNull(creada.getId());
        assertEquals("Higiene personal", creada.getDescripcion());
        assertEquals(TipoTarea.HIGIENE, creada.getTipo());
        assertEquals(Prioridad.BAJA, creada.getPrioridad());
    }

    @Test
    void noDeberiaCrearTareaSinRelaciones() {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Sin relaciones");
        tarea.setTipo(TipoTarea.OTRO);
        tarea.setPrioridad(Prioridad.MEDIA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        tarea.setAsignadoA(null);
        tarea.setPaciente(null);
        tarea.setTurno(null);

        assertThrows(IllegalArgumentException.class, () -> {
            tareaService.crear(tarea);
        });
    }

    @Test
    void deberiaActualizarTarea() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u6_ctrl");
        Paciente paciente = crearPaciente("HC-C006");

        TareaClinica tarea = crearTarea(usuario, paciente, turno, "Descripción original");

        TareaClinica datos = new TareaClinica();
        datos.setDescripcion("Descripción actualizada");
        datos.setTipo(TipoTarea.CURA);
        datos.setPrioridad(Prioridad.URGENTE);
        datos.setEstado(EstadoTarea.EN_CURSO);
        datos.setFecha(LocalDateTime.now());
        datos.setObservaciones("Actualizado en prueba");

        TareaClinica resultado = tareaService.actualizar(tarea.getId(), datos);

        assertEquals("Descripción actualizada", resultado.getDescripcion());
        assertEquals(TipoTarea.CURA, resultado.getTipo());
        assertEquals(EstadoTarea.EN_CURSO, resultado.getEstado());
        assertEquals(Prioridad.URGENTE, resultado.getPrioridad());
    }

    @Test
    void deberiaEliminarTarea() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario("u7_ctrl");
        Paciente paciente = crearPaciente("HC-C007");

        TareaClinica tarea = crearTarea(usuario, paciente, turno, "Tarea temporal");
        Long id = tarea.getId();

        assertTrue(tareaService.obtenerPorId(id).isPresent());

        tareaService.eliminar(id);

        assertFalse(tareaService.obtenerPorId(id).isPresent());
    }

    private Turno crearTurno() {
        Turno turno = new Turno();
        turno.setNombre("Turno-Ctrl-" + System.currentTimeMillis());
        turno.setHoraInicio(LocalTime.of(7, 0));
        turno.setHoraFin(LocalTime.of(15, 0));
        return turnoRepository.save(turno);
    }

    private Usuario crearUsuario(String usernameBase) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellidos("Controlador");
        usuario.setUsername(usernameBase + "-" + System.currentTimeMillis());
        usuario.setPassword("password123");
        usuario.setRol(Rol.ENFERMERIA);
        return usuarioService.crear(usuario);
    }

    private Paciente crearPaciente(String nhcBase) {
        Paciente paciente = new Paciente();
        paciente.setNombre("Paciente");
        paciente.setApellidos("Test");
        paciente.setFechaNacimiento(LocalDate.of(1985, 6, 15));
        paciente.setNumeroHistoriaClinica(nhcBase + (System.currentTimeMillis() % 10000L));
        paciente.setHabitacion("100");
        return pacienteRepository.save(paciente);
    }

    private TareaClinica crearTarea(Usuario usuario, Paciente paciente, Turno turno, String descripcion) {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion(descripcion);
        tarea.setTipo(TipoTarea.CONTROL_SIGNOS_VITALES);
        tarea.setPrioridad(Prioridad.MEDIA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        tarea.setAsignadoA(usuario);
        tarea.setPaciente(paciente);
        tarea.setTurno(turno);
        return tareaService.crear(tarea);
    }
}
