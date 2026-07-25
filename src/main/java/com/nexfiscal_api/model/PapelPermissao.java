package com.nexfiscal_api.model;

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
@Table(name = "papel_permissao")
public class PapelPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_papel_permissao")
    @SequenceGenerator(name = "seq_papel_permissao", sequenceName = "seq_papel_permissao", allocationSize = 1)
    @Column(name = "id_papel_permissao")
    private Long idPapelPermissao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_papel", nullable = false)
    private Papel papel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permissao", nullable = false)
    private Permissao permissao;
}
