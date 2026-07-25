package com.nexfiscal_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_proposta")
public class ItemProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_proposta")
    @SequenceGenerator(name = "seq_item_proposta", sequenceName = "seq_item_proposta", allocationSize = 1)
    @Column(name = "id_item_proposta")
    private Long idItemProposta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proposta", nullable = false)
    private Proposta proposta;

    @Column(name = "ds_descricao", nullable = false)
    private String dsDescricao = "";

    @Column(name = "qt_quantidade", nullable = false)
    private BigDecimal qtQuantidade = BigDecimal.ONE;

    @Column(name = "vl_unitario", nullable = false)
    private BigDecimal vlUnitario = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item_catalogo")
    private ItemCatalogo itemCatalogo;
}
