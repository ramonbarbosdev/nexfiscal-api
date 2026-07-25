package com.nexfiscal_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexfiscal_api.dto.cnpj.CnpjLookupDto;
import com.nexfiscal_api.service.CnpjService;

@RestController
@RequestMapping("/cnpj")
public class CnpjController {

    private final CnpjService cnpjService;

    public CnpjController(CnpjService cnpjService) {
        this.cnpjService = cnpjService;
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<CnpjLookupDto> buscar(@PathVariable String cnpj) {
        return ResponseEntity.ok(cnpjService.buscar(cnpj));
    }
}
