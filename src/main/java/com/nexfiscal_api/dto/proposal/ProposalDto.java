package com.nexfiscal_api.dto.proposal;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record ProposalDto(
        Long id,
        String numero,
        String status,
        OffsetDateTime createdAt,
        Long empresaId,
        Long clienteId,
        ProposalEmpresaDto empresa,
        ProposalClienteDto cliente,
        ProposalProjetoDto projeto,
        List<ProposalItemDto> itens,
        BigDecimal desconto,
        BigDecimal entrada,
        String formaPagamento,
        String observacoes) {
}
