package com.nexfiscal_api.dto.proposal;

public record ProposalProjetoDto(
        String titulo,
        String descricao,
        String area,
        String prazo,
        String validade) {
}
