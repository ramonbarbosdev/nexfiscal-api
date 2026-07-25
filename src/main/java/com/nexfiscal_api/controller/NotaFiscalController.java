package com.nexfiscal_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexfiscal_api.dto.invoice.InvoiceDto;
import com.nexfiscal_api.dto.invoice.InvoiceFormDto;
import com.nexfiscal_api.dto.invoice.InvoiceStatusPatchRequest;
import com.nexfiscal_api.service.NotaFiscalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalController {

    private final NotaFiscalService service;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Page<InvoiceDto>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20, sort = "dtEmissao") Pageable pageable) {
        return ResponseEntity.ok(service.listar(busca, status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> criar(@RequestBody InvoiceFormDto form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> atualizar(@PathVariable Long id, @RequestBody InvoiceFormDto form) {
        return ResponseEntity.ok(service.atualizar(id, form));
    }

    @PostMapping("/{id}/emitir")
    public ResponseEntity<InvoiceDto> emitir(@PathVariable Long id) {
        return ResponseEntity.ok(service.emitir(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<InvoiceDto> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InvoiceDto> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceStatusPatchRequest request) {
        return ResponseEntity.ok(service.atualizarStatus(id, request.status()));
    }

    @PostMapping("/{id}/duplicar")
    public ResponseEntity<InvoiceDto> duplicar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.duplicar(id));
    }

    @PostMapping("/importar")
    public ResponseEntity<List<InvoiceDto>> importar(@RequestBody Object payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.importar(payload));
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar() throws JsonProcessingException {
        Map<String, Object> payload = service.exportar();
        byte[] body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(payload);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"notas-fiscais.json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
