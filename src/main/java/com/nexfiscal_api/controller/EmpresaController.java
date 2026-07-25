package com.nexfiscal_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexfiscal_api.dto.empresa.EmpresaDto;
import com.nexfiscal_api.dto.empresa.EmpresaFormDto;
import com.nexfiscal_api.service.EmpresaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;

    @GetMapping
    public ResponseEntity<Page<EmpresaDto>> listar(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 50, sort = "nmEmpresa") Pageable pageable) {
        return ResponseEntity.ok(service.listar(busca, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaDto> criar(@Valid @RequestBody EmpresaFormDto form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> atualizar(@PathVariable Long id, @Valid @RequestBody EmpresaFormDto form) {
        return ResponseEntity.ok(service.atualizar(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
