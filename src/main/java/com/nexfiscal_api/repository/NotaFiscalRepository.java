package com.nexfiscal_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.NotaFiscal;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    @Query("""
            select n from NotaFiscal n
            where (:status is null or n.dsStatus = :status)
              and (
                :busca is null or :busca = '' or
                lower(n.tomNome) like lower(concat('%', :busca, '%')) or
                lower(n.srvDescricao) like lower(concat('%', :busca, '%')) or
                n.nuNumero like concat('%', :busca, '%') or
                lower(n.prestRazaoSocial) like lower(concat('%', :busca, '%'))
              )
            """)
    Page<NotaFiscal> buscar(@Param("busca") String busca, @Param("status") String status, Pageable pageable);
}
