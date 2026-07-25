package com.nexfiscal_api.dto.usuario;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UsuarioFormDto(
        @NotBlank(message = "Informe o nome do usuário")
        @Size(max = 255)
        String nome,

        @NotBlank(message = "Informe o e-mail")
        @Email(message = "E-mail inválido")
        @Size(max = 255)
        String email,

        @Size(max = 100)
        String senha,

        boolean ativo,

        @NotEmpty(message = "Selecione ao menos um perfil")
        List<@NotBlank String> papeis) {
}
