package com.hospital.meditrack.Usuario;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UsuarioControllerTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deberiaObtenerTodosLosUsuarios() {
        Usuario u1 = new Usuario();
        u1.setNombre("Juan");
        u1.setApellidos("García López");
        u1.setUsername("jgarcia_test");
        u1.setPassword("password123");
        u1.setRol(Rol.ENFERMERIA);
        usuarioService.crear(u1);

        Usuario u2 = new Usuario();
        u2.setNombre("María");
        u2.setApellidos("Martínez Ruiz");
        u2.setUsername("mmartinez_test");
        u2.setPassword("clave456");
        u2.setRol(Rol.MEDICINA);
        usuarioService.crear(u2);

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertNotNull(usuarios);
        assertTrue(usuarios.size() >= 2);

        boolean tieneJuan = usuarios.stream().anyMatch(u -> u.getUsername().equals("jgarcia_test"));
        boolean tieneMaria = usuarios.stream().anyMatch(u -> u.getUsername().equals("mmartinez_test"));

        assertTrue(tieneJuan, "Debería existir usuario jgarcia_test");
        assertTrue(tieneMaria, "Debería existir usuario mmartinez_test");
    }

    @Test
    void deberiaObtenerUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        usuario.setApellidos("Fernández Pérez");
        usuario.setUsername("cfernandez_test");
        usuario.setPassword("pass789");
        usuario.setRol(Rol.SUPERVISOR);
        Usuario usuarioCreado = usuarioService.crear(usuario);

        Optional<Usuario> resultado = usuarioService.obtenerPorId(usuarioCreado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
        assertEquals("cfernandez_test", resultado.get().getUsername());
        assertEquals(Rol.SUPERVISOR, resultado.get().getRol());
    }

    @Test
    void deberiaRetornarVacioSiUsuarioNoExiste() {
        Optional<Usuario> resultado = usuarioService.obtenerPorId(99999L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deberiaCrearNuevoUsuarioConPasswordEncriptada() {
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre("Ana");
        usuarioNuevo.setApellidos("Sánchez Gómez");
        usuarioNuevo.setUsername("asanchez_test");
        usuarioNuevo.setPassword("miPassword");
        usuarioNuevo.setRol(Rol.ENFERMERIA);

        Usuario usuarioCreado = usuarioService.crear(usuarioNuevo);

        assertNotNull(usuarioCreado.getId());
        assertEquals("asanchez_test", usuarioCreado.getUsername());
        assertTrue(passwordEncoder.matches("miPassword", usuarioCreado.getPassword()),
                "La contraseña debe estar encriptada con BCrypt");

        Optional<Usuario> recuperado = usuarioService.obtenerPorId(usuarioCreado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("Ana", recuperado.get().getNombre());
    }

    @Test
    void noDeberiaCrearUsuarioDuplicado() {
        Usuario primerUsuario = new Usuario();
        primerUsuario.setNombre("Luis");
        primerUsuario.setApellidos("Torres Vila");
        primerUsuario.setUsername("ltorres_test");
        primerUsuario.setPassword("password");
        primerUsuario.setRol(Rol.MEDICINA);
        usuarioService.crear(primerUsuario);

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setNombre("Otro");
        usuarioDuplicado.setApellidos("Apellido");
        usuarioDuplicado.setUsername("ltorres_test");
        usuarioDuplicado.setPassword("otraPassword");
        usuarioDuplicado.setRol(Rol.ENFERMERIA);

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.crear(usuarioDuplicado);
        });
    }

    @Test
    void deberiaActualizarUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Rosa");
        usuario.setApellidos("Blanco Navarro");
        usuario.setUsername("rblanco_test");
        usuario.setPassword("password");
        usuario.setRol(Rol.ENFERMERIA);
        Usuario usuarioCreado = usuarioService.crear(usuario);

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Rosa María");
        usuarioActualizado.setApellidos("Blanco Navarro");
        usuarioActualizado.setUsername("rblanco_test");
        usuarioActualizado.setPassword("nuevaPassword");
        usuarioActualizado.setRol(Rol.SUPERVISOR);

        Usuario resultado = usuarioService.actualizar(usuarioCreado.getId(), usuarioActualizado);

        assertEquals("Rosa María", resultado.getNombre());
        assertEquals(Rol.SUPERVISOR, resultado.getRol());
        assertTrue(passwordEncoder.matches("nuevaPassword", resultado.getPassword()),
                "La nueva contraseña debe estar encriptada");
    }

    @Test
    void deberiaEliminarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Temporal");
        usuario.setApellidos("Apellido Test");
        usuario.setUsername("temporal_test");
        usuario.setPassword("password");
        usuario.setRol(Rol.ENFERMERIA);
        Usuario usuarioCreado = usuarioService.crear(usuario);
        Long idUsuario = usuarioCreado.getId();

        assertTrue(usuarioService.obtenerPorId(idUsuario).isPresent());

        usuarioService.eliminar(idUsuario);

        assertFalse(usuarioService.obtenerPorId(idUsuario).isPresent());
    }
}
