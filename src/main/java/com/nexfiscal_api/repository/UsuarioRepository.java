package com.nexfiscal_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNmEmailIgnoreCaseAndFlAtivoTrue(String nmEmail);

    @Query("""
            select distinct p.nmChave
            from UsuarioPapel up
            join up.papel papel
            join PapelPermissao pp on pp.papel = papel
            join pp.permissao p
            where up.usuario.idUsuario = :idUsuario
              and papel.flAtivo = true
            """)
    List<String> listarPermissoes(@Param("idUsuario") Long idUsuario);
}
