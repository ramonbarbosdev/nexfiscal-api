package com.nexfiscal_api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiErrorResponse {

    private int status;
    @JsonProperty("erro")
    private String error;
    @JsonProperty("mensagem")
    private String message;
    private List<FieldErrorDTO> details;
    private String path;
    private LocalDateTime timestamp;
    private String debugMessage;
}
