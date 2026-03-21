package com.hospital.meditrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.meditrack.model.entity.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
	Optional<Turno> findByNombre(String nombre);

}
