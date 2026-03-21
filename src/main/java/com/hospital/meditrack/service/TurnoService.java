package com.hospital.meditrack.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.meditrack.repository.TurnoRepository;
import com.hospital.meditrack.model.entity.Turno;


@Service
public class TurnoService {
	@Autowired
	private TurnoRepository turnoRepository;
	
	//Obtener todos los turnos
	@Transactional(readOnly = true)
	public List<Turno> obtenerTodos(){
		
		return turnoRepository.findAll();
	}
	
	//Buscar turno por Id
	@Transactional(readOnly = true)
	public Optional<Turno> obtenerPorId(Long id){
		return turnoRepository.findById(id);
	}
	
	//Buscar turno por su nombre
	@Transactional(readOnly = true)
	public Optional<Turno> obtenerPorNombre(String nombre){
		return turnoRepository.findByNombre(nombre);
	}
	
	//Crear un turno
	@Transactional
	public Turno crear(Turno turno) {
		//Validar que el nombre no existe
		if(turnoRepository.findByNombre(turno.getNombre()).isPresent()) {
			throw new IllegalArgumentException("Ya existe un turno con el nombre " + turno.getNombre());
		}
		
		return turnoRepository.save(turno);
	}
	
	//Actualizar un turno ya existente
	@Transactional
	public Turno actualizar(Long id, Turno turnoActualizado) {
		Turno turnoExistente = turnoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(
				"No existe un turno con el ID: " + id
				));
		//Actualizar campos
		turnoExistente.setNombre(turnoActualizado.getNombre());
		turnoExistente.setHoraInicio(turnoActualizado.getHoraInicio());
		turnoExistente.setHoraFin(turnoActualizado.getHoraFin());
		
		return turnoRepository.save(turnoExistente);
	}
	
	//Eliminar un turno por su ID
	@Transactional
	public void eliminar(Long id) {
		if(!turnoRepository.existsById(id)) {
			throw new IllegalArgumentException(
					"No existe un turno con ID" + id
					);
		}
		turnoRepository.deleteById(id);
	} 

}
