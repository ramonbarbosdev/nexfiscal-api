package com.nexfiscal_api.dto.common;

public record PartyAddressDto(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep) {
}
