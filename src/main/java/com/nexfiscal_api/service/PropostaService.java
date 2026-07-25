package com.nexfiscal_api.service;

import java.time.Year;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.proposal.ProposalDto;
import com.nexfiscal_api.dto.proposal.ProposalWriteDto;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.PropostaMapper;
import com.nexfiscal_api.model.Cliente;
import com.nexfiscal_api.model.Empresa;
import com.nexfiscal_api.model.Proposta;
import com.nexfiscal_api.repository.ClienteRepository;
import com.nexfiscal_api.repository.EmpresaRepository;
import com.nexfiscal_api.repository.PropostaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private static final Set<String> STATUS_VALIDOS = Set.of("pendente", "aprovada", "cancelada", "concluida");

    private final PropostaRepository repository;
    private final PropostaNumeroGenerator numeroGenerator;
    private final EmpresaService empresaService;
    private final ClienteService clienteService;
    private final EmpresaRepository empresaRepository;
    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Page<ProposalDto> listar(String busca, String status, Pageable pageable) {
        String statusFilter = normalizeStatus(status);
        return repository.buscar(busca, statusFilter, pageable).map(PropostaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProposalDto buscarPorId(Long id) {
        return PropostaMapper.toDto(buscarEntidade(id));
    }

    @Transactional
    public ProposalDto criar(ProposalWriteDto request) {
        int ano = Year.now().getValue();
        int seq = numeroGenerator.proximoSeq(ano);

        Proposta entity = new Proposta();
        entity.setNuAno(ano);
        entity.setNuSeq(seq);
        entity.setNuNumero(String.format("%d-%04d", ano, seq));
        entity.setDsStatus("pendente");
        aplicarRequest(entity, request);
        return PropostaMapper.toDto(repository.save(entity));
    }

    @Transactional
    public ProposalDto atualizar(Long id, ProposalWriteDto request) {
        Proposta entity = buscarEntidade(id);
        aplicarRequest(entity, request);
        return PropostaMapper.toDto(repository.save(entity));
    }

    @Transactional
    public ProposalDto atualizarStatus(Long id, String status) {
        validarStatus(status);
        Proposta entity = buscarEntidade(id);
        entity.setDsStatus(status);
        return PropostaMapper.toDto(repository.save(entity));
    }

    @Transactional
    public ProposalDto duplicar(Long id) {
        Proposta source = buscarEntidade(id);
        int ano = Year.now().getValue();
        int seq = numeroGenerator.proximoSeq(ano);

        Proposta copy = new Proposta();
        copy.setNuAno(ano);
        copy.setNuSeq(seq);
        copy.setNuNumero(String.format("%d-%04d", ano, seq));
        copy.setDsStatus("pendente");
        PropostaMapper.copyFrom(copy, source);
        return PropostaMapper.toDto(repository.save(copy));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarRequest(Proposta entity, ProposalWriteDto request) {
        PropostaMapper.applyForm(entity, request.toForm());
        PropostaMapper.applyReferencias(
                entity,
                resolverEmpresa(request.empresaId()),
                resolverCliente(request.clienteId()));
        persistirCadastros(entity, request);
    }

    private Empresa resolverEmpresa(Long empresaId) {
        if (empresaId == null) {
            return null;
        }
        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

    private Cliente resolverCliente(Long clienteId) {
        if (clienteId == null) {
            return null;
        }
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private Proposta buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposta não encontrada"));
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank() || "all".equalsIgnoreCase(status)) {
            return null;
        }
        validarStatus(status);
        return status;
    }

    private void validarStatus(String status) {
        if (!STATUS_VALIDOS.contains(status)) {
            throw new BusinessException("Status inválido: " + status);
        }
    }

    private void persistirCadastros(Proposta entity, ProposalWriteDto request) {
        if (request.deveSalvarEmpresa()) {
            Long empresaId = empresaService.salvarDaProposta(request.empresa(), request.empresaId());
            if (empresaId != null) {
                entity.setEmpresa(empresaRepository.getReferenceById(empresaId));
            }
        }
        if (request.deveSalvarCliente()) {
            Long clienteId = clienteService.salvarDaProposta(request.cliente(), request.clienteId());
            if (clienteId != null) {
                entity.setCliente(clienteRepository.getReferenceById(clienteId));
            }
        }
    }
}
