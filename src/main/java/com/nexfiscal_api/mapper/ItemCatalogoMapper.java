package com.nexfiscal_api.mapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.nexfiscal_api.dto.item.ItemCatalogoDto;
import com.nexfiscal_api.dto.item.ItemCatalogoFormDto;
import com.nexfiscal_api.model.ItemCatalogo;

public final class ItemCatalogoMapper {

    private ItemCatalogoMapper() {
    }

    public static ItemCatalogoDto toDto(ItemCatalogo entity) {
        return new ItemCatalogoDto(
                entity.getIdItemCatalogo(),
                entity.getDsTipo(),
                entity.getNmItem(),
                entity.getDsDescricao(),
                entity.getDsCodigoLc116(),
                entity.getVlPrecoPadrao(),
                entity.getVlAliquotaIss(),
                entity.isSgIssRetido(),
                entity.getDsUnidade(),
                entity.getDsCodigoInterno(),
                entity.isSgAtivo(),
                toOffset(entity.getDtCriacao()));
    }

    public static void applyForm(ItemCatalogo entity, ItemCatalogoFormDto form) {
        entity.setDsTipo(form.tipo());
        entity.setNmItem(nullToEmpty(form.nome()));
        entity.setDsDescricao(nullToEmpty(form.descricao()));
        entity.setDsCodigoLc116(nullToEmpty(form.codigoLc116()));
        entity.setVlPrecoPadrao(nullToZero(form.precoPadrao()));
        entity.setVlAliquotaIss(nullToZero(form.aliquotaIss()));
        entity.setSgIssRetido(Boolean.TRUE.equals(form.issRetido()));
        entity.setDsUnidade(nullToEmpty(form.unidade()).isBlank() ? "un" : nullToEmpty(form.unidade()));
        entity.setDsCodigoInterno(nullToEmpty(form.codigoInterno()));
        entity.setSgAtivo(form.ativo() == null || form.ativo());
    }

    private static OffsetDateTime toOffset(java.time.LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
