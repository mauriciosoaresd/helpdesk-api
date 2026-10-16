package com.mauriciosoaresd.backend_gestao_os_api.controller;

import com.mauriciosoaresd.backend_gestao_os_api.dto.LoginRequestDTO;
import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioRequestDTO;
import com.mauriciosoaresd.backend_gestao_os_api.dto.UsuarioResponseDTO;
import com.mauriciosoaresd.backend_gestao_os_api.repository.UsuarioRepository;
import com.mauriciosoaresd.backend_gestao_os_api.security.JwtTokenService;
import com.mauriciosoaresd.backend_gestao_os_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        var usuario = usuarioRepository.findByEmail(dto.email())
                .orElse(null);

        if (usuario == null || !passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Credenciais inválidas"));
        }

        var token = jwtTokenService.gerarToken(usuario.getEmail());
        return ResponseEntity.ok(Map.of("token", token, "tipo", "Bearer"));
    }
}