package com.hospital.meditrack.model.dto;

import com.hospital.meditrack.model.enums.Prioridad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {
    private LocalDateTime timestamp;
    private DashboardStats stats;
    private Map<String, Long> cargaPorTurno;
    private Map<Prioridad, Long> tareasPorPrioridad;
    private List<UsuarioConTareas> topEnfermeros;
}