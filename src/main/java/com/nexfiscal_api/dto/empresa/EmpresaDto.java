package com.nexfiscal_api.dto.empresa;

import java.time.OffsetDateTime;

public record EmpresaDto(
        Long id,
        String logo,
        String nome,
        String whatsapp,
        String instagram,
        String email,
        OffsetDateTime createdAt) {
}
