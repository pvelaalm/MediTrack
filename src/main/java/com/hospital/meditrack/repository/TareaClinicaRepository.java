package com.hospital.meditrack.repository;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaClinicaRepository extends JpaRepository<TareaClinica, Long> {

    List<TareaClinica> findByPacienteId(Long pacienteId);

    List<TareaClinica> findByAsignadoAId(Long usuarioId);

    List<TareaClinica> findByTurnoId(Long turnoId);

    List<TareaClinica> findByEstado(EstadoTarea estado);

    List<TareaClinica> findByPrioridad(Prioridad prioridad);

    List<TareaClinica> findByAsignadoAIdAndEstado(Long usuarioId, EstadoTarea estado);

    List<TareaClinica> findByTurnoIdAndEstado(Long turnoId, EstadoTarea estado);
}
