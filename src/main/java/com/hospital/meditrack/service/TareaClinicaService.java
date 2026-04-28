package com.hospital.meditrack.service;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.repository.PacienteRepository;
import com.hospital.meditrack.repository.TareaClinicaRepository;
import com.hospital.meditrack.repository.TurnoRepository;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaClinicaService {

    @Autowired
    private TareaClinicaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerTodas() {
        return tareaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TareaClinica> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorPaciente(Long pacienteId) {
        return tareaRepository.findByPacienteId(pacienteId);
    }

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorUsuario(Long usuarioId) {
        return tareaRepository.findByAsignadoAId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorTurno(Long turnoId) {
        return tareaRepository.findByTurnoId(turnoId);
    }

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<TareaClinica> obtenerPorPrioridad(Prioridad prioridad) {
        return tareaRepository.findByPrioridad(prioridad);
    }

    @Transactional
    public TareaClinica crear(TareaClinica tarea) {
        if (tarea.getAsignadoA() == null || tarea.getAsignadoA().getId() == null) {
            throw new IllegalArgumentException("Debe asignar la tarea a un usuario");
        }
        if (tarea.getPaciente() == null || tarea.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Debe asociar la tarea a un paciente");
        }
        if (tarea.getTurno() == null || tarea.getTurno().getId() == null) {
            throw new IllegalArgumentException("Debe asociar la tarea a un turno");
        }

        if (!usuarioRepository.existsById(tarea.getAsignadoA().getId())) {
            throw new IllegalArgumentException("El usuario asignado no existe");
        }
        if (!pacienteRepository.existsById(tarea.getPaciente().getId())) {
            throw new IllegalArgumentException("El paciente no existe");
        }
        if (!turnoRepository.existsById(tarea.getTurno().getId())) {
            throw new IllegalArgumentException("El turno no existe");
        }

        return tareaRepository.save(tarea);
    }

    @Transactional
    public TareaClinica actualizar(Long id, TareaClinica tareaActualizada) {
        return tareaRepository.findById(id)
                .map(tarea -> {
                    tarea.setDescripcion(tareaActualizada.getDescripcion());
                    tarea.setTipo(tareaActualizada.getTipo());
                    tarea.setPrioridad(tareaActualizada.getPrioridad());
                    tarea.setEstado(tareaActualizada.getEstado());
                    tarea.setFecha(tareaActualizada.getFecha());
                    tarea.setObservaciones(tareaActualizada.getObservaciones());

                    if (tareaActualizada.getAsignadoA() != null) {
                        if (!usuarioRepository.existsById(tareaActualizada.getAsignadoA().getId())) {
                            throw new IllegalArgumentException("El usuario asignado no existe");
                        }
                        tarea.setAsignadoA(tareaActualizada.getAsignadoA());
                    }
                    if (tareaActualizada.getPaciente() != null) {
                        if (!pacienteRepository.existsById(tareaActualizada.getPaciente().getId())) {
                            throw new IllegalArgumentException("El paciente no existe");
                        }
                        tarea.setPaciente(tareaActualizada.getPaciente());
                    }
                    if (tareaActualizada.getTurno() != null) {
                        if (!turnoRepository.existsById(tareaActualizada.getTurno().getId())) {
                            throw new IllegalArgumentException("El turno no existe");
                        }
                        tarea.setTurno(tareaActualizada.getTurno());
                    }

                    return tareaRepository.save(tarea);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la tarea con ID: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        tareaRepository.deleteById(id);
    }
}
