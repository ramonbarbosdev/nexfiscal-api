package com.nexfiscal_api.dto.proposal;

import jakarta.validation.constraints.NotBlank;

public record ProposalStatusPatchRequest(@NotBlank String status) {
}
