package com.nexfiscal_api.mapper;

import com.nexfiscal_api.dto.common.PartyAddressDto;
import com.nexfiscal_api.dto.config.PrestadorConfigDto;
import com.nexfiscal_api.model.PrestadorConfig;

public final class PrestadorConfigMapper {

    private PrestadorConfigMapper() {
    }

    public static PrestadorConfigDto toDto(PrestadorConfig entity) {
        return new PrestadorConfigDto(
                entity.getRazaoSocial(),
                entity.getNomeFantasia(),
                entity.getCnpj(),
                entity.getInscricaoMunicipal(),
                entity.getEmail(),
                entity.getTelefone(),
                new PartyAddressDto(
                        entity.getLogradouro(),
                        entity.getNumero(),
                        entity.getComplemento(),
                        entity.getBairro(),
                        entity.getCidade(),
                        entity.getUf(),
                        entity.getCep()));
    }

    public static void apply(PrestadorConfig entity, PrestadorConfigDto dto) {
        if (dto == null) {
            return;
        }
        entity.setRazaoSocial(nullToEmpty(dto.razaoSocial()));
        entity.setNomeFantasia(nullToEmpty(dto.nomeFantasia()));
        entity.setCnpj(nullToEmpty(dto.cnpj()));
        entity.setInscricaoMunicipal(nullToEmpty(dto.inscricaoMunicipal()));
        entity.setEmail(nullToEmpty(dto.email()));
        entity.setTelefone(nullToEmpty(dto.telefone()));
        if (dto.endereco() != null) {
            entity.setLogradouro(nullToEmpty(dto.endereco().logradouro()));
            entity.setNumero(nullToEmpty(dto.endereco().numero()));
            entity.setComplemento(nullToEmpty(dto.endereco().complemento()));
            entity.setBairro(nullToEmpty(dto.endereco().bairro()));
            entity.setCidade(nullToEmpty(dto.endereco().cidade()));
            entity.setUf(nullToEmpty(dto.endereco().uf()));
            entity.setCep(nullToEmpty(dto.endereco().cep()));
        }
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
