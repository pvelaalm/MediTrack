package com.hospital.meditrack.service;

import com.hospital.meditrack.model.dto.DashboardStats;
import com.hospital.meditrack.model.dto.UsuarioConTareas;
import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.model.enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private TareaClinicaService tareaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TurnoService turnoService;

    @Transactional(readOnly = true)
    public DashboardStats obtenerEstadisticasGenerales() {
        List<TareaClinica> todas = tareaService.obtenerTodas();

        long totalTareas = todas.size();
        long pendientes = todas.stream().filter(t -> t.getEstado() == EstadoTarea.PENDIENTE).count();
        long enCurso = todas.stream().filter(t -> t.getEstado() == EstadoTarea.EN_CURSO).count();
        long realizadas = todas.stream().filter(t -> t.getEstado() == EstadoTarea.REALIZADA).count();
        long canceladas = todas.stream().filter(t -> t.getEstado() == EstadoTarea.CANCELADA).count();
        long totalPacientes = pacienteService.obtenerTodos().size();
        long totalEnfermeros = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getRol() == Rol.ENFERMERIA)
                .count();

        return new DashboardStats(totalTareas, pendientes, enCurso, realizadas, canceladas,
                totalPacientes, totalEnfermeros);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> obtenerCargaPorTurno() {
        List<Turno> turnos = turnoService.obtenerTodos();
        Map<String, Long> carga = new LinkedHashMap<>();
        for (Turno turno : turnos) {
            long count = tareaService.obtenerPorTurno(turno.getId()).size();
            carga.put(turno.getNombre(), count);
        }
        return carga;
    }

    @Transactional(readOnly = true)
    public Map<Prioridad, Long> obtenerTareasPorPrioridad() {
        List<TareaClinica> todas = tareaService.obtenerTodas();
        Map<Prioridad, Long> resultado = new LinkedHashMap<>();
        for (Prioridad p : Prioridad.values()) {
            long count = todas.stream().filter(t -> t.getPrioridad() == p).count();
            resultado.put(p, count);
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public List<UsuarioConTareas> obtenerTop5EnfermerosConMasTareas() {
        List<Usuario> enfermeros = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getRol() == Rol.ENFERMERIA)
                .collect(Collectors.toList());

        return enfermeros.stream()
                .map(u -> {
                    long numTareas = tareaService.obtenerPorUsuario(u.getId()).size();
                    String nombreCompleto = u.getNombre() + " " + u.getApellidos();
                    return new UsuarioConTareas(u.getId(), nombreCompleto, numTareas);
                })
                .sorted(Comparator.comparingLong(UsuarioConTareas::getNumeroTareas).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}