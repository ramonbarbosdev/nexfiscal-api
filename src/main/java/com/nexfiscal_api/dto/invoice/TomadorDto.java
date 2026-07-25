package com.nexfiscal_api.dto.invoice;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record TomadorDto(
        String tipo,
        String nome,
        String cpfCnpj,
        String email,
        String telefone,
        String inscricaoMunicipal,
        PartyAddressDto endereco) {
}
