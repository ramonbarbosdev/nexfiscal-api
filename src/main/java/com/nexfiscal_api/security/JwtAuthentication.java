package com.nexfiscal_api.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class JwtAuthentication implements Authentication {

    private final Long idUsuario;
    private final String email;
    private final String nome;
    private final List<String> permissoes;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = true;

    public JwtAuthentication(
            Long idUsuario,
            String email,
            String nome,
            List<String> permissoes,
            Collection<? extends GrantedAuthority> authorities) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.nome = nome;
        this.permissoes = permissoes;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return idUsuario;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}
