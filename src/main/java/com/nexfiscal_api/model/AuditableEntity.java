package com.nexfiscal_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDateTime dtCriacao;

    @Column(name = "dt_atualizacao")
    private LocalDateTime dtAtualizacao;

    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        if (this.dtCriacao == null) {
            this.dtCriacao = agora;
        }
        if (this.dtAtualizacao == null) {
            this.dtAtualizacao = agora;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dtAtualizacao = LocalDateTime.now();
    }
}
