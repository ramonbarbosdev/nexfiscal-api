package com.nexfiscal_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexfiscal_api.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    Optional<Papel> findByNmPapelIgnoreCase(String nmPapel);
}
