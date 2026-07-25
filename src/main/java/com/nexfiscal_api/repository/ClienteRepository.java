package com.nexfiscal_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
            select c from Cliente c
            where :busca is null or :busca = '' or
              lower(c.nmCliente) like lower(concat('%', :busca, '%')) or
              lower(c.dsTelefone) like lower(concat('%', :busca, '%'))
            """)
    Page<Cliente> buscar(@Param("busca") String busca, Pageable pageable);
}
