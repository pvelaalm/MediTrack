package com.hospital.meditrack.Usuario;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deberiaGuardarYRecuperarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidos("García López");
        usuario.setUsername("jgarcia");
        usuario.setPassword(passwordEncoder.encode("password123"));
        usuario.setRol(Rol.ENFERMERIA);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertNotNull(usuarioGuardado.getId());
        assertEquals("Juan", usuarioGuardado.getNombre());
        assertEquals("jgarcia", usuarioGuardado.getUsername());
        assertEquals(Rol.ENFERMERIA, usuarioGuardado.getRol());

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuarioGuardado.getId());

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("García López", usuarioEncontrado.get().getApellidos());
        assertTrue(passwordEncoder.matches("password123", usuarioEncontrado.get().getPassword()));
    }

    @Test
    void deberiaBuscarPorUsername() {
        Usuario usuario = new Usuario();
        usuario.setNombre("María");
        usuario.setApellidos("Martínez Ruiz");
        usuario.setUsername("mmartinez");
        usuario.setPassword(passwordEncoder.encode("clave456"));
        usuario.setRol(Rol.MEDICINA);
        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findByUsername("mmartinez");

        assertTrue(encontrado.isPresent());
        assertEquals("María", encontrado.get().getNombre());
        assertEquals(Rol.MEDICINA, encontrado.get().getRol());
    }
}
