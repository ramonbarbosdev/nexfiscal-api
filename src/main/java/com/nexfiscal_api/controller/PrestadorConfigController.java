package com.nexfiscal_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexfiscal_api.dto.config.PrestadorConfigDto;
import com.nexfiscal_api.service.PrestadorConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/config/prestador")
@RequiredArgsConstructor
public class PrestadorConfigController {

    private final PrestadorConfigService service;

    @GetMapping
    public ResponseEntity<PrestadorConfigDto> obter() {
        return ResponseEntity.ok(service.obter());
    }

    @PutMapping
    public ResponseEntity<PrestadorConfigDto> atualizar(@RequestBody PrestadorConfigDto dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
}
