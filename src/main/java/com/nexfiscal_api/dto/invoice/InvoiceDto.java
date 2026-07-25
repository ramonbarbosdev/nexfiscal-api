package com.nexfiscal_api.dto.invoice;

import java.time.OffsetDateTime;

public record InvoiceDto(
        Long id,
        String numero,
        String serie,
        String status,
        OffsetDateTime dataEmissao,
        String codigoVerificacao,
        PrestadorDto prestador,
        TomadorDto tomador,
        ServicoDto servico,
        String observacoes) {
}
