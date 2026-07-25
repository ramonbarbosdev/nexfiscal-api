package com.nexfiscal_api.dto.usuario;

import java.time.OffsetDateTime;
import java.util.List;

public record UsuarioDto(
        Long id,
        String nome,
        String email,
        boolean ativo,
        List<String> papeis,
        OffsetDateTime createdAt) {
}
