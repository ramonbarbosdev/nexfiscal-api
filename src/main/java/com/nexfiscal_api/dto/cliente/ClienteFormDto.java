package com.nexfiscal_api.dto.cliente;

import com.nexfiscal_api.dto.common.PartyAddressDto;

import jakarta.validation.constraints.NotBlank;

public record ClienteFormDto(
        @NotBlank String nome,
        String telefone,
        String tipo,
        String cpfCnpj,
        PartyAddressDto endereco) {
}
