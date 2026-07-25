package com.nexfiscal_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexfiscal_api.dto.usuario.PapelDto;
import com.nexfiscal_api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/papeis")
@RequiredArgsConstructor
public class PapelController {

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<List<PapelDto>> listar() {
        return ResponseEntity.ok(service.listarPapeis());
    }
}
