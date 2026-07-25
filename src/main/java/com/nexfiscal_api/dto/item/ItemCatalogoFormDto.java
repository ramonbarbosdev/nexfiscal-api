package com.nexfiscal_api.dto.item;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ItemCatalogoFormDto(
        @NotBlank @Pattern(regexp = "produto|servico") String tipo,
        @NotBlank String nome,
        String descricao,
        String codigoLc116,
        BigDecimal precoPadrao,
        BigDecimal aliquotaIss,
        Boolean issRetido,
        String unidade,
        String codigoInterno,
        Boolean ativo) {
}
