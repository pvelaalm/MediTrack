package com.hospital.meditrack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioConTareas {
    private Long usuarioId;
    private String nombreCompleto;
    private Long numeroTareas;
}