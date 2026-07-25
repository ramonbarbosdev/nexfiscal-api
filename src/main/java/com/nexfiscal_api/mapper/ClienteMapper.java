package com.nexfiscal_api.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.nexfiscal_api.dto.cliente.ClienteDto;
import com.nexfiscal_api.dto.cliente.ClienteFormDto;
import com.nexfiscal_api.dto.proposal.ProposalClienteDto;
import com.nexfiscal_api.model.Cliente;

public final class ClienteMapper {

    private ClienteMapper() {
    }

    public static ClienteDto toDto(Cliente entity) {
        return new ClienteDto(
                entity.getIdCliente(),
                entity.getNmCliente(),
                entity.getDsTelefone(),
                entity.getDsTipo(),
                entity.getDsCpfCnpj(),
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

    public static void applyForm(Cliente entity, ClienteFormDto form) {
        entity.setNmCliente(nullToEmpty(form.nome()));
        entity.setDsTelefone(nullToEmpty(form.telefone()));
        entity.setDsTipo(normalizeTipo(form.tipo()));
        entity.setDsCpfCnpj(nullToEmpty(form.cpfCnpj()));
        AddressMapper.apply(entity, form.endereco());
    }

    public static void applyFromProposal(Cliente entity, ProposalClienteDto dto) {
        entity.setNmCliente(nullToEmpty(dto.nome()));
        entity.setDsTelefone(nullToEmpty(dto.telefone()));
    }

    public static ClienteFormDto toFormDto(ProposalClienteDto dto) {
        return new ClienteFormDto(dto.nome(), dto.telefone(), "pf", "", null);
    }

    public static ProposalClienteDto toProposalDto(Cliente entity) {
        return new ProposalClienteDto(entity.getNmCliente(), entity.getDsTelefone());
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static String normalizeTipo(String tipo) {
        return "pj".equalsIgnoreCase(nullToEmpty(tipo)) ? "pj" : "pf";
    }
}
