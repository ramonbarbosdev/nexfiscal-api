package com.nexfiscal_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.empresa.EmpresaDto;
import com.nexfiscal_api.dto.empresa.EmpresaFormDto;
import com.nexfiscal_api.dto.proposal.ProposalEmpresaDto;
import com.nexfiscal_api.exception.ConflictException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.EmpresaMapper;
import com.nexfiscal_api.model.Empresa;
import com.nexfiscal_api.repository.EmpresaRepository;
import com.nexfiscal_api.repository.PropostaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;
    private final PropostaRepository propostaRepository;

    @Transactional(readOnly = true)
    public Page<EmpresaDto> listar(String busca, Pageable pageable) {
        return repository.buscar(busca, pageable).map(EmpresaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EmpresaDto buscarPorId(Long id) {
        return EmpresaMapper.toDto(buscarEntidade(id));
    }

    @Transactional
    public EmpresaDto criar(EmpresaFormDto form) {
        Empresa entity = new Empresa();
        EmpresaMapper.applyForm(entity, form);
        return EmpresaMapper.toDto(repository.save(entity));
    }

    @Transactional
    public EmpresaDto atualizar(Long id, EmpresaFormDto form) {
        Empresa entity = buscarEntidade(id);
        EmpresaMapper.applyForm(entity, form);
        return EmpresaMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void excluir(Long id) {
        long vinculos = propostaRepository.countByEmpresa_IdEmpresa(id);
        if (vinculos > 0) {
            throw new ConflictException(
                    "Não é possível excluir esta empresa pois ela está vinculada a "
                            + vinculos + " proposta(s).");
        }
        repository.delete(buscarEntidade(id));
    }

    @Transactional
    public Long salvarDaProposta(ProposalEmpresaDto dto, Long empresaId) {
        if (dto == null || isBlank(dto.nome())) {
            return empresaId;
        }

        Empresa entity;
        if (empresaId != null) {
            entity = buscarEntidade(empresaId);
        } else {
            entity = new Empresa();
        }

        EmpresaMapper.applyFromProposal(entity, dto);
        return repository.save(entity).getIdEmpresa();
    }

    private Empresa buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
