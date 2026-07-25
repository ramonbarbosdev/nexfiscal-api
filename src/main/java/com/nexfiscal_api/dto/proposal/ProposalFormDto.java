package com.nexfiscal_api.dto.proposal;

import java.math.BigDecimal;
import java.util.List;

public record ProposalFormDto(
        ProposalEmpresaDto empresa,
        ProposalClienteDto cliente,
        ProposalProjetoDto projeto,
        List<ProposalItemDto> itens,
        BigDecimal desconto,
        BigDecimal entrada,
        String formaPagamento,
        String observacoes) {
}
