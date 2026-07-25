package com.nexfiscal_api.dto.cep;

public record CepLookupDto(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String cidade,
        String uf) {
}
