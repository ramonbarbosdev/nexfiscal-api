package com.nexfiscal_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.ItemCatalogo;

public interface ItemCatalogoRepository extends JpaRepository<ItemCatalogo, Long> {

    @Query("""
            select i from ItemCatalogo i
            where (:busca is null or :busca = '' or
              lower(i.nmItem) like lower(concat('%', :busca, '%')) or
              lower(i.dsDescricao) like lower(concat('%', :busca, '%')) or
              lower(i.dsCodigoInterno) like lower(concat('%', :busca, '%')))
              and (:tipo is null or :tipo = '' or i.dsTipo = :tipo)
            """)
    Page<ItemCatalogo> buscar(@Param("busca") String busca, @Param("tipo") String tipo, Pageable pageable);
}
