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
@Table(name = "cliente")
public class Cliente extends AuditableEntity implements AddressMapper.AddressWritable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    @SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nm_cliente", nullable = false)
    private String nmCliente = "";

    @Column(name = "ds_telefone", nullable = false)
    private String dsTelefone = "";

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
