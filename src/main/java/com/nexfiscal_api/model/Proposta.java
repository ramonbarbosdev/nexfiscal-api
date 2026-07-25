package com.nexfiscal_api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "proposta")
public class Proposta extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proposta")
    @SequenceGenerator(name = "seq_proposta", sequenceName = "seq_proposta", allocationSize = 1)
    @Column(name = "id_proposta")
    private Long idProposta;

    @Column(name = "nu_numero", nullable = false, unique = true)
    private String nuNumero;

    @Column(name = "nu_ano", nullable = false)
    private Integer nuAno;

    @Column(name = "nu_seq", nullable = false)
    private Integer nuSeq;

    @Column(name = "ds_status", nullable = false)
    private String dsStatus = "pendente";

    @Column(name = "ds_empresa_logo", nullable = false, columnDefinition = "text")
    private String dsEmpresaLogo = "";

    @Column(name = "nm_empresa", nullable = false)
    private String nmEmpresa = "";

    @Column(name = "ds_empresa_whatsapp", nullable = false)
    private String dsEmpresaWhatsapp = "";

    @Column(name = "ds_empresa_instagram", nullable = false)
    private String dsEmpresaInstagram = "";

    @Column(name = "nm_empresa_email", nullable = false)
    private String nmEmpresaEmail = "";

    @Column(name = "nm_cliente", nullable = false)
    private String nmCliente = "";

    @Column(name = "ds_cliente_telefone", nullable = false)
    private String dsClienteTelefone = "";

    @Column(name = "nm_projeto_titulo", nullable = false)
    private String nmProjetoTitulo = "";

    @Column(name = "ds_projeto_descricao", nullable = false, columnDefinition = "text")
    private String dsProjetoDescricao = "";

    @Column(name = "ds_projeto_area", nullable = false)
    private String dsProjetoArea = "";

    @Column(name = "ds_projeto_prazo", nullable = false)
    private String dsProjetoPrazo = "";

    @Column(name = "ds_projeto_validade", nullable = false)
    private String dsProjetoValidade = "";

    @Column(name = "vl_desconto", nullable = false)
    private BigDecimal vlDesconto = BigDecimal.ZERO;

    @Column(name = "vl_entrada", nullable = false)
    private BigDecimal vlEntrada = BigDecimal.ZERO;

    @Column(name = "ds_forma_pagamento", nullable = false)
    private String dsFormaPagamento = "";

    @Column(name = "ds_observacoes", nullable = false, columnDefinition = "text")
    private String dsObservacoes = "";

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("idItemProposta ASC")
    private List<ItemProposta> itens = new ArrayList<>();
}
