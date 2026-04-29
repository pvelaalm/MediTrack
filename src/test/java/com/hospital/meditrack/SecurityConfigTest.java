package com.hospital.meditrack;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.model.enums.Rol;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityConfigTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        if (usuarioRepository.findByUsername("testuser").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Test");
            usuario.setApellidos("User");
            usuario.setUsername("testuser");
            usuario.setPassword(passwordEncoder.encode("password"));
            usuario.setRol(Rol.SUPERVISOR);
            usuarioRepository.save(usuario);
        }
    }

    @Test
    void deberiaDenegarAccesoSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaDeberiaVerTurnos() throws Exception {
        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaCrearTurnos() throws Exception {
        mockMvc.perform(post("/api/turnos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "medico", roles = "MEDICINA")
    void medicinaDeberiaCrearPacientes() throws Exception {
        mockMvc.perform(post("/api/pacientes"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaCrearPacientes() throws Exception {
        mockMvc.perform(post("/api/pacientes"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "supervisor", roles = "SUPERVISOR")
    void supervisorDeberiaTenerAccesoCompleto() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enfermera", roles = "ENFERMERIA")
    void enfermeriaNoDeberiaAccederAUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "medico", roles = "MEDICINA")
    void medicinaNoDeberiaAccederAUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isForbidden());
    }
}
