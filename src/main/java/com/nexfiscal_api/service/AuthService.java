package com.nexfiscal_api.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.auth.LoginRequest;
import com.nexfiscal_api.dto.auth.LoginResponse;
import com.nexfiscal_api.dto.auth.MeResponse;
import com.nexfiscal_api.exception.UnauthorizedException;
import com.nexfiscal_api.model.Usuario;
import com.nexfiscal_api.repository.UsuarioRepository;
import com.nexfiscal_api.security.AuthContext;
import com.nexfiscal_api.security.JwtAuthentication;
import com.nexfiscal_api.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String CREDENCIAIS_INVALIDAS = "E-mail ou senha inválidos";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthContext authContext;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByNmEmailIgnoreCaseAndFlAtivoTrue(request.email())
                .filter(u -> passwordEncoder.matches(request.password(), u.getDsSenha()))
                .orElseThrow(() -> new UnauthorizedException(CREDENCIAIS_INVALIDAS));

        List<String> permissoes = usuarioRepository.listarPermissoes(usuario.getIdUsuario());
        String token = jwtService.gerarToken(usuario.getIdUsuario(), usuario.getNmEmail(), permissoes);
        return new LoginResponse(token);
    }

    @Transactional(readOnly = true)
    public MeResponse me() {
        JwtAuthentication atual = authContext.atual();
        Usuario usuario = usuarioRepository.findById(atual.getIdUsuario())
                .filter(Usuario::isFlAtivo)
                .orElseThrow(() -> new UnauthorizedException("Usuário autenticado não encontrado"));

        return new MeResponse(
                usuario.getIdUsuario(),
                usuario.getNmEmail(),
                usuario.getNmUsuario(),
                atual.getPermissoes());
    }
}
