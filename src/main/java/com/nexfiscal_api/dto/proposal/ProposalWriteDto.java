package com.nexfiscal_api.dto.proposal;

import java.math.BigDecimal;
import java.util.List;

public record ProposalWriteDto(
        ProposalEmpresaDto empresa,
        ProposalClienteDto cliente,
        ProposalProjetoDto projeto,
        List<ProposalItemDto> itens,
        BigDecimal desconto,
        BigDecimal entrada,
        String formaPagamento,
        String observacoes,
        Boolean salvarEmpresa,
        Boolean salvarCliente,
        Long empresaId,
        Long clienteId) {

    public ProposalFormDto toForm() {
        return new ProposalFormDto(empresa, cliente, projeto, itens, desconto, entrada, formaPagamento, observacoes);
    }

    public boolean deveSalvarEmpresa() {
        return Boolean.TRUE.equals(salvarEmpresa);
    }

    public boolean deveSalvarCliente() {
        return Boolean.TRUE.equals(salvarCliente);
    }
}
