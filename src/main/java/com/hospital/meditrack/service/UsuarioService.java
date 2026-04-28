package com.hospital.meditrack.service;

import com.hospital.meditrack.model.entity.Usuario;
import com.hospital.meditrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con el username: " + usuario.getUsername());
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setApellidos(usuarioActualizado.getApellidos());
                    usuario.setUsername(usuarioActualizado.getUsername());
                    if (usuarioActualizado.getPassword() != null
                            && !usuarioActualizado.getPassword().isBlank()) {
                        usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
                    }
                    usuario.setRol(usuarioActualizado.getRol());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el usuario con ID: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
