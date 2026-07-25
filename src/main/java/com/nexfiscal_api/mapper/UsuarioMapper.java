package com.nexfiscal_api.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.nexfiscal_api.dto.usuario.PapelDto;
import com.nexfiscal_api.dto.usuario.UsuarioDto;
import com.nexfiscal_api.dto.usuario.UsuarioFormDto;
import com.nexfiscal_api.model.Papel;
import com.nexfiscal_api.model.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioDto toDto(Usuario entity, List<String> papeis) {
        return new UsuarioDto(
                entity.getIdUsuario(),
                entity.getNmUsuario(),
                entity.getNmEmail(),
                entity.isFlAtivo(),
                papeis,
                toOffset(entity.getDtCriacao()));
    }

    public static PapelDto toDto(Papel entity) {
        return new PapelDto(entity.getIdPapel(), entity.getNmPapel(), entity.getDsPapel());
    }

    public static void applyForm(Usuario entity, UsuarioFormDto form) {
        entity.setNmUsuario(nullToEmpty(form.nome()).trim());
        entity.setNmEmail(nullToEmpty(form.email()).trim().toLowerCase());
        entity.setFlAtivo(form.ativo());
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
