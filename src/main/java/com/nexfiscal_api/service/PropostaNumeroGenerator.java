package com.nexfiscal_api.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PropostaNumeroGenerator {

    private final JdbcTemplate jdbcTemplate;

    public int proximoSeq(int ano) {
        Integer seq = jdbcTemplate.queryForObject("""
                insert into proposta_seq_ano (nu_ano, nu_seq)
                values (?, 1)
                on conflict (nu_ano) do update set nu_seq = proposta_seq_ano.nu_seq + 1
                returning nu_seq
                """, Integer.class, ano);
        return seq == null ? 1 : seq;
    }
}
