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
@Table(name = "proposta_seq_ano")
public class PropostaSeqAno {

    @Id
    @Column(name = "nu_ano")
    private Integer nuAno;

    @Column(name = "nu_seq", nullable = false)
    private Integer nuSeq;
}
