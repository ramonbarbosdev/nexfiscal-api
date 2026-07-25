package com.nexfiscal_api.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.nexfiscal_api.exception.UnauthorizedException;

@Component
public class AuthContext {

    public JwtAuthentication atual() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthentication jwt) {
            return jwt;
        }
        throw new UnauthorizedException("Usuário não autenticado");
    }
}
