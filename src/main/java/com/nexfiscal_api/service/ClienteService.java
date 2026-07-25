package com.nexfiscal_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.cliente.ClienteDto;
import com.nexfiscal_api.dto.cliente.ClienteFormDto;
import com.nexfiscal_api.dto.proposal.ProposalClienteDto;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.ClienteMapper;
import com.nexfiscal_api.model.Cliente;
import com.nexfiscal_api.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public Page<ClienteDto> listar(String busca, Pageable pageable) {
        return repository.buscar(busca, pageable).map(ClienteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ClienteDto buscarPorId(Long id) {
        return ClienteMapper.toDto(buscarEntidade(id));
    }

    @Transactional
    public ClienteDto criar(ClienteFormDto form) {
        Cliente entity = new Cliente();
        ClienteMapper.applyForm(entity, form);
        return ClienteMapper.toDto(repository.save(entity));
    }

    @Transactional
    public ClienteDto atualizar(Long id, ClienteFormDto form) {
        Cliente entity = buscarEntidade(id);
        ClienteMapper.applyForm(entity, form);
        return ClienteMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    @Transactional
    public void salvarDaProposta(ProposalClienteDto dto, Long clienteId) {
        if (dto == null || isBlank(dto.nome())) {
            return;
        }

        Cliente entity;
        if (clienteId != null) {
            entity = buscarEntidade(clienteId);
        } else {
            entity = new Cliente();
        }

        ClienteMapper.applyFromProposal(entity, dto);
        repository.save(entity);
    }

    private Cliente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
