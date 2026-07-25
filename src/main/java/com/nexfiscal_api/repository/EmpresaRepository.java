package com.nexfiscal_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("""
            select e from Empresa e
            where :busca is null or :busca = '' or
              lower(e.nmEmpresa) like lower(concat('%', :busca, '%')) or
              lower(e.nmEmail) like lower(concat('%', :busca, '%'))
            """)
    Page<Empresa> buscar(@Param("busca") String busca, Pageable pageable);
}
