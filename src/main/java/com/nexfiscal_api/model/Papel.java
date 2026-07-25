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
@Table(name = "papel")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_papel")
    @SequenceGenerator(name = "seq_papel", sequenceName = "seq_papel", allocationSize = 1)
    @Column(name = "id_papel")
    private Long idPapel;

    @Column(name = "nm_papel", nullable = false, unique = true)
    private String nmPapel;

    @Column(name = "ds_papel")
    private String dsPapel;

    @Column(name = "fl_ativo", nullable = false)
    private boolean flAtivo = true;
}
