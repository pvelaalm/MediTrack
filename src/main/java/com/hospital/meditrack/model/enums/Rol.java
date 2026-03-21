package com.hospital.meditrack.model.enums;

// Roles de usuario en el sistema MediTrack.  
//Define los tres perfiles de acceso diferenciados.

 
 
public enum Rol {

	//Puede: consultar tareas asignadas, actualizar estados, añadir observaciones.
	ENFERMERIA,
	//Puede: Crear tareas, asignarlas a enfermeria, consultar seguimiento.
	MEDICINA,
	//Puede: Visualizar carga de trabajo,reasignar tareas, consultar metricas.
	SUPERVISOR
	
}
