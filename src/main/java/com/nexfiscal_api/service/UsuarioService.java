package com.nexfiscal_api.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.usuario.PapelDto;
import com.nexfiscal_api.dto.usuario.UsuarioDto;
import com.nexfiscal_api.dto.usuario.UsuarioFormDto;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ConflictException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.UsuarioMapper;
import com.nexfiscal_api.model.Papel;
import com.nexfiscal_api.model.Usuario;
import com.nexfiscal_api.model.UsuarioPapel;
import com.nexfiscal_api.repository.PapelRepository;
import com.nexfiscal_api.repository.UsuarioPapelRepository;
import com.nexfiscal_api.repository.UsuarioRepository;
import com.nexfiscal_api.security.PermissionAuthorization;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final UsuarioPapelRepository usuarioPapelRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionAuthorization permissionAuthorization;

    @Transactional(readOnly = true)
    public Page<UsuarioDto> listar(String busca, Pageable pageable) {
        permissionAuthorization.require("USER_VIEW");
        return usuarioRepository.buscar(busca, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public UsuarioDto buscarPorId(Long id) {
        permissionAuthorization.require("USER_VIEW");
        return toDto(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<PapelDto> listarPapeis() {
        permissionAuthorization.require("USER_VIEW");
        return papelRepository.findByFlAtivoTrueOrderByNmPapelAsc().stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }

    @Transactional
    public UsuarioDto criar(UsuarioFormDto form) {
        permissionAuthorization.require("USER_CREATE");
        validarSenhaObrigatoria(form.senha(), true);

        if (usuarioRepository.findByNmEmailIgnoreCase(form.email().trim()).isPresent()) {
            throw new ConflictException("Já existe um usuário com este e-mail");
        }

        Usuario entity = new Usuario();
        UsuarioMapper.applyForm(entity, form);
        entity.setDsSenha(passwordEncoder.encode(form.senha().trim()));
        Usuario saved = usuarioRepository.save(entity);
        sincronizarPapeis(saved, form.papeis());
        return toDto(saved);
    }

    @Transactional
    public UsuarioDto atualizar(Long id, UsuarioFormDto form) {
        permissionAuthorization.require("USER_EDIT");
        Usuario entity = buscarEntidade(id);
        validarAutoDesativacao(id, form.ativo());

        if (usuarioRepository.existsByNmEmailIgnoreCaseAndIdUsuarioNot(form.email().trim(), id)) {
            throw new ConflictException("Já existe um usuário com este e-mail");
        }

        UsuarioMapper.applyForm(entity, form);
        if (form.senha() != null && !form.senha().isBlank()) {
            entity.setDsSenha(passwordEncoder.encode(form.senha().trim()));
        }
        Usuario saved = usuarioRepository.save(entity);
        sincronizarPapeis(saved, form.papeis());
        return toDto(saved);
    }

    @Transactional
    public void excluir(Long id) {
        permissionAuthorization.require("USER_DELETE");
        Usuario entity = buscarEntidade(id);
        validarAutoDesativacao(id, false);
        entity.setFlAtivo(false);
        usuarioRepository.save(entity);
    }

    private Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    private UsuarioDto toDto(Usuario entity) {
        return UsuarioMapper.toDto(entity, usuarioRepository.listarPapeis(entity.getIdUsuario()));
    }

    private void sincronizarPapeis(Usuario usuario, List<String> papeis) {
        Set<String> nomes = new HashSet<>();
        for (String papelNome : papeis) {
            if (papelNome == null || papelNome.isBlank()) {
                continue;
            }
            String normalizado = papelNome.trim().toUpperCase();
            if (!nomes.add(normalizado)) {
                continue;
            }
            Papel papel = papelRepository.findByNmPapelIgnoreCase(normalizado)
                    .filter(Papel::isFlAtivo)
                    .orElseThrow(() -> new BusinessException("Perfil inválido: " + papelNome));
            boolean exists = usuarioPapelRepository.existsByUsuarioIdUsuarioAndPapelNmPapelIgnoreCase(
                    usuario.getIdUsuario(), papel.getNmPapel());
            if (!exists) {
                UsuarioPapel link = new UsuarioPapel();
                link.setUsuario(usuario);
                link.setPapel(papel);
                usuarioPapelRepository.save(link);
            }
        }

        List<UsuarioPapel> atuais = usuarioPapelRepository.findByUsuarioIdUsuario(usuario.getIdUsuario());
        for (UsuarioPapel link : atuais) {
            if (!nomes.contains(link.getPapel().getNmPapel().toUpperCase())) {
                usuarioPapelRepository.delete(link);
            }
        }
    }

    private void validarSenhaObrigatoria(String senha, boolean obrigatoria) {
        if (obrigatoria && (senha == null || senha.isBlank())) {
            throw new BusinessException("Informe a senha do usuário");
        }
        if (senha != null && !senha.isBlank() && senha.trim().length() < 6) {
            throw new BusinessException("A senha deve ter pelo menos 6 caracteres");
        }
    }

    private void validarAutoDesativacao(Long id, boolean ativo) {
        if (id.equals(permissionAuthorization.currentUserId()) && !ativo) {
            throw new BusinessException("Você não pode desativar o próprio usuário");
        }
    }
}
