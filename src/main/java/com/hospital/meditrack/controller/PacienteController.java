package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.Paciente;
import com.hospital.meditrack.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de pacientes.
 * Expone endpoints para operaciones CRUD.
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    /**
     * GET /api/pacientes
     * Obtiene todos los pacientes.
     */
    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * GET /api/pacientes/{id}
     * Obtiene un paciente por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return pacienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/pacientes/historia/{numeroHistoriaClinica}
     * Obtiene un paciente por su número de historia clínica.
     */
    @GetMapping("/historia/{numeroHistoriaClinica}")
    public ResponseEntity<Paciente> obtenerPorNumeroHistoriaClinica(
            @PathVariable String numeroHistoriaClinica) {
        return pacienteService.obtenerPorNumeroHistoriaClinica(numeroHistoriaClinica)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/pacientes
     * Crea un nuevo paciente.
     */
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        try {
            Paciente pacienteCreado = pacienteService.crear(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/pacientes/{id}
     * Actualiza un paciente existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long id,
            @RequestBody Paciente paciente) {
        try {
            Paciente pacienteActualizado = pacienteService.actualizar(id, paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/pacientes/{id}
     * Elimina un paciente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}