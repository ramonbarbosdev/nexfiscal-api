package com.nexfiscal_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexfiscal_api.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    java.util.Optional<Papel> findByNmPapelIgnoreCase(String nmPapel);

    List<Papel> findByFlAtivoTrueOrderByNmPapelAsc();
}
