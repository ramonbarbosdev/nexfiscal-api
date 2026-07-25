package com.nexfiscal_api.mapper;

import com.nexfiscal_api.dto.common.PartyAddressDto;

public final class AddressMapper {

    private AddressMapper() {
    }

    public static PartyAddressDto toDto(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String cep) {
        return new PartyAddressDto(
                nullToEmpty(logradouro),
                nullToEmpty(numero),
                nullToEmpty(complemento),
                nullToEmpty(bairro),
                nullToEmpty(cidade),
                nullToEmpty(uf),
                nullToEmpty(cep));
    }

    public static void apply(
            AddressWritable target,
            PartyAddressDto endereco) {
        if (endereco == null) {
            return;
        }
        target.setLogradouro(nullToEmpty(endereco.logradouro()));
        target.setNumero(nullToEmpty(endereco.numero()));
        target.setComplemento(nullToEmpty(endereco.complemento()));
        target.setBairro(nullToEmpty(endereco.bairro()));
        target.setCidade(nullToEmpty(endereco.cidade()));
        target.setUf(nullToEmpty(endereco.uf()));
        target.setCep(nullToEmpty(endereco.cep()));
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    public interface AddressWritable {
        void setLogradouro(String value);

        void setNumero(String value);

        void setComplemento(String value);

        void setBairro(String value);

        void setCidade(String value);

        void setUf(String value);

        void setCep(String value);
    }
}
