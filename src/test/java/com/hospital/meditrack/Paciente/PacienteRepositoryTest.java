package com.hospital.meditrack.Paciente;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    void deberiaGuardarYRecuperarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellidos("García López");
        paciente.setFechaNacimiento(LocalDate.of(1980, 5, 15));
        paciente.setNumeroHistoriaClinica("HC-001");
        paciente.setHabitacion("101A");

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        assertNotNull(pacienteGuardado.getId());
        assertEquals("Juan", pacienteGuardado.getNombre());
        assertEquals("HC-001", pacienteGuardado.getNumeroHistoriaClinica());

        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(pacienteGuardado.getId());

        assertTrue(pacienteEncontrado.isPresent());
        assertEquals("García López", pacienteEncontrado.get().getApellidos());
        assertEquals("101A", pacienteEncontrado.get().getHabitacion());
    }

    @Test
    void deberiaBuscarPorNumeroHistoriaClinica() {
        Paciente paciente = new Paciente();
        paciente.setNombre("María");
        paciente.setApellidos("Martínez Ruiz");
        paciente.setFechaNacimiento(LocalDate.of(1995, 3, 22));
        paciente.setNumeroHistoriaClinica("HC-002");
        paciente.setHabitacion("205B");
        pacienteRepository.save(paciente);

        Optional<Paciente> encontrado = pacienteRepository.findByNumeroHistoriaClinica("HC-002");

        assertTrue(encontrado.isPresent());
        assertEquals("María", encontrado.get().getNombre());
        assertEquals(LocalDate.of(1995, 3, 22), encontrado.get().getFechaNacimiento());
    }
}