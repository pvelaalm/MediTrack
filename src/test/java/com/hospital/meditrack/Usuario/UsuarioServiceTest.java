package com.hospital.meditrack.Usuario;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import com.hospital.meditrack.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deberiaObtenerTodos() {
        Usuario u1 = new Usuario(1L, "Juan", "García", "jgarcia", "$2a$hash1", Rol.ENFERMERIA);
        Usuario u2 = new Usuario(2L, "María", "Martínez", "mmartinez", "$2a$hash2", Rol.MEDICINA);
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> resultado = usuarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void deberiaObtenerPorId() {
        Usuario usuario = new Usuario(1L, "Juan", "García", "jgarcia", "$2a$hash1", Rol.ENFERMERIA);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("jgarcia", resultado.get().getUsername());
        assertEquals(Rol.ENFERMERIA, resultado.get().getRol());
    }

    @Test
    void deberiaCrearUsuarioConPasswordEncriptada() {
        Usuario usuario = new Usuario(null, "Pedro", "López", "plopez", "clave123", Rol.SUPERVISOR);
        Usuario usuarioGuardado = new Usuario(1L, "Pedro", "López", "plopez", "$2a$10$hashedclave", Rol.SUPERVISOR);

        when(usuarioRepository.findByUsername("plopez")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("clave123")).thenReturn("$2a$10$hashedclave");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        Usuario resultado = usuarioService.crear(usuario);

        assertNotNull(resultado.getId());
        assertEquals("plopez", resultado.getUsername());
        assertEquals("$2a$10$hashedclave", resultado.getPassword());
        verify(passwordEncoder, times(1)).encode("clave123");
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void noDeberiaCrearUsuarioDuplicado() {
        Usuario usuarioExistente = new Usuario(1L, "Juan", "García", "jgarcia", "$2a$hash", Rol.ENFERMERIA);
        Usuario usuarioNuevo = new Usuario(null, "Otro", "Nombre", "jgarcia", "pass", Rol.MEDICINA);

        when(usuarioRepository.findByUsername("jgarcia")).thenReturn(Optional.of(usuarioExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.crear(usuarioNuevo)
        );

        assertTrue(exception.getMessage().contains("Ya existe un usuario"));
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
