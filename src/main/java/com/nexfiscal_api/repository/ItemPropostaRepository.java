package com.nexfiscal_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexfiscal_api.model.ItemProposta;

public interface ItemPropostaRepository extends JpaRepository<ItemProposta, Long> {

    long countByItemCatalogo_IdItemCatalogo(Long idItemCatalogo);
}
