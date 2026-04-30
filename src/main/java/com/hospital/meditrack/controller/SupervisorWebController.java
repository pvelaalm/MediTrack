package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.TurnoService;
import com.hospital.meditrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supervisor")
public class SupervisorWebController {

    @Autowired
    private TareaClinicaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TurnoService turnoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalTareas", tareaService.obtenerTodas().size());
        model.addAttribute("totalUsuarios", usuarioService.obtenerTodos().size());
        model.addAttribute("totalTurnos", turnoService.obtenerTodos().size());
        return "supervisor/dashboard";
    }

    @GetMapping("/carga-trabajo")
    public String cargaTrabajo(Model model) {
        List<Turno> turnos = turnoService.obtenerTodos();
        Map<Turno, Long> cargaPorTurno = new LinkedHashMap<>();
        for (Turno turno : turnos) {
            long count = tareaService.obtenerPorTurno(turno.getId()).size();
            cargaPorTurno.put(turno, count);
        }
        model.addAttribute("cargaPorTurno", cargaPorTurno);
        return "supervisor/carga-trabajo";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "supervisor/usuarios";
    }

    @GetMapping("/reasignar-tareas")
    public String reasignarTareas(Model model) {
        model.addAttribute("tareas", tareaService.obtenerTodas());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "supervisor/reasignar-tareas";
    }

    @GetMapping("/dashboard-realtime")
    public String dashboardRealtime() {
        return "supervisor/dashboard-realtime";
    }

    @PostMapping("/reasignar-tarea/{id}")
    public String reasignarTarea(
            @PathVariable Long id,
            @RequestParam Long nuevoUsuarioId) {

        TareaClinica existente = tareaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada: " + id));
        Usuario nuevoUsuario = usuarioService.obtenerPorId(nuevoUsuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + nuevoUsuarioId));

        TareaClinica actualizada = new TareaClinica();
        actualizada.setDescripcion(existente.getDescripcion());
        actualizada.setTipo(existente.getTipo());
        actualizada.setPrioridad(existente.getPrioridad());
        actualizada.setEstado(existente.getEstado());
        actualizada.setFecha(existente.getFecha());
        actualizada.setObservaciones(existente.getObservaciones());
        actualizada.setAsignadoA(nuevoUsuario);
        actualizada.setPaciente(existente.getPaciente());
        actualizada.setTurno(existente.getTurno());

        tareaService.actualizar(id, actualizada);
        return "redirect:/supervisor/reasignar-tareas";
    }
}
