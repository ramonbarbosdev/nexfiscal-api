package com.nexfiscal_api.dto.cliente;

import jakarta.validation.constraints.NotBlank;

public record ClienteFormDto(
        @NotBlank String nome,
        String telefone) {
}
