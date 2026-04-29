package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.model.enums.TipoTarea;
import com.hospital.meditrack.repository.UsuarioRepository;
import com.hospital.meditrack.service.PacienteService;
import com.hospital.meditrack.service.TareaClinicaService;
import com.hospital.meditrack.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/medicina")
public class MedicinaWebController {

    @Autowired
    private TareaClinicaService tareaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TareaClinica> tareas = tareaService.obtenerTodas();
        List<Paciente> pacientes = pacienteService.obtenerTodos();

        model.addAttribute("totalTareas", tareas.size());
        model.addAttribute("totalPacientes", pacientes.size());
        model.addAttribute("tareasPendientes", tareas.stream()
                .filter(t -> t.getEstado() == EstadoTarea.PENDIENTE).count());
        model.addAttribute("tareasRecientes", tareas.stream()
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .limit(5).toList());
        return "medicina/dashboard";
    }

    @GetMapping("/crear-tarea")
    public String mostrarFormularioCrearTarea(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        model.addAttribute("enfermeros", usuarioRepository.findByRol(Rol.ENFERMERIA));
        model.addAttribute("turnos", turnoService.obtenerTodos());
        model.addAttribute("tiposTarea", TipoTarea.values());
        model.addAttribute("prioridades", Prioridad.values());
        model.addAttribute("estadosTarea", EstadoTarea.values());
        return "medicina/crear-tarea";
    }

    @PostMapping("/crear-tarea")
    public String crearTarea(
            @RequestParam String descripcion,
            @RequestParam TipoTarea tipo,
            @RequestParam Prioridad prioridad,
            @RequestParam EstadoTarea estado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam(required = false) String observaciones,
            @RequestParam Long pacienteId,
            @RequestParam Long usuarioId,
            @RequestParam Long turnoId) {

        TareaClinica tarea = new TareaClinica();
        tarea.setDescripcion(descripcion);
        tarea.setTipo(tipo);
        tarea.setPrioridad(prioridad);
        tarea.setEstado(estado);
        tarea.setFecha(fecha);
        tarea.setObservaciones(observaciones);

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        tarea.setPaciente(paciente);

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        tarea.setAsignadoA(usuario);

        Turno turno = new Turno();
        turno.setId(turnoId);
        tarea.setTurno(turno);

        tareaService.crear(tarea);
        return "redirect:/medicina/dashboard";
    }

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        return "medicina/pacientes";
    }

    @GetMapping("/crear-paciente")
    public String mostrarFormularioCrearPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "medicina/crear-paciente";
    }

    @PostMapping("/crear-paciente")
    public String crearPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.crear(paciente);
        return "redirect:/medicina/pacientes";
    }
}
