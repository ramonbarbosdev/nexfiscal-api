package com.nexfiscal_api.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.nexfiscal_api.dto.empresa.EmpresaDto;
import com.nexfiscal_api.dto.empresa.EmpresaFormDto;
import com.nexfiscal_api.dto.proposal.ProposalEmpresaDto;
import com.nexfiscal_api.model.Empresa;

public final class EmpresaMapper {

    private EmpresaMapper() {
    }

    public static EmpresaDto toDto(Empresa entity) {
        return new EmpresaDto(
                entity.getIdEmpresa(),
                entity.getDsLogo(),
                entity.getNmEmpresa(),
                entity.getDsWhatsapp(),
                entity.getDsInstagram(),
                entity.getNmEmail(),
                entity.getDsCnpj(),
                AddressMapper.toDto(
                        entity.getLogradouro(),
                        entity.getNumero(),
                        entity.getComplemento(),
                        entity.getBairro(),
                        entity.getCidade(),
                        entity.getUf(),
                        entity.getCep()),
                toOffset(entity.getDtCriacao()));
    }

    public static void applyForm(Empresa entity, EmpresaFormDto form) {
        entity.setDsLogo(nullToEmpty(form.logo()));
        entity.setNmEmpresa(nullToEmpty(form.nome()));
        entity.setDsWhatsapp(nullToEmpty(form.whatsapp()));
        entity.setDsInstagram(nullToEmpty(form.instagram()));
        entity.setNmEmail(nullToEmpty(form.email()));
        entity.setDsCnpj(nullToEmpty(form.cnpj()));
        AddressMapper.apply(entity, form.endereco());
    }

    public static void applyFromProposal(Empresa entity, ProposalEmpresaDto dto) {
        entity.setDsLogo(nullToEmpty(dto.logo()));
        entity.setNmEmpresa(nullToEmpty(dto.nome()));
        entity.setDsWhatsapp(nullToEmpty(dto.whatsapp()));
        entity.setDsInstagram(nullToEmpty(dto.instagram()));
        entity.setNmEmail(nullToEmpty(dto.email()));
    }

    public static EmpresaFormDto toFormDto(ProposalEmpresaDto dto) {
        return new EmpresaFormDto(dto.logo(), dto.nome(), dto.whatsapp(), dto.instagram(), dto.email(), "", null);
    }

    public static ProposalEmpresaDto toProposalDto(Empresa entity) {
        return new ProposalEmpresaDto(
                entity.getDsLogo(),
                entity.getNmEmpresa(),
                entity.getDsWhatsapp(),
                entity.getDsInstagram(),
                entity.getNmEmail());
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
