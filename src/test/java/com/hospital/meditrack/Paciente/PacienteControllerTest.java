package com.hospital.meditrack.Paciente;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteControllerTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    void deberiaObtenerTodosLosPacientes() {
        Paciente p1 = new Paciente();
        p1.setNombre("Juan");
        p1.setApellidos("García López");
        p1.setFechaNacimiento(LocalDate.of(1980, 5, 15));
        p1.setNumeroHistoriaClinica("HC-101");
        p1.setHabitacion("101A");
        pacienteService.crear(p1);

        Paciente p2 = new Paciente();
        p2.setNombre("María");
        p2.setApellidos("Martínez Ruiz");
        p2.setFechaNacimiento(LocalDate.of(1990, 3, 22));
        p2.setNumeroHistoriaClinica("HC-102");
        p2.setHabitacion("202B");
        pacienteService.crear(p2);

        List<Paciente> pacientes = pacienteService.obtenerTodos();

        assertNotNull(pacientes);
        assertTrue(pacientes.size() >= 2);

        boolean tieneJuan = pacientes.stream().anyMatch(p -> p.getNumeroHistoriaClinica().equals("HC-101"));
        boolean tieneMaria = pacientes.stream().anyMatch(p -> p.getNumeroHistoriaClinica().equals("HC-102"));

        assertTrue(tieneJuan, "Debería existir paciente HC-101");
        assertTrue(tieneMaria, "Debería existir paciente HC-102");
    }

    @Test
    void deberiaObtenerPorId() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Carlos");
        paciente.setApellidos("Fernández Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1965, 11, 30));
        paciente.setNumeroHistoriaClinica("HC-103");
        paciente.setHabitacion("303C");
        Paciente pacienteCreado = pacienteService.crear(paciente);

        Optional<Paciente> resultado = pacienteService.obtenerPorId(pacienteCreado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
        assertEquals("HC-103", resultado.get().getNumeroHistoriaClinica());
    }

    @Test
    void deberiaRetornarVacioSiPacienteNoExiste() {
        Optional<Paciente> resultado = pacienteService.obtenerPorId(99999L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deberiaCrearNuevoPaciente() {
        Paciente pacienteNuevo = new Paciente();
        pacienteNuevo.setNombre("Ana");
        pacienteNuevo.setApellidos("Sánchez Gómez");
        pacienteNuevo.setFechaNacimiento(LocalDate.of(2000, 7, 4));
        pacienteNuevo.setNumeroHistoriaClinica("HC-104");
        pacienteNuevo.setHabitacion("404D");

        Paciente pacienteCreado = pacienteService.crear(pacienteNuevo);

        assertNotNull(pacienteCreado.getId());
        assertEquals("Ana", pacienteCreado.getNombre());
        assertEquals("HC-104", pacienteCreado.getNumeroHistoriaClinica());

        Optional<Paciente> pacienteRecuperado = pacienteService.obtenerPorId(pacienteCreado.getId());
        assertTrue(pacienteRecuperado.isPresent());
        assertEquals("Sánchez Gómez", pacienteRecuperado.get().getApellidos());
    }

    @Test
    void noDeberiaCrearPacienteDuplicado() {
        Paciente primerPaciente = new Paciente();
        primerPaciente.setNombre("Luis");
        primerPaciente.setApellidos("Torres Vila");
        primerPaciente.setFechaNacimiento(LocalDate.of(1970, 2, 14));
        primerPaciente.setNumeroHistoriaClinica("HC-105");
        primerPaciente.setHabitacion("505E");
        pacienteService.crear(primerPaciente);

        Paciente pacienteDuplicado = new Paciente();
        pacienteDuplicado.setNombre("Otro");
        pacienteDuplicado.setApellidos("Apellido");
        pacienteDuplicado.setFechaNacimiento(LocalDate.of(1985, 9, 1));
        pacienteDuplicado.setNumeroHistoriaClinica("HC-105");
        pacienteDuplicado.setHabitacion("606F");

        assertThrows(IllegalArgumentException.class, () -> {
            pacienteService.crear(pacienteDuplicado);
        });
    }

    @Test
    void deberiaActualizarPacienteExistente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Rosa");
        paciente.setApellidos("Blanco Navarro");
        paciente.setFechaNacimiento(LocalDate.of(1988, 12, 25));
        paciente.setNumeroHistoriaClinica("HC-106");
        paciente.setHabitacion("107G");
        Paciente pacienteCreado = pacienteService.crear(paciente);

        Paciente pacienteActualizado = new Paciente();
        pacienteActualizado.setNombre("Rosa María");
        pacienteActualizado.setApellidos("Blanco Navarro");
        pacienteActualizado.setFechaNacimiento(LocalDate.of(1988, 12, 25));
        pacienteActualizado.setNumeroHistoriaClinica("HC-106");
        pacienteActualizado.setHabitacion("208H");

        Paciente resultado = pacienteService.actualizar(pacienteCreado.getId(), pacienteActualizado);

        assertEquals("Rosa María", resultado.getNombre());
        assertEquals("208H", resultado.getHabitacion());
        assertEquals("HC-106", resultado.getNumeroHistoriaClinica());
    }

    @Test
    void deberiaEliminarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Temporal");
        paciente.setApellidos("Apellido Test");
        paciente.setFechaNacimiento(LocalDate.of(1999, 6, 10));
        paciente.setNumeroHistoriaClinica("HC-999");
        paciente.setHabitacion("999Z");
        Paciente pacienteCreado = pacienteService.crear(paciente);
        Long idPaciente = pacienteCreado.getId();

        assertTrue(pacienteService.obtenerPorId(idPaciente).isPresent());

        pacienteService.eliminar(idPaciente);

        assertFalse(pacienteService.obtenerPorId(idPaciente).isPresent());
    }
}