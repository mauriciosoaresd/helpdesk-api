package com.mauriciosoaresd.backend_gestao_os_api.service;

import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioRequestDTO;
import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioResponseDTO;
import com.mauriciosoaresd.backend_gestao_os_api.model.Perfil;
import com.mauriciosoaresd.backend_gestao_os_api.model.Usuario;
import com.mauriciosoaresd.backend_gestao_os_api.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso quando o e-mail não estiver em uso")
    void cadastrarUsuarioComSucesso() {
        // Arrange (Preparação)
        var requestDTO = new UsuarioRequestDTO(
                "João Silva",
                "joao@email.com",
                "123456",
                Perfil.CLIENTE
        );

        when(usuarioRepository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(passwordEncoder.encode(requestDTO.email())).thenReturn("senhaCriptografada123");

        var usuarioSalvo = Usuario.builder()
                .id(1L)
                .nome(requestDTO.nome())
                .email(requestDTO.email())
                .senha("senhaCriptografada123")
                .perfil(requestDTO.perfil())
                .criadoEm(LocalDateTime.now())
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        // Act (Ação)
        UsuarioResponseDTO resultado = usuarioService.cadastrarUsuario(requestDTO);

        // Assert (Verificação)
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("João Silva", resultado.nome());
        assertEquals("joao@email.com", resultado.email());
        assertEquals(Perfil.CLIENTE, resultado.perfil());

        verify(usuarioRepository, times(1)).existsByEmail(requestDTO.email());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com e-mail já existente")
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        var requestDTO = new UsuarioRequestDTO(
                "Maria Souza",
                "maria@email.com",
                "123456",
                Perfil.TECNICO
        );

        when(usuarioRepository.existsByEmail(requestDTO.email())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.cadastrarUsuario(requestDTO)
        );

        assertEquals("E-mail já cadastrado no sistema.", exception.getMessage());
        verify(usuarioRepository, times(1)).existsByEmail(requestDTO.email());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}