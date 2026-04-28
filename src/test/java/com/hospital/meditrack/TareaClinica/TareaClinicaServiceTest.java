package com.hospital.meditrack.TareaClinica;

import com.hospital.meditrack.model.entity.*;
import com.hospital.meditrack.model.enums.*;
import com.hospital.meditrack.repository.*;
import com.hospital.meditrack.service.TareaClinicaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TareaClinicaServiceTest {

    @Mock
    private TareaClinicaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TareaClinicaService tareaService;

    @Test
    void deberiaObtenerTodas() {
        TareaClinica t1 = crearTareaConId(1L, "Tarea 1");
        TareaClinica t2 = crearTareaConId(2L, "Tarea 2");
        when(tareaRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TareaClinica> resultado = tareaService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    void deberiaObtenerPorId() {
        TareaClinica tarea = crearTareaConId(1L, "Medicación matutina");
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        Optional<TareaClinica> resultado = tareaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Medicación matutina", resultado.get().getDescripcion());
    }

    @Test
    void deberiaCrearTareaConRelacionesValidas() {
        Usuario usuario = new Usuario(1L, "Juan", "García", "jgarcia", "hash", Rol.ENFERMERIA);
        Paciente paciente = new Paciente(1L, "María", "López", null, "HC-001", "101");
        Turno turno = new Turno(1L, "Mañana", null, null);

        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Control de signos");
        tarea.setTipo(TipoTarea.CONTROL_SIGNOS_VITALES);
        tarea.setPrioridad(Prioridad.ALTA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        tarea.setAsignadoA(usuario);
        tarea.setPaciente(paciente);
        tarea.setTurno(turno);

        TareaClinica tareaGuardada = new TareaClinica();
        tareaGuardada.setId(1L);
        tareaGuardada.setDescripcion("Control de signos");
        tareaGuardada.setAsignadoA(usuario);
        tareaGuardada.setPaciente(paciente);
        tareaGuardada.setTurno(turno);

        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(pacienteRepository.existsById(1L)).thenReturn(true);
        when(turnoRepository.existsById(1L)).thenReturn(true);
        when(tareaRepository.save(any(TareaClinica.class))).thenReturn(tareaGuardada);

        TareaClinica resultado = tareaService.crear(tarea);

        assertNotNull(resultado.getId());
        assertEquals("Control de signos", resultado.getDescripcion());
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void noDeberiaCrearTareaSinUsuario() {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Cura");
        tarea.setAsignadoA(null);
        tarea.setPaciente(new Paciente(1L, "María", "López", null, "HC-001", "101"));
        tarea.setTurno(new Turno(1L, "Mañana", null, null));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> tareaService.crear(tarea)
        );

        assertTrue(exception.getMessage().contains("usuario"));
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void noDeberiaCrearTareaSinPaciente() {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Higiene");
        tarea.setAsignadoA(new Usuario(1L, "Juan", "García", "jgarcia", "hash", Rol.ENFERMERIA));
        tarea.setPaciente(null);
        tarea.setTurno(new Turno(1L, "Mañana", null, null));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> tareaService.crear(tarea)
        );

        assertTrue(exception.getMessage().contains("paciente"));
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void noDeberiaCrearTareaSinTurno() {
        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion("Movilización");
        tarea.setAsignadoA(new Usuario(1L, "Juan", "García", "jgarcia", "hash", Rol.ENFERMERIA));
        tarea.setPaciente(new Paciente(1L, "María", "López", null, "HC-001", "101"));
        tarea.setTurno(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> tareaService.crear(tarea)
        );

        assertTrue(exception.getMessage().contains("turno"));
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void deberiaActualizarTarea() {
        TareaClinica tareaExistente = crearTareaConId(1L, "Descripción original");
        TareaClinica tareaActualizada = new TareaClinica();
        tareaActualizada.setDescripcion("Descripción actualizada");
        tareaActualizada.setTipo(TipoTarea.CURA);
        tareaActualizada.setPrioridad(Prioridad.URGENTE);
        tareaActualizada.setEstado(EstadoTarea.EN_CURSO);
        tareaActualizada.setFecha(LocalDateTime.now());

        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaRepository.save(any(TareaClinica.class))).thenAnswer(i -> i.getArgument(0));

        TareaClinica resultado = tareaService.actualizar(1L, tareaActualizada);

        assertEquals("Descripción actualizada", resultado.getDescripcion());
        assertEquals(TipoTarea.CURA, resultado.getTipo());
        assertEquals(EstadoTarea.EN_CURSO, resultado.getEstado());
        verify(tareaRepository, times(1)).save(tareaExistente);
    }

    private TareaClinica crearTareaConId(Long id, String descripcion) {
        TareaClinica tarea = new TareaClinica();
        tarea.setId(id);
        tarea.setDescripcion(descripcion);
        tarea.setTipo(TipoTarea.MEDICACION);
        tarea.setPrioridad(Prioridad.MEDIA);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setFecha(LocalDateTime.now());
        return tarea;
    }
}
