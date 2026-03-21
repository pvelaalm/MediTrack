package com.hospital.meditrack.model.entity;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="turno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String nombre;
	
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;
	
	@Column(name="hora_fin", nullable = false)
	private LocalTime horaFin;
	

}
