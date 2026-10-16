package com.mauriciosoaresd.backend_gestao_os_api.service;

import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioRequestDTO;
import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioResponseDTO;
import com.mauriciosoaresd.backend_gestao_os_api.model.Usuario;
import com.mauriciosoaresd.backend_gestao_os_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha())) // Criptografia BCrypt
                .perfil(dto.perfil())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        return UsuarioResponseDTO.fromEntity(usuarioSalvo);
    }
}