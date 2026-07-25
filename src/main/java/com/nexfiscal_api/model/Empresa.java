package com.nexfiscal_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.nexfiscal_api.mapper.AddressMapper;

@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa extends AuditableEntity implements AddressMapper.AddressWritable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", allocationSize = 1)
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "ds_logo", nullable = false, columnDefinition = "text")
    private String dsLogo = "";

    @Column(name = "nm_empresa", nullable = false)
    private String nmEmpresa = "";

    @Column(name = "ds_whatsapp", nullable = false)
    private String dsWhatsapp = "";

    @Column(name = "ds_instagram", nullable = false)
    private String dsInstagram = "";

    @Column(name = "nm_email", nullable = false)
    private String nmEmail = "";

    @Column(name = "ds_cnpj", nullable = false)
    private String dsCnpj = "";

    @Column(name = "nm_logradouro", nullable = false)
    private String logradouro = "";

    @Column(name = "ds_numero", nullable = false)
    private String numero = "";

    @Column(name = "ds_complemento", nullable = false)
    private String complemento = "";

    @Column(name = "nm_bairro", nullable = false)
    private String bairro = "";

    @Column(name = "nm_cidade", nullable = false)
    private String cidade = "";

    @Column(name = "sg_uf", nullable = false)
    private String uf = "";

    @Column(name = "ds_cep", nullable = false)
    private String cep = "";
}
