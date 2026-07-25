package com.nexfiscal_api.dto.cliente;

import java.time.OffsetDateTime;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record ClienteDto(
        Long id,
        String nome,
        String telefone,
        PartyAddressDto endereco,
        OffsetDateTime createdAt) {
}
