package com.nexfiscal_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_catalogo")
public class ItemCatalogo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_catalogo")
    @SequenceGenerator(name = "seq_item_catalogo", sequenceName = "seq_item_catalogo", allocationSize = 1)
    @Column(name = "id_item_catalogo")
    private Long idItemCatalogo;

    @Column(name = "ds_tipo", nullable = false)
    private String dsTipo = "servico";

    @Column(name = "nm_item", nullable = false)
    private String nmItem = "";

    @Column(name = "ds_descricao", nullable = false, columnDefinition = "text")
    private String dsDescricao = "";

    @Column(name = "ds_codigo_lc116", nullable = false)
    private String dsCodigoLc116 = "";

    @Column(name = "vl_preco_padrao", nullable = false)
    private BigDecimal vlPrecoPadrao = BigDecimal.ZERO;

    @Column(name = "vl_aliquota_iss", nullable = false)
    private BigDecimal vlAliquotaIss = BigDecimal.ZERO;

    @Column(name = "sg_iss_retido", nullable = false)
    private boolean sgIssRetido = false;

    @Column(name = "ds_unidade", nullable = false)
    private String dsUnidade = "un";

    @Column(name = "ds_codigo_interno", nullable = false)
    private String dsCodigoInterno = "";

    @Column(name = "sg_ativo", nullable = false)
    private boolean sgAtivo = true;
}
