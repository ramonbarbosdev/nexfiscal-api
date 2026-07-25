package com.nexfiscal_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexfiscal_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNmEmailIgnoreCaseAndFlAtivoTrue(String nmEmail);

    Optional<Usuario> findByNmEmailIgnoreCase(String nmEmail);

    boolean existsByNmEmailIgnoreCaseAndIdUsuarioNot(String nmEmail, Long idUsuario);

    @Query("""
            select u from Usuario u
            where :busca is null or :busca = '' or
              lower(u.nmUsuario) like lower(concat('%', :busca, '%')) or
              lower(u.nmEmail) like lower(concat('%', :busca, '%'))
            """)
    Page<Usuario> buscar(@Param("busca") String busca, Pageable pageable);

    @Query("""
            select distinct papel.nmPapel
            from UsuarioPapel up
            join up.papel papel
            where up.usuario.idUsuario = :idUsuario
              and papel.flAtivo = true
            order by papel.nmPapel
            """)
    List<String> listarPapeis(@Param("idUsuario") Long idUsuario);

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
