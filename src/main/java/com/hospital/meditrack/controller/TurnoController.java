package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.entity.Turno;
import com.hospital.meditrack.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de turnos.
 *
 * Endpoints disponibles:
 * - GET    /api/turnos          → Listar todos los turnos
 * - GET    /api/turnos/{id}     → Obtener un turno por ID
 * - GET    /api/turnos/nombre/{nombre} → Obtener turno por nombre
 * - POST   /api/turnos          → Crear un nuevo turno
 * - PUT    /api/turnos/{id}     → Actualizar un turno
 * - DELETE /api/turnos/{id}     → Eliminar un turno
 */
@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    /**
     * GET /api/turnos
     * Obtener todos los turnos.
     *
     * @return Lista de turnos en formato JSON
     */
    @GetMapping
    public ResponseEntity<List<Turno>> obtenerTodos() {
        List<Turno> turnos = turnoService.obtenerTodos();
        return ResponseEntity.ok(turnos);
    }

    /**
     * GET /api/turnos/{id}
     * Obtener un turno por su ID.
     *
     * @param id ID del turno
     * @return Turno encontrado o 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerPorId(@PathVariable Long id) {
        Optional<Turno> turno = turnoService.obtenerPorId(id);

        return turno
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/turnos/nombre/{nombre}
     * Obtener un turno por su nombre.
     *
     * @param nombre Nombre del turno (Mañana, Tarde, Noche)
     * @return Turno encontrado o 404 Not Found
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Turno> obtenerPorNombre(@PathVariable String nombre) {
        Optional<Turno> turno = turnoService.obtenerPorNombre(nombre);

        return turno
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/turnos
     * Crear un nuevo turno.
     *
     * @param turno Datos del turno en el body de la petición (JSON)
     * @return Turno creado con código 201 Created
     */
    @PostMapping
    public ResponseEntity<Turno> crear(@RequestBody Turno turno) {
        try {
            Turno turnoCreado = turnoService.crear(turno);
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/turnos/{id}
     * Actualizar un turno existente.
     *
     * @param id ID del turno a actualizar
     * @param turno Nuevos datos del turno
     * @return Turno actualizado o 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizar(
            @PathVariable Long id,
            @RequestBody Turno turno
    ) {
        try {
            Turno turnoActualizado = turnoService.actualizar(id, turno);
            return ResponseEntity.ok(turnoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/turnos/{id}
     * Eliminar un turno.
     *
     * @param id ID del turno a eliminar
     * @return 204 No Content si se eliminó, 404 Not Found si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            turnoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}