package com.nexfiscal_api.dto.invoice;

import java.math.BigDecimal;

public record ServicoDto(
        String codigoLc116,
        String descricao,
        String discriminacao,
        BigDecimal valorServico,
        BigDecimal aliquotaIss,
        boolean issRetido,
        BigDecimal valorDeducoes,
        BigDecimal descontoIncondicionado,
        BigDecimal descontoCondicionado) {
}
