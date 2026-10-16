package com.mauriciosoaresd.backend_gestao_os_api.dto;

import com.mauriciosoaresd.backend_gestao_os_api.model.Perfil;
import com.mauriciosoaresd.backend_gestao_os_api.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Perfil perfil,
        LocalDateTime criadoEm
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getCriadoEm()
        );
    }
}