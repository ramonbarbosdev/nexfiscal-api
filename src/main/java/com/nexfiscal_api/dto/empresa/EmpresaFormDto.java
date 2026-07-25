package com.nexfiscal_api.dto.empresa;

import com.nexfiscal_api.dto.common.PartyAddressDto;

import jakarta.validation.constraints.NotBlank;

public record EmpresaFormDto(
        String logo,
        @NotBlank String nome,
        String whatsapp,
        String instagram,
        String email,
        PartyAddressDto endereco) {
}
