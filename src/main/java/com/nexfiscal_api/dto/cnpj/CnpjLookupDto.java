package com.nexfiscal_api.dto.cnpj;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public record CnpjLookupDto(
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        String email,
        String telefone,
        PartyAddressDto endereco) {
}
