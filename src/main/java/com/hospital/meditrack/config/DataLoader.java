package com.hospital.meditrack.config;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {

            Usuario enfermera = new Usuario();
            enfermera.setNombre("Ana");
            enfermera.setApellidos("García López");
            enfermera.setUsername("enfermera");
            enfermera.setPassword(passwordEncoder.encode("enfermera123"));
            enfermera.setRol(Rol.ENFERMERIA);
            usuarioRepository.save(enfermera);

            Usuario medico = new Usuario();
            medico.setNombre("Carlos");
            medico.setApellidos("Martínez Ruiz");
            medico.setUsername("medico");
            medico.setPassword(passwordEncoder.encode("medico123"));
            medico.setRol(Rol.MEDICINA);
            usuarioRepository.save(medico);

            Usuario supervisor = new Usuario();
            supervisor.setNombre("Laura");
            supervisor.setApellidos("Fernández Sánchez");
            supervisor.setUsername("supervisor");
            supervisor.setPassword(passwordEncoder.encode("supervisor123"));
            supervisor.setRol(Rol.SUPERVISOR);
            usuarioRepository.save(supervisor);

            System.out.println("Usuarios de prueba creados:");
            System.out.println("  - enfermera / enfermera123 (ENFERMERIA)");
            System.out.println("  - medico / medico123 (MEDICINA)");
            System.out.println("  - supervisor / supervisor123 (SUPERVISOR)");
        }
    }
}
