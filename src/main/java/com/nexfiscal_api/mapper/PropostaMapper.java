package com.nexfiscal_api.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.nexfiscal_api.dto.proposal.ProposalClienteDto;
import com.nexfiscal_api.dto.proposal.ProposalDto;
import com.nexfiscal_api.dto.proposal.ProposalEmpresaDto;
import com.nexfiscal_api.dto.proposal.ProposalFormDto;
import com.nexfiscal_api.dto.proposal.ProposalItemDto;
import com.nexfiscal_api.dto.proposal.ProposalProjetoDto;
import com.nexfiscal_api.model.ItemProposta;
import com.nexfiscal_api.model.Proposta;

public final class PropostaMapper {

    private PropostaMapper() {
    }

    public static ProposalDto toDto(Proposta entity) {
        return new ProposalDto(
                entity.getIdProposta(),
                entity.getNuNumero(),
                entity.getDsStatus(),
                toOffset(entity.getDtCriacao()),
                new ProposalEmpresaDto(
                        entity.getDsEmpresaLogo(),
                        entity.getNmEmpresa(),
                        entity.getDsEmpresaWhatsapp(),
                        entity.getDsEmpresaInstagram(),
                        entity.getNmEmpresaEmail()),
                new ProposalClienteDto(entity.getNmCliente(), entity.getDsClienteTelefone()),
                new ProposalProjetoDto(
                        entity.getNmProjetoTitulo(),
                        entity.getDsProjetoDescricao(),
                        entity.getDsProjetoArea(),
                        entity.getDsProjetoPrazo(),
                        entity.getDsProjetoValidade()),
                entity.getItens().stream().map(PropostaMapper::toItemDto).toList(),
                entity.getVlDesconto(),
                entity.getVlEntrada(),
                entity.getDsFormaPagamento(),
                entity.getDsObservacoes());
    }

    public static ProposalItemDto toItemDto(ItemProposta item) {
        return new ProposalItemDto(
                item.getIdItemProposta(),
                item.getDsDescricao(),
                item.getQtQuantidade(),
                item.getVlUnitario());
    }

    public static void applyForm(Proposta entity, ProposalFormDto form) {
        if (form.empresa() != null) {
            entity.setDsEmpresaLogo(nullToEmpty(form.empresa().logo()));
            entity.setNmEmpresa(nullToEmpty(form.empresa().nome()));
            entity.setDsEmpresaWhatsapp(nullToEmpty(form.empresa().whatsapp()));
            entity.setDsEmpresaInstagram(nullToEmpty(form.empresa().instagram()));
            entity.setNmEmpresaEmail(nullToEmpty(form.empresa().email()));
        }
        if (form.cliente() != null) {
            entity.setNmCliente(nullToEmpty(form.cliente().nome()));
            entity.setDsClienteTelefone(nullToEmpty(form.cliente().telefone()));
        }
        if (form.projeto() != null) {
            entity.setNmProjetoTitulo(nullToEmpty(form.projeto().titulo()));
            entity.setDsProjetoDescricao(nullToEmpty(form.projeto().descricao()));
            entity.setDsProjetoArea(nullToEmpty(form.projeto().area()));
            entity.setDsProjetoPrazo(nullToEmpty(form.projeto().prazo()));
            entity.setDsProjetoValidade(nullToEmpty(form.projeto().validade()));
        }
        entity.setVlDesconto(form.desconto() != null ? form.desconto() : entity.getVlDesconto());
        entity.setVlEntrada(form.entrada() != null ? form.entrada() : entity.getVlEntrada());
        entity.setDsFormaPagamento(nullToEmpty(form.formaPagamento()));
        entity.setDsObservacoes(nullToEmpty(form.observacoes()));
        syncItens(entity, form.itens());
    }

    private static void syncItens(Proposta entity, List<ProposalItemDto> itens) {
        entity.getItens().clear();
        if (itens == null) {
            return;
        }
        for (ProposalItemDto itemDto : itens) {
            ItemProposta item = new ItemProposta();
            item.setProposta(entity);
            item.setDsDescricao(nullToEmpty(itemDto.desc()));
            item.setQtQuantidade(itemDto.qtd() != null ? itemDto.qtd() : item.getQtQuantidade());
            item.setVlUnitario(itemDto.valor() != null ? itemDto.valor() : item.getVlUnitario());
            entity.getItens().add(item);
        }
    }

    public static void copyFrom(Proposta target, Proposta source) {
        target.setDsEmpresaLogo(source.getDsEmpresaLogo());
        target.setNmEmpresa(source.getNmEmpresa());
        target.setDsEmpresaWhatsapp(source.getDsEmpresaWhatsapp());
        target.setDsEmpresaInstagram(source.getDsEmpresaInstagram());
        target.setNmEmpresaEmail(source.getNmEmpresaEmail());
        target.setNmCliente(source.getNmCliente());
        target.setDsClienteTelefone(source.getDsClienteTelefone());
        target.setNmProjetoTitulo(source.getNmProjetoTitulo());
        target.setDsProjetoDescricao(source.getDsProjetoDescricao());
        target.setDsProjetoArea(source.getDsProjetoArea());
        target.setDsProjetoPrazo(source.getDsProjetoPrazo());
        target.setDsProjetoValidade(source.getDsProjetoValidade());
        target.setVlDesconto(source.getVlDesconto());
        target.setVlEntrada(source.getVlEntrada());
        target.setDsFormaPagamento(source.getDsFormaPagamento());
        target.setDsObservacoes(source.getDsObservacoes());
        target.getItens().clear();
        for (ItemProposta item : source.getItens()) {
            ItemProposta copy = new ItemProposta();
            copy.setProposta(target);
            copy.setDsDescricao(item.getDsDescricao());
            copy.setQtQuantidade(item.getQtQuantidade());
            copy.setVlUnitario(item.getVlUnitario());
            target.getItens().add(copy);
        }
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
