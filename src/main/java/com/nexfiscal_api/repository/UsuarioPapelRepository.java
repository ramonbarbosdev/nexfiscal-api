package com.nexfiscal_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexfiscal_api.model.UsuarioPapel;

public interface UsuarioPapelRepository extends JpaRepository<UsuarioPapel, Long> {

    boolean existsByUsuarioIdUsuarioAndPapelNmPapelIgnoreCase(Long idUsuario, String nmPapel);

    List<UsuarioPapel> findByUsuarioIdUsuario(Long idUsuario);

    void deleteByUsuarioIdUsuario(Long idUsuario);
}
