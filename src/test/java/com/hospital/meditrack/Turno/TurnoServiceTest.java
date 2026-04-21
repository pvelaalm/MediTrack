package com.hospital.meditrack.Turno;

import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.repository.TurnoRepository;
import com.hospital.meditrack.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para TurnoService.
 * Usa Mockito para simular el repositorio.
 */
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {
    
    @Mock
    private TurnoRepository turnoRepository;
    
    @InjectMocks
    private TurnoService turnoService;
    
    @Test
    void deberiaObtenerTodos() {
        // Arrange
        Turno turno1 = new Turno(1L, "Mañana", LocalTime.of(7, 0), LocalTime.of(15, 0));
        Turno turno2 = new Turno(2L, "Tarde", LocalTime.of(15, 0), LocalTime.of(23, 0));
        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno1, turno2));
        
        // Act
        List<Turno> resultado = turnoService.obtenerTodos();
        
        // Assert
        assertEquals(2, resultado.size());
        verify(turnoRepository, times(1)).findAll();
    }
    
    @Test
    void deberiaObtenerPorId() {
        // Arrange
        Turno turno = new Turno(1L, "Mañana", LocalTime.of(7, 0), LocalTime.of(15, 0));
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        
        // Act
        Optional<Turno> resultado = turnoService.obtenerPorId(1L);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Mañana", resultado.get().getNombre());
    }
    
    @Test
    void deberiaCrearTurno() {
        // Arrange
        Turno turno = new Turno(null, "Noche", LocalTime.of(23, 0), LocalTime.of(7, 0));
        Turno turnoGuardado = new Turno(1L, "Noche", LocalTime.of(23, 0), LocalTime.of(7, 0));
        
        when(turnoRepository.findByNombre("Noche")).thenReturn(Optional.empty());
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoGuardado);
        
        // Act
        Turno resultado = turnoService.crear(turno);
        
        // Assert
        assertNotNull(resultado.getId());
        assertEquals("Noche", resultado.getNombre());
        verify(turnoRepository, times(1)).save(turno);
    }
    
    @Test
    void noDeberiaCrearTurnoDuplicado() {
        // Arrange
        Turno turnoExistente = new Turno(1L, "Mañana", LocalTime.of(7, 0), LocalTime.of(15, 0));
        Turno turnoNuevo = new Turno(null, "Mañana", LocalTime.of(7, 0), LocalTime.of(15, 0));
        
        when(turnoRepository.findByNombre("Mañana")).thenReturn(Optional.of(turnoExistente));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> turnoService.crear(turnoNuevo)
        );
        
        assertTrue(exception.getMessage().contains("Ya existe un turno"));
        verify(turnoRepository, never()).save(any(Turno.class));
    }
}
