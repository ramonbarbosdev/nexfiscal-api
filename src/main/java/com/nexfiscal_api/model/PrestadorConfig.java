package com.nexfiscal_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prestador_config")
public class PrestadorConfig extends AuditableEntity {

    @Id
    @Column(name = "id_config")
    private Long idConfig = 1L;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial = "";

    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia = "";

    @Column(name = "cnpj", nullable = false)
    private String cnpj = "";

    @Column(name = "inscricao_municipal", nullable = false)
    private String inscricaoMunicipal = "";

    @Column(name = "email", nullable = false)
    private String email = "";

    @Column(name = "telefone", nullable = false)
    private String telefone = "";

    @Column(name = "logradouro", nullable = false)
    private String logradouro = "";

    @Column(name = "numero", nullable = false)
    private String numero = "";

    @Column(name = "complemento", nullable = false)
    private String complemento = "";

    @Column(name = "bairro", nullable = false)
    private String bairro = "";

    @Column(name = "cidade", nullable = false)
    private String cidade = "";

    @Column(name = "uf", nullable = false)
    private String uf = "";

    @Column(name = "cep", nullable = false)
    private String cep = "";
}
