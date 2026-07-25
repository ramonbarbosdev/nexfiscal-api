package com.nexfiscal_api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexfiscal_api.dto.invoice.InvoiceDto;
import com.nexfiscal_api.dto.invoice.InvoiceFormDto;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.NotaFiscalMapper;
import com.nexfiscal_api.model.NotaFiscal;
import com.nexfiscal_api.repository.NotaFiscalRepository;
import com.nexfiscal_api.util.VerificationCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaFiscalService {

    private static final Set<String> STATUS_VALIDOS = Set.of("rascunho", "emitida", "cancelada");
    private static final String EXPORT_VERSION = "1";

    private final NotaFiscalRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public Page<InvoiceDto> listar(String busca, String status, Pageable pageable) {
        String statusFilter = normalizeStatus(status);
        return repository.buscar(busca, statusFilter, pageable).map(NotaFiscalMapper::toDto);
    }

    @Transactional(readOnly = true)
    public InvoiceDto buscarPorId(Long id) {
        return NotaFiscalMapper.toDto(buscarEntidade(id));
    }

    @Transactional
    public InvoiceDto criar(InvoiceFormDto form) {
        NotaFiscal entity = new NotaFiscal();
        entity.setNuNumero(proximoNumero());
        entity.setNuSerie("1");
        entity.setDsStatus("rascunho");
        entity.setDtEmissao(LocalDateTime.now());
        NotaFiscalMapper.applyForm(entity, form);
        return NotaFiscalMapper.toDto(repository.save(entity));
    }

    @Transactional
    public InvoiceDto atualizar(Long id, InvoiceFormDto form) {
        NotaFiscal entity = buscarEntidade(id);
        if ("cancelada".equals(entity.getDsStatus())) {
            throw new BusinessException("Nota fiscal cancelada não pode ser editada");
        }
        NotaFiscalMapper.applyForm(entity, form);
        return NotaFiscalMapper.toDto(repository.save(entity));
    }

    @Transactional
    public InvoiceDto emitir(Long id) {
        NotaFiscal entity = buscarEntidade(id);
        if ("cancelada".equals(entity.getDsStatus())) {
            throw new BusinessException("Nota fiscal cancelada não pode ser emitida");
        }
        entity.setDsStatus("emitida");
        entity.setDtEmissao(LocalDateTime.now());
        if (entity.getDsCodigoVerificacao() == null || entity.getDsCodigoVerificacao().isBlank()) {
            entity.setDsCodigoVerificacao(VerificationCodeGenerator.generate());
        }
        return NotaFiscalMapper.toDto(repository.save(entity));
    }

    @Transactional
    public InvoiceDto cancelar(Long id) {
        NotaFiscal entity = buscarEntidade(id);
        entity.setDsStatus("cancelada");
        return NotaFiscalMapper.toDto(repository.save(entity));
    }

    @Transactional
    public InvoiceDto atualizarStatus(Long id, String status) {
        validarStatus(status);
        NotaFiscal entity = buscarEntidade(id);
        entity.setDsStatus(status);
        if ("emitida".equals(status)
                && (entity.getDsCodigoVerificacao() == null || entity.getDsCodigoVerificacao().isBlank())) {
            entity.setDsCodigoVerificacao(VerificationCodeGenerator.generate());
            entity.setDtEmissao(LocalDateTime.now());
        }
        return NotaFiscalMapper.toDto(repository.save(entity));
    }

    @Transactional
    public InvoiceDto duplicar(Long id) {
        NotaFiscal source = buscarEntidade(id);
        NotaFiscal copy = new NotaFiscal();
        copy.setNuNumero(proximoNumero());
        copy.setNuSerie("1");
        copy.setDsStatus("rascunho");
        copy.setDtEmissao(LocalDateTime.now());
        copy.setDsCodigoVerificacao(null);
        NotaFiscalMapper.copyFrom(copy, source);
        return NotaFiscalMapper.toDto(repository.save(copy));
    }

    @Transactional
    public void excluir(Long id) {
        NotaFiscal entity = buscarEntidade(id);
        if ("emitida".equals(entity.getDsStatus())) {
            throw new BusinessException("Cancele a nota fiscal antes de excluir");
        }
        repository.delete(entity);
    }

    @Transactional
    public List<InvoiceDto> importar(Object payload) {
        List<?> rawList = extrairLista(payload);
        List<InvoiceDto> importadas = new ArrayList<>();

        for (Object raw : rawList) {
            importadas.add(criar(toForm(raw)));
        }

        return importadas;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> exportar() {
        List<InvoiceDto> invoices = repository.findAll().stream().map(NotaFiscalMapper::toDto).toList();
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("version", EXPORT_VERSION);
        payload.put("exportedAt", LocalDateTime.now().toString());
        payload.put("invoices", invoices);
        return payload;
    }

    private NotaFiscal buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal não encontrada"));
    }

    private String proximoNumero() {
        Long seq = jdbcTemplate.queryForObject("select nextval('seq_nota_fiscal_numero')", Long.class);
        return String.format("%06d", seq == null ? 1L : seq);
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

    private InvoiceFormDto toForm(Object raw) {
        if (raw instanceof Map<?, ?> map) {
            if (map.get("form") != null) {
                return objectMapper.convertValue(map.get("form"), InvoiceFormDto.class);
            }
            if (map.containsKey("prestador") && map.containsKey("tomador") && map.containsKey("servico")) {
                return objectMapper.convertValue(map, InvoiceFormDto.class);
            }
        }
        return objectMapper.convertValue(raw, InvoiceFormDto.class);
    }

    @SuppressWarnings("unchecked")
    private List<?> extrairLista(Object payload) {
        if (payload instanceof List<?> list) {
            return list;
        }
        if (payload instanceof Map<?, ?> map && map.get("invoices") instanceof List<?> invoices) {
            return invoices;
        }
        if (payload instanceof Map<?, ?> map) {
            return List.of(map);
        }
        throw new BusinessException("Formato de importação inválido");
    }
}
