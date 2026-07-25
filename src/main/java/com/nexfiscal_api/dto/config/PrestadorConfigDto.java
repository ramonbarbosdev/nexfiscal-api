package com.nexfiscal_api.dto.config;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record PrestadorConfigDto(
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        String inscricaoMunicipal,
        String email,
        String telefone,
        PartyAddressDto endereco) {
}
