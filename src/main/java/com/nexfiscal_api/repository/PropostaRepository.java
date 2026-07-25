package com.nexfiscal_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    long countByEmpresa_IdEmpresa(Long idEmpresa);

    long countByCliente_IdCliente(Long idCliente);

    @Query("""
            select p from Proposta p
            where (:status is null or p.dsStatus = :status)
              and (
                :busca is null or :busca = '' or
                lower(p.nmCliente) like lower(concat('%', :busca, '%')) or
                lower(p.nmProjetoTitulo) like lower(concat('%', :busca, '%')) or
                lower(p.nmEmpresa) like lower(concat('%', :busca, '%')) or
                lower(p.nuNumero) like lower(concat('%', :busca, '%'))
              )
            """)
    Page<Proposta> buscar(@Param("busca") String busca, @Param("status") String status, Pageable pageable);
}
