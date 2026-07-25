package com.nexfiscal_api.dto.invoice;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record PrestadorDto(
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        String inscricaoMunicipal,
        String email,
        String telefone,
        PartyAddressDto endereco) {
}
