package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.TurnoService;
import com.hospital.meditrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("roles", Rol.values());
        return "supervisor/usuarios";
    }

    @PostMapping("/usuarios/crear")
    public String crearUsuario(@RequestParam String nombre,
                               @RequestParam String apellidos,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam Rol rol,
                               RedirectAttributes redirectAttrs) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuarioService.crear(usuario);
            redirectAttrs.addFlashAttribute("mensajeExito", "Usuario creado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/supervisor/usuarios";
    }

    @PostMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id,
                                @RequestParam String nombre,
                                @RequestParam String apellidos,
                                @RequestParam String username,
                                @RequestParam(required = false) String password,
                                @RequestParam Rol rol,
                                RedirectAttributes redirectAttrs) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuarioService.actualizar(id, usuario);
            redirectAttrs.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/supervisor/usuarios";
    }

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            usuarioService.eliminar(id);
            redirectAttrs.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "No se pudo eliminar el usuario.");
        }
        return "redirect:/supervisor/usuarios";
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
