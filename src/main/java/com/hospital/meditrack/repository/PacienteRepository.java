package com.hospital.meditrack.repository;

import com.hospital.meditrack.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestión de pacientes.
 * Spring Data JPA genera automáticamente la implementación.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Busca un paciente por su número de historia clínica.
     *
     * @param numeroHistoriaClinica El número de historia clínica único
     * @return Optional con el paciente si existe
     */
    Optional<Paciente> findByNumeroHistoriaClinica(String numeroHistoriaClinica);
}