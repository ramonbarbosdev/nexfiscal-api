package com.nexfiscal_api.dto.invoice;

public record InvoiceFormDto(
        PrestadorDto prestador,
        TomadorDto tomador,
        ServicoDto servico,
        String observacoes) {
}
