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

@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa extends AuditableEntity {

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
}
