package com.nexfiscal_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "nota_fiscal")
public class NotaFiscal extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal")
    @SequenceGenerator(name = "seq_nota_fiscal", sequenceName = "seq_nota_fiscal", allocationSize = 1)
    @Column(name = "id_nota_fiscal")
    private Long idNotaFiscal;

    @Column(name = "nu_numero", nullable = false)
    private String nuNumero;

    @Column(name = "nu_serie", nullable = false)
    private String nuSerie = "1";

    @Column(name = "ds_status", nullable = false)
    private String dsStatus = "rascunho";

    @Column(name = "dt_emissao", nullable = false)
    private LocalDateTime dtEmissao = LocalDateTime.now();

    @Column(name = "ds_codigo_verificacao")
    private String dsCodigoVerificacao;

    @Column(name = "ds_observacoes", nullable = false, columnDefinition = "text")
    private String dsObservacoes = "";

    @Column(name = "prest_razao_social", nullable = false)
    private String prestRazaoSocial = "";

    @Column(name = "prest_nome_fantasia", nullable = false)
    private String prestNomeFantasia = "";

    @Column(name = "prest_cnpj", nullable = false)
    private String prestCnpj = "";

    @Column(name = "prest_inscricao_municipal", nullable = false)
    private String prestInscricaoMunicipal = "";

    @Column(name = "prest_email", nullable = false)
    private String prestEmail = "";

    @Column(name = "prest_telefone", nullable = false)
    private String prestTelefone = "";

    @Column(name = "prest_logradouro", nullable = false)
    private String prestLogradouro = "";

    @Column(name = "prest_numero", nullable = false)
    private String prestNumero = "";

    @Column(name = "prest_complemento", nullable = false)
    private String prestComplemento = "";

    @Column(name = "prest_bairro", nullable = false)
    private String prestBairro = "";

    @Column(name = "prest_cidade", nullable = false)
    private String prestCidade = "";

    @Column(name = "prest_uf", nullable = false)
    private String prestUf = "";

    @Column(name = "prest_cep", nullable = false)
    private String prestCep = "";

    @Column(name = "tom_tipo", nullable = false)
    private String tomTipo = "pj";

    @Column(name = "tom_nome", nullable = false)
    private String tomNome = "";

    @Column(name = "tom_cpf_cnpj", nullable = false)
    private String tomCpfCnpj = "";

    @Column(name = "tom_email", nullable = false)
    private String tomEmail = "";

    @Column(name = "tom_telefone", nullable = false)
    private String tomTelefone = "";

    @Column(name = "tom_inscricao_municipal", nullable = false)
    private String tomInscricaoMunicipal = "";

    @Column(name = "tom_logradouro", nullable = false)
    private String tomLogradouro = "";

    @Column(name = "tom_numero", nullable = false)
    private String tomNumero = "";

    @Column(name = "tom_complemento", nullable = false)
    private String tomComplemento = "";

    @Column(name = "tom_bairro", nullable = false)
    private String tomBairro = "";

    @Column(name = "tom_cidade", nullable = false)
    private String tomCidade = "";

    @Column(name = "tom_uf", nullable = false)
    private String tomUf = "";

    @Column(name = "tom_cep", nullable = false)
    private String tomCep = "";

    @Column(name = "srv_codigo_lc116", nullable = false)
    private String srvCodigoLc116 = "";

    @Column(name = "srv_descricao", nullable = false)
    private String srvDescricao = "";

    @Column(name = "srv_discriminacao", nullable = false, columnDefinition = "text")
    private String srvDiscriminacao = "";

    @Column(name = "srv_valor_servico", nullable = false)
    private BigDecimal srvValorServico = BigDecimal.ZERO;

    @Column(name = "srv_aliquota_iss", nullable = false)
    private BigDecimal srvAliquotaIss = BigDecimal.ZERO;

    @Column(name = "srv_iss_retido", nullable = false)
    private boolean srvIssRetido;

    @Column(name = "srv_valor_deducoes", nullable = false)
    private BigDecimal srvValorDeducoes = BigDecimal.ZERO;

    @Column(name = "srv_desconto_incondicionado", nullable = false)
    private BigDecimal srvDescontoIncondicionado = BigDecimal.ZERO;

    @Column(name = "srv_desconto_condicionado", nullable = false)
    private BigDecimal srvDescontoCondicionado = BigDecimal.ZERO;
}
