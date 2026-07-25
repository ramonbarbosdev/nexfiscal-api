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
@Table(name = "permissao")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_permissao")
    @SequenceGenerator(name = "seq_permissao", sequenceName = "seq_permissao", allocationSize = 1)
    @Column(name = "id_permissao")
    private Long idPermissao;

    @Column(name = "nm_permissao", nullable = false)
    private String nmPermissao;

    @Column(name = "ds_permissao")
    private String dsPermissao;

    @Column(name = "nm_chave", nullable = false, unique = true)
    private String nmChave;
}
