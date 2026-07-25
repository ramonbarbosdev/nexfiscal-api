package com.nexfiscal_api.dto.empresa;

import java.time.OffsetDateTime;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record EmpresaDto(
        Long id,
        String logo,
        String nome,
        String whatsapp,
        String instagram,
        String email,
        String cnpj,
        PartyAddressDto endereco,
        OffsetDateTime createdAt) {
}
