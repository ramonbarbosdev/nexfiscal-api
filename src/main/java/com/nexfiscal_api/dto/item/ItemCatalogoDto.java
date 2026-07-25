package com.nexfiscal_api.dto.item;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ItemCatalogoDto(
        Long id,
        String tipo,
        String nome,
        String descricao,
        String codigoLc116,
        BigDecimal precoPadrao,
        BigDecimal aliquotaIss,
        boolean issRetido,
        String unidade,
        String codigoInterno,
        boolean ativo,
        OffsetDateTime createdAt) {
}
