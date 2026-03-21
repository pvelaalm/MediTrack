package com.hospital.meditrack;


import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TurnoRepositoryTest {
    
    @Autowired
    private TurnoRepository turnoRepository;
    
    @Test
    void deberiaGuardarYRecuperarTurno() {
        // Arrange - Crear un turno
        Turno turno = new Turno();
        turno.setNombre("Mañana");
        turno.setHoraInicio(LocalTime.of(7, 0));
        turno.setHoraFin(LocalTime.of(15, 0));
        
        
        // Act - Guardar en BD
        Turno turnoGuardado = turnoRepository.save(turno);
        
        // Assert - Verificar que se guardó
        assertNotNull(turnoGuardado.getId());
        assertEquals("Mañana", turnoGuardado.getNombre());
        
        // Act - Buscar por ID
        Optional<Turno> turnoEncontrado = turnoRepository.findById(turnoGuardado.getId());
        
        // Assert - Verificar que se encontró
        assertTrue(turnoEncontrado.isPresent());
        assertEquals("Mañana", turnoEncontrado.get().getNombre());
    }
    
    @Test
    void deberiaBuscarPorNombre() {
        // Arrange
        Turno turno = new Turno();
        turno.setNombre("Tarde");
        turno.setHoraInicio(LocalTime.of(15, 0));
        turno.setHoraFin(LocalTime.of(23, 0));
        turnoRepository.save(turno);
        
        // Act
        Optional<Turno> encontrado = turnoRepository.findByNombre("Tarde");
        
        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(LocalTime.of(15, 0), encontrado.get().getHoraInicio());
    }
}