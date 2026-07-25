package com.nexfiscal_api.dto.auth;

import java.util.List;

public record MeResponse(
        Long id,
        String email,
        String nome,
        List<String> permissoes) {
}
