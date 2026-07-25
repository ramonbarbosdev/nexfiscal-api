package com.nexfiscal_api.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.nexfiscal_api.dto.common.PartyAddressDto;
import com.nexfiscal_api.dto.invoice.InvoiceDto;
import com.nexfiscal_api.dto.invoice.InvoiceFormDto;
import com.nexfiscal_api.dto.invoice.PrestadorDto;
import com.nexfiscal_api.dto.invoice.ServicoDto;
import com.nexfiscal_api.dto.invoice.TomadorDto;
import com.nexfiscal_api.model.NotaFiscal;

public final class NotaFiscalMapper {

    private NotaFiscalMapper() {
    }

    public static InvoiceDto toDto(NotaFiscal entity) {
        return new InvoiceDto(
                entity.getIdNotaFiscal(),
                entity.getNuNumero(),
                entity.getNuSerie(),
                entity.getDsStatus(),
                toOffset(entity.getDtEmissao()),
                entity.getDsCodigoVerificacao(),
                toPrestadorDto(entity),
                toTomadorDto(entity),
                toServicoDto(entity),
                entity.getDsObservacoes());
    }

    public static void applyForm(NotaFiscal entity, InvoiceFormDto form) {
        if (form.prestador() != null) {
            applyPrestador(entity, form.prestador());
        }
        if (form.tomador() != null) {
            applyTomador(entity, form.tomador());
        }
        if (form.servico() != null) {
            applyServico(entity, form.servico());
        }
        entity.setDsObservacoes(nullToEmpty(form.observacoes()));
    }

    public static void copyFrom(NotaFiscal target, NotaFiscal source) {
        applyPrestador(target, toPrestadorDto(source));
        applyTomador(target, toTomadorDto(source));
        applyServico(target, toServicoDto(source));
        target.setDsObservacoes(source.getDsObservacoes());
    }

    private static PrestadorDto toPrestadorDto(NotaFiscal entity) {
        return new PrestadorDto(
                entity.getPrestRazaoSocial(),
                entity.getPrestNomeFantasia(),
                entity.getPrestCnpj(),
                entity.getPrestInscricaoMunicipal(),
                entity.getPrestEmail(),
                entity.getPrestTelefone(),
                new PartyAddressDto(
                        entity.getPrestLogradouro(),
                        entity.getPrestNumero(),
                        entity.getPrestComplemento(),
                        entity.getPrestBairro(),
                        entity.getPrestCidade(),
                        entity.getPrestUf(),
                        entity.getPrestCep()));
    }

    private static TomadorDto toTomadorDto(NotaFiscal entity) {
        return new TomadorDto(
                entity.getTomTipo(),
                entity.getTomNome(),
                entity.getTomCpfCnpj(),
                entity.getTomEmail(),
                entity.getTomTelefone(),
                entity.getTomInscricaoMunicipal(),
                new PartyAddressDto(
                        entity.getTomLogradouro(),
                        entity.getTomNumero(),
                        entity.getTomComplemento(),
                        entity.getTomBairro(),
                        entity.getTomCidade(),
                        entity.getTomUf(),
                        entity.getTomCep()));
    }

    private static ServicoDto toServicoDto(NotaFiscal entity) {
        return new ServicoDto(
                entity.getSrvCodigoLc116(),
                entity.getSrvDescricao(),
                entity.getSrvDiscriminacao(),
                entity.getSrvValorServico(),
                entity.getSrvAliquotaIss(),
                entity.isSrvIssRetido(),
                entity.getSrvValorDeducoes(),
                entity.getSrvDescontoIncondicionado(),
                entity.getSrvDescontoCondicionado());
    }

    private static void applyPrestador(NotaFiscal entity, PrestadorDto dto) {
        entity.setPrestRazaoSocial(nullToEmpty(dto.razaoSocial()));
        entity.setPrestNomeFantasia(nullToEmpty(dto.nomeFantasia()));
        entity.setPrestCnpj(nullToEmpty(dto.cnpj()));
        entity.setPrestInscricaoMunicipal(nullToEmpty(dto.inscricaoMunicipal()));
        entity.setPrestEmail(nullToEmpty(dto.email()));
        entity.setPrestTelefone(nullToEmpty(dto.telefone()));
        if (dto.endereco() != null) {
            entity.setPrestLogradouro(nullToEmpty(dto.endereco().logradouro()));
            entity.setPrestNumero(nullToEmpty(dto.endereco().numero()));
            entity.setPrestComplemento(nullToEmpty(dto.endereco().complemento()));
            entity.setPrestBairro(nullToEmpty(dto.endereco().bairro()));
            entity.setPrestCidade(nullToEmpty(dto.endereco().cidade()));
            entity.setPrestUf(nullToEmpty(dto.endereco().uf()));
            entity.setPrestCep(nullToEmpty(dto.endereco().cep()));
        }
    }

    private static void applyTomador(NotaFiscal entity, TomadorDto dto) {
        entity.setTomTipo(dto.tipo() != null ? dto.tipo() : "pj");
        entity.setTomNome(nullToEmpty(dto.nome()));
        entity.setTomCpfCnpj(nullToEmpty(dto.cpfCnpj()));
        entity.setTomEmail(nullToEmpty(dto.email()));
        entity.setTomTelefone(nullToEmpty(dto.telefone()));
        entity.setTomInscricaoMunicipal(nullToEmpty(dto.inscricaoMunicipal()));
        if (dto.endereco() != null) {
            entity.setTomLogradouro(nullToEmpty(dto.endereco().logradouro()));
            entity.setTomNumero(nullToEmpty(dto.endereco().numero()));
            entity.setTomComplemento(nullToEmpty(dto.endereco().complemento()));
            entity.setTomBairro(nullToEmpty(dto.endereco().bairro()));
            entity.setTomCidade(nullToEmpty(dto.endereco().cidade()));
            entity.setTomUf(nullToEmpty(dto.endereco().uf()));
            entity.setTomCep(nullToEmpty(dto.endereco().cep()));
        }
    }

    private static void applyServico(NotaFiscal entity, ServicoDto dto) {
        entity.setSrvCodigoLc116(nullToEmpty(dto.codigoLc116()));
        entity.setSrvDescricao(nullToEmpty(dto.descricao()));
        entity.setSrvDiscriminacao(nullToEmpty(dto.discriminacao()));
        if (dto.valorServico() != null) {
            entity.setSrvValorServico(dto.valorServico());
        }
        if (dto.aliquotaIss() != null) {
            entity.setSrvAliquotaIss(dto.aliquotaIss());
        }
        entity.setSrvIssRetido(dto.issRetido());
        if (dto.valorDeducoes() != null) {
            entity.setSrvValorDeducoes(dto.valorDeducoes());
        }
        if (dto.descontoIncondicionado() != null) {
            entity.setSrvDescontoIncondicionado(dto.descontoIncondicionado());
        }
        if (dto.descontoCondicionado() != null) {
            entity.setSrvDescontoCondicionado(dto.descontoCondicionado());
        }
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
