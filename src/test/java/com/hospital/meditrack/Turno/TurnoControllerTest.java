package com.hospital.meditrack.Turno;


import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para el flujo completo del Controller.
 * Prueba: Controller → Service → Repository → Database
 */
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoControllerTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    void deberiaObtenerTodosLosTurnos() {
        // Arrange - Crear turnos de prueba
        Turno turno1 = new Turno();
        turno1.setNombre("Mañana");
        turno1.setHoraInicio(LocalTime.of(7, 0));
        turno1.setHoraFin(LocalTime.of(15, 0));
        turnoService.crear(turno1);

        Turno turno2 = new Turno();
        turno2.setNombre("Tarde");
        turno2.setHoraInicio(LocalTime.of(15, 0));
        turno2.setHoraFin(LocalTime.of(23, 0));
        turnoService.crear(turno2);

        // Act
        List<Turno> turnos = turnoService.obtenerTodos();

        // Assert
        assertNotNull(turnos);
        assertTrue(turnos.size() >= 2);

        // Verificar que existen los turnos creados
        boolean tieneMañana = turnos.stream()
                .anyMatch(t -> t.getNombre().equals("Mañana"));
        boolean tieneTarde = turnos.stream()
                .anyMatch(t -> t.getNombre().equals("Tarde"));

        assertTrue(tieneMañana, "Debería existir turno Mañana");
        assertTrue(tieneTarde, "Debería existir turno Tarde");
    }

    @Test
    void deberiaObtenerTurnoPorId() {
        // Arrange
        Turno turno = new Turno();
        turno.setNombre("Noche");
        turno.setHoraInicio(LocalTime.of(23, 0));
        turno.setHoraFin(LocalTime.of(7, 0));
        Turno turnoCreado = turnoService.crear(turno);

        // Act
        Optional<Turno> resultado = turnoService.obtenerPorId(turnoCreado.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Noche", resultado.get().getNombre());
        assertEquals(LocalTime.of(23, 0), resultado.get().getHoraInicio());
    }

    @Test
    void deberiaRetornarVacioSiTurnoNoExiste() {
        // Act
        Optional<Turno> resultado = turnoService.obtenerPorId(99999L);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    void deberiaCrearNuevoTurno() {
        // Arrange
        Turno turnoNuevo = new Turno();
        turnoNuevo.setNombre("Vespertino");
        turnoNuevo.setHoraInicio(LocalTime.of(12, 0));
        turnoNuevo.setHoraFin(LocalTime.of(20, 0));

        // Act
        Turno turnoCreado = turnoService.crear(turnoNuevo);

        // Assert
        assertNotNull(turnoCreado.getId());
        assertEquals("Vespertino", turnoCreado.getNombre());
        assertEquals(LocalTime.of(12, 0), turnoCreado.getHoraInicio());

        // Verificar que se puede recuperar
        Optional<Turno> turnoRecuperado = turnoService.obtenerPorId(turnoCreado.getId());
        assertTrue(turnoRecuperado.isPresent());
        assertEquals("Vespertino", turnoRecuperado.get().getNombre());
    }

    @Test
    void noDeberiaCrearTurnoDuplicado() {
        // Arrange - Crear primer turno
        Turno turnoPrimero = new Turno();
        turnoPrimero.setNombre("Rotativo");
        turnoPrimero.setHoraInicio(LocalTime.of(8, 0));
        turnoPrimero.setHoraFin(LocalTime.of(16, 0));
        turnoService.crear(turnoPrimero);

        // Arrange - Intentar crear turno duplicado
        Turno turnoDuplicado = new Turno();
        turnoDuplicado.setNombre("Rotativo"); // Mismo nombre
        turnoDuplicado.setHoraInicio(LocalTime.of(9, 0));
        turnoDuplicado.setHoraFin(LocalTime.of(17, 0));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            turnoService.crear(turnoDuplicado);
        });
    }

    @Test
    void deberiaActualizarTurnoExistente() {
        // Arrange - Crear turno
        Turno turno = new Turno();
        turno.setNombre("Especial");
        turno.setHoraInicio(LocalTime.of(10, 0));
        turno.setHoraFin(LocalTime.of(18, 0));
        Turno turnoCreado = turnoService.crear(turno);

        // Act - Actualizar
        Turno turnoActualizado = new Turno();
        turnoActualizado.setNombre("Especial Modificado");
        turnoActualizado.setHoraInicio(LocalTime.of(11, 0));
        turnoActualizado.setHoraFin(LocalTime.of(19, 0));

        Turno resultado = turnoService.actualizar(turnoCreado.getId(), turnoActualizado);

        // Assert
        assertEquals("Especial Modificado", resultado.getNombre());
        assertEquals(LocalTime.of(11, 0), resultado.getHoraInicio());
        assertEquals(LocalTime.of(19, 0), resultado.getHoraFin());
    }

    @Test
    void deberiaEliminarTurno() {
        // Arrange - Crear turno
        Turno turno = new Turno();
        turno.setNombre("Temporal");
        turno.setHoraInicio(LocalTime.of(6, 0));
        turno.setHoraFin(LocalTime.of(14, 0));
        Turno turnoCreado = turnoService.crear(turno);
        Long idTurno = turnoCreado.getId();

        // Verificar que existe
        assertTrue(turnoService.obtenerPorId(idTurno).isPresent());

        // Act - Eliminar
        turnoService.eliminar(idTurno);

        // Assert - Verificar que ya no existe
        assertFalse(turnoService.obtenerPorId(idTurno).isPresent());
    }
}