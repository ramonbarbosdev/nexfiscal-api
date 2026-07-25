package com.nexfiscal_api.dto.proposal;

import java.math.BigDecimal;

public record ProposalItemDto(
        Long id,
        String desc,
        BigDecimal qtd,
        BigDecimal valor,
        Long catalogItemId) {
}
