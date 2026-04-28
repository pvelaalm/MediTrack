package com.hospital.meditrack.Paciente;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.repository.PacienteRepository;
import com.hospital.meditrack.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void deberiaObtenerTodos() {
        Paciente p1 = new Paciente(1L, "Juan", "García", LocalDate.of(1980, 1, 1), "HC-001", "101A");
        Paciente p2 = new Paciente(2L, "María", "Martínez", LocalDate.of(1990, 6, 15), "HC-002", "202B");
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Paciente> resultado = pacienteService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    void deberiaObtenerPorId() {
        Paciente paciente = new Paciente(1L, "Juan", "García", LocalDate.of(1980, 1, 1), "HC-001", "101A");
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = pacienteService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        assertEquals("HC-001", resultado.get().getNumeroHistoriaClinica());
    }

    @Test
    void deberiaCrearPaciente() {
        Paciente paciente = new Paciente(null, "Pedro", "López", LocalDate.of(1975, 8, 20), "HC-003", "303C");
        Paciente pacienteGuardado = new Paciente(1L, "Pedro", "López", LocalDate.of(1975, 8, 20), "HC-003", "303C");

        when(pacienteRepository.findByNumeroHistoriaClinica("HC-003")).thenReturn(Optional.empty());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteGuardado);

        Paciente resultado = pacienteService.crear(paciente);

        assertNotNull(resultado.getId());
        assertEquals("Pedro", resultado.getNombre());
        assertEquals("HC-003", resultado.getNumeroHistoriaClinica());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    void noDeberiaCrearPacienteDuplicado() {
        Paciente pacienteExistente = new Paciente(1L, "Juan", "García", LocalDate.of(1980, 1, 1), "HC-001", "101A");
        Paciente pacienteNuevo = new Paciente(null, "Otro", "Apellido", LocalDate.of(2000, 1, 1), "HC-001", "202B");

        when(pacienteRepository.findByNumeroHistoriaClinica("HC-001")).thenReturn(Optional.of(pacienteExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pacienteService.crear(pacienteNuevo)
        );

        assertTrue(exception.getMessage().contains("Ya existe un paciente"));
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }
}
