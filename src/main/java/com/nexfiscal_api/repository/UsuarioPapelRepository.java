package com.nexfiscal_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexfiscal_api.model.UsuarioPapel;

public interface UsuarioPapelRepository extends JpaRepository<UsuarioPapel, Long> {

    boolean existsByUsuarioIdUsuarioAndPapelNmPapelIgnoreCase(Long idUsuario, String nmPapel);
}
