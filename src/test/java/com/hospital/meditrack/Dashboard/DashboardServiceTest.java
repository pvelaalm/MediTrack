package com.hospital.meditrack.Dashboard;

import com.hospital.meditrack.model.dto.DashboardData;
import com.hospital.meditrack.model.dto.DashboardStats;
import com.hospital.meditrack.model.dto.UsuarioConTareas;
import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.service.DashboardService;
import com.hospital.meditrack.service.PacienteService;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.TurnoService;
import com.hospital.meditrack.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class DashboardServiceTest {

    @Mock
    private TareaClinicaService tareaService;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TurnoService turnoService;

    @InjectMocks
    private DashboardService dashboardService;

    private TareaClinica tareaUrgentePendiente;
    private TareaClinica tareaAltaEnCurso;
    private TareaClinica tareaMediaRealizada;
    private Usuario enfermero1;
    private Usuario enfermero2;
    private Usuario medico;
    private Turno turnoManana;

    @BeforeEach
    void setUp() {
        turnoManana = new Turno();
        turnoManana.setId(1L);
        turnoManana.setNombre("Mañana");

        enfermero1 = new Usuario();
        enfermero1.setId(1L);
        enfermero1.setNombre("Ana");
        enfermero1.setApellidos("García");
        enfermero1.setRol(Rol.ENFERMERIA);

        enfermero2 = new Usuario();
        enfermero2.setId(2L);
        enfermero2.setNombre("Carlos");
        enfermero2.setApellidos("López");
        enfermero2.setRol(Rol.ENFERMERIA);

        medico = new Usuario();
        medico.setId(3L);
        medico.setNombre("Dr. Pérez");
        medico.setApellidos("Ruiz");
        medico.setRol(Rol.MEDICINA);

        tareaUrgentePendiente = new TareaClinica();
        tareaUrgentePendiente.setId(1L);
        tareaUrgentePendiente.setPrioridad(Prioridad.URGENTE);
        tareaUrgentePendiente.setEstado(EstadoTarea.PENDIENTE);
        tareaUrgentePendiente.setAsignadoA(enfermero1);
        tareaUrgentePendiente.setTurno(turnoManana);

        tareaAltaEnCurso = new TareaClinica();
        tareaAltaEnCurso.setId(2L);
        tareaAltaEnCurso.setPrioridad(Prioridad.ALTA);
        tareaAltaEnCurso.setEstado(EstadoTarea.EN_CURSO);
        tareaAltaEnCurso.setAsignadoA(enfermero1);
        tareaAltaEnCurso.setTurno(turnoManana);

        tareaMediaRealizada = new TareaClinica();
        tareaMediaRealizada.setId(3L);
        tareaMediaRealizada.setPrioridad(Prioridad.MEDIA);
        tareaMediaRealizada.setEstado(EstadoTarea.REALIZADA);
        tareaMediaRealizada.setAsignadoA(enfermero2);
        tareaMediaRealizada.setTurno(turnoManana);
    }

    @Test
    void deberiaObtenerEstadisticasGeneralesValidas() {
        List<TareaClinica> tareas = Arrays.asList(tareaUrgentePendiente, tareaAltaEnCurso, tareaMediaRealizada);
        when(tareaService.obtenerTodas()).thenReturn(tareas);
        when(pacienteService.obtenerTodos()).thenReturn(List.of());
        when(usuarioService.obtenerTodos()).thenReturn(Arrays.asList(enfermero1, enfermero2, medico));

        DashboardStats stats = dashboardService.obtenerEstadisticasGenerales();

        assertNotNull(stats);
        assertEquals(3, stats.getTotalTareas());
        assertEquals(1, stats.getTareasPendientes());
        assertEquals(1, stats.getTareasEnCurso());
        assertEquals(1, stats.getTareasRealizadas());
        assertEquals(0, stats.getTareasCanceladas());
        assertEquals(2, stats.getTotalEnfermeros());
    }

    @Test
    void deberiaObtenerCargaPorTurno() {
        List<Turno> turnos = List.of(turnoManana);
        List<TareaClinica> tareasManana = Arrays.asList(tareaUrgentePendiente, tareaAltaEnCurso, tareaMediaRealizada);
        when(turnoService.obtenerTodos()).thenReturn(turnos);
        when(tareaService.obtenerPorTurno(1L)).thenReturn(tareasManana);

        Map<String, Long> carga = dashboardService.obtenerCargaPorTurno();

        assertNotNull(carga);
        assertTrue(carga.containsKey("Mañana"));
        assertEquals(3L, carga.get("Mañana"));
    }

    @Test
    void deberiaObtenerTop5EnfermerosOrdenadosPorTareas() {
        when(usuarioService.obtenerTodos()).thenReturn(Arrays.asList(enfermero1, enfermero2, medico));
        when(tareaService.obtenerPorUsuario(1L)).thenReturn(
                Arrays.asList(tareaUrgentePendiente, tareaAltaEnCurso));
        when(tareaService.obtenerPorUsuario(2L)).thenReturn(
                List.of(tareaMediaRealizada));

        List<UsuarioConTareas> top = dashboardService.obtenerTop5EnfermerosConMasTareas();

        assertNotNull(top);
        assertFalse(top.isEmpty());
        assertEquals("Ana García", top.get(0).getNombreCompleto());
        assertEquals(2L, top.get(0).getNumeroTareas());
        assertEquals(1L, top.get(1).getNumeroTareas());
    }
}
