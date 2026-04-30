package com.hospital.meditrack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private long totalTareas;
    private long tareasPendientes;
    private long tareasEnCurso;
    private long tareasRealizadas;
    private long tareasCanceladas;
    private long totalPacientes;
    private long totalEnfermeros;
}
