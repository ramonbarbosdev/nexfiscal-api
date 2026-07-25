package com.nexfiscal_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.config.PrestadorConfigDto;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.PrestadorConfigMapper;
import com.nexfiscal_api.model.PrestadorConfig;
import com.nexfiscal_api.repository.PrestadorConfigRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrestadorConfigService {

    private static final Long CONFIG_ID = 1L;

    private final PrestadorConfigRepository repository;

    @Transactional(readOnly = true)
    public PrestadorConfigDto obter() {
        return PrestadorConfigMapper.toDto(buscar());
    }

    @Transactional
    public PrestadorConfigDto atualizar(PrestadorConfigDto dto) {
        PrestadorConfig entity = buscar();
        PrestadorConfigMapper.apply(entity, dto);
        return PrestadorConfigMapper.toDto(repository.save(entity));
    }

    private PrestadorConfig buscar() {
        return repository.findById(CONFIG_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Configuração do prestador não encontrada"));
    }
}
