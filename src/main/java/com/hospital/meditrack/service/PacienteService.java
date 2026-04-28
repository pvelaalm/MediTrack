package com.hospital.meditrack.service;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de pacientes.
 * Contiene la lógica de negocio relacionada con pacientes.
 */
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Obtiene todos los pacientes.
     */
    @Transactional(readOnly = true)
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    /**
     * Obtiene un paciente por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    /**
     * Obtiene un paciente por su número de historia clínica.
     */
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorNumeroHistoriaClinica(String numeroHistoriaClinica) {
        return pacienteRepository.findByNumeroHistoriaClinica(numeroHistoriaClinica);
    }

    /**
     * Crea un nuevo paciente.
     * Valida que no exista ya un paciente con el mismo número de historia clínica.
     */
    @Transactional
    public Paciente crear(Paciente paciente) {
        // Validar que no exista ya un paciente con ese número de historia clínica
        if (pacienteRepository.findByNumeroHistoriaClinica(
                paciente.getNumeroHistoriaClinica()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un paciente con el número de historia clínica: "
                            + paciente.getNumeroHistoriaClinica());
        }
        return pacienteRepository.save(paciente);
    }

    /**
     * Actualiza un paciente existente.
     */
    @Transactional
    public Paciente actualizar(Long id, Paciente pacienteActualizado) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNombre(pacienteActualizado.getNombre());
                    paciente.setApellidos(pacienteActualizado.getApellidos());
                    paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
                    paciente.setNumeroHistoriaClinica(pacienteActualizado.getNumeroHistoriaClinica());
                    paciente.setHabitacion(pacienteActualizado.getHabitacion());
                    return pacienteRepository.save(paciente);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el paciente con ID: " + id));
    }

    /**
     * Elimina un paciente por su ID.
     */
    @Transactional
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }
}