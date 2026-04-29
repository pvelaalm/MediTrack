package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enfermeria")
public class EnfermeriaWebController {

    @Autowired
    private TareaClinicaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String username = auth.getName();
        Usuario usuario = usuarioService.obtenerPorUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));
        List<TareaClinica> tareas = tareaService.obtenerPorUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas.stream().limit(5).toList());
        model.addAttribute("totalTareas", tareas.size());
        model.addAttribute("tareasPendientes", tareas.stream()
                .filter(t -> t.getEstado() == EstadoTarea.PENDIENTE).count());
        model.addAttribute("tareasEnCurso", tareas.stream()
                .filter(t -> t.getEstado() == EstadoTarea.EN_CURSO).count());
        return "enfermeria/dashboard";
    }

    @GetMapping("/mis-tareas")
    public String misTareas(Authentication auth, Model model) {
        String username = auth.getName();
        Usuario usuario = usuarioService.obtenerPorUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));
        List<TareaClinica> tareas = tareaService.obtenerPorUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas);
        model.addAttribute("estados", EstadoTarea.values());
        return "enfermeria/mis-tareas";
    }

    @PostMapping("/tareas/{id}/actualizar-estado")
    public String actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestParam(required = false) String observaciones) {

        TareaClinica existente = tareaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada: " + id));

        TareaClinica actualizada = new TareaClinica();
        actualizada.setDescripcion(existente.getDescripcion());
        actualizada.setTipo(existente.getTipo());
        actualizada.setPrioridad(existente.getPrioridad());
        actualizada.setEstado(EstadoTarea.valueOf(nuevoEstado));
        actualizada.setFecha(existente.getFecha());
        actualizada.setObservaciones(
                (observaciones != null && !observaciones.isBlank())
                        ? observaciones
                        : existente.getObservaciones()
        );
        actualizada.setAsignadoA(existente.getAsignadoA());
        actualizada.setPaciente(existente.getPaciente());
        actualizada.setTurno(existente.getTurno());

        tareaService.actualizar(id, actualizada);
        return "redirect:/enfermeria/mis-tareas";
    }
}
