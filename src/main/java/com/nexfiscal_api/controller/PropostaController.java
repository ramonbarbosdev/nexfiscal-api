package com.nexfiscal_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.nexfiscal_api.dto.proposal.ProposalDto;
import com.nexfiscal_api.dto.proposal.ProposalStatusPatchRequest;
import com.nexfiscal_api.dto.proposal.ProposalWriteDto;
import com.nexfiscal_api.service.PropostaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/propostas")
@RequiredArgsConstructor
public class PropostaController {

    private final PropostaService service;

    @GetMapping
    public ResponseEntity<Page<ProposalDto>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20, sort = "dtCriacao") Pageable pageable) {
        return ResponseEntity.ok(service.listar(busca, status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProposalDto> criar(@RequestBody ProposalWriteDto form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProposalDto> atualizar(@PathVariable Long id, @RequestBody ProposalWriteDto form) {
        return ResponseEntity.ok(service.atualizar(id, form));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProposalDto> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProposalStatusPatchRequest request) {
        return ResponseEntity.ok(service.atualizarStatus(id, request.status()));
    }

    @PostMapping("/{id}/duplicar")
    public ResponseEntity<ProposalDto> duplicar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.duplicar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
