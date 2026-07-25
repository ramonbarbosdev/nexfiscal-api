package com.nexfiscal_api.dto.invoice;

import jakarta.validation.constraints.NotBlank;

public record InvoiceStatusPatchRequest(@NotBlank String status) {
}
