package com.nexfiscal_api.dto.cliente;

import java.time.OffsetDateTime;

public record ClienteDto(
        Long id,
        String nome,
        String telefone,
        OffsetDateTime createdAt) {
}
