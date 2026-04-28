package com.hospital.meditrack.TareaClinica;

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

        TareaClinica tareaGuardada = tareaRepository.save(tarea);

        assertNotNull(tareaGuardada.getId());
        assertEquals("Administrar medicación", tareaGuardada.getDescripcion());
        assertEquals(TipoTarea.MEDICACION, tareaGuardada.getTipo());
        assertEquals(usuario.getId(), tareaGuardada.getAsignadoA().getId());
        assertEquals(paciente.getId(), tareaGuardada.getPaciente().getId());
        assertEquals(turno.getId(), tareaGuardada.getTurno().getId());
    }

    @Test
    void deberiaBuscarTareasPorPaciente() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario();
        Paciente paciente = crearPaciente();

        crearTarea(usuario, paciente, turno, "Tarea 1");
        crearTarea(usuario, paciente, turno, "Tarea 2");

        List<TareaClinica> tareas = tareaRepository.findByPacienteId(paciente.getId());

        assertEquals(2, tareas.size());
        assertTrue(tareas.stream().allMatch(t -> t.getPaciente().getId().equals(paciente.getId())));
    }

    @Test
    void deberiaBuscarTareasPorEstado() {
        Turno turno = crearTurno();
        Usuario usuario = crearUsuario();
        Paciente paciente = crearPaciente();

        crearTarea(usuario, paciente, turno, "Tarea pendiente");

        List<TareaClinica> tareas = tareaRepository.findByEstado(EstadoTarea.PENDIENTE);

        assertFalse(tareas.isEmpty());
        assertTrue(tareas.stream().anyMatch(t -> t.getDescripcion().equals("Tarea pendiente")));
    }

    private Turno crearTurno() {
        Turno turno = new Turno();
        turno.setNombre("Turno-Test-" + System.currentTimeMillis());
        turno.setHoraInicio(LocalTime.of(7, 0));
        turno.setHoraFin(LocalTime.of(15, 0));
        return turnoRepository.save(turno);
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellidos("Usuario");
        usuario.setUsername("test-" + System.currentTimeMillis());
        usuario.setPassword("hashedpassword");
        usuario.setRol(Rol.ENFERMERIA);
        return usuarioRepository.save(usuario);
    }

    private Paciente crearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellidos("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setNumeroHistoriaClinica("HC" + (System.currentTimeMillis() % 100000L));
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
