package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.TareaClinica;
import com.hospital.meditrack.model.enums.EstadoTarea;
import com.hospital.meditrack.model.enums.Prioridad;
import com.hospital.meditrack.service.TareaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaClinicaController {

    @Autowired
    private TareaClinicaService tareaService;

    @GetMapping
    public ResponseEntity<List<TareaClinica>> obtenerTodas() {
        return ResponseEntity.ok(tareaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaClinica> obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(tareaService.obtenerPorPaciente(pacienteId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tareaService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/turno/{turnoId}")
    public ResponseEntity<List<TareaClinica>> obtenerPorTurno(@PathVariable Long turnoId) {
        return ResponseEntity.ok(tareaService.obtenerPorTurno(turnoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaClinica>> obtenerPorEstado(@PathVariable EstadoTarea estado) {
        return ResponseEntity.ok(tareaService.obtenerPorEstado(estado));
    }

    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<TareaClinica>> obtenerPorPrioridad(@PathVariable Prioridad prioridad) {
        return ResponseEntity.ok(tareaService.obtenerPorPrioridad(prioridad));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody TareaClinica tarea) {
        try {
            TareaClinica creada = tareaService.crear(tarea);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TareaClinica tarea) {
        try {
            TareaClinica actualizada = tareaService.actualizar(id, tarea);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
