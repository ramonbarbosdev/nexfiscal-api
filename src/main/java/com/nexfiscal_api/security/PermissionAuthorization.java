package com.nexfiscal_api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PermissionAuthorization {

    private final AuthContext authContext;

    public void require(String permission) {
        if (!has(permission)) {
            throw new AccessDeniedException("Sem permissão para esta ação");
        }
    }

    public boolean has(String permission) {
        return authContext.atual().getPermissoes().contains(permission);
    }

    public Long currentUserId() {
        return authContext.atual().getIdUsuario();
    }
}
