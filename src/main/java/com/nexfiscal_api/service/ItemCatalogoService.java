package com.nexfiscal_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexfiscal_api.dto.item.ItemCatalogoDto;
import com.nexfiscal_api.dto.item.ItemCatalogoFormDto;
import com.nexfiscal_api.exception.ConflictException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.mapper.ItemCatalogoMapper;
import com.nexfiscal_api.model.ItemCatalogo;
import com.nexfiscal_api.repository.ItemCatalogoRepository;
import com.nexfiscal_api.repository.ItemPropostaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCatalogoService {

    private final ItemCatalogoRepository repository;
    private final ItemPropostaRepository itemPropostaRepository;

    @Transactional(readOnly = true)
    public Page<ItemCatalogoDto> listar(String busca, String tipo, Pageable pageable) {
        return repository.buscar(busca, tipo, pageable).map(ItemCatalogoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ItemCatalogoDto buscarPorId(Long id) {
        return ItemCatalogoMapper.toDto(buscarEntidade(id));
    }

    @Transactional
    public ItemCatalogoDto criar(ItemCatalogoFormDto form) {
        ItemCatalogo entity = new ItemCatalogo();
        ItemCatalogoMapper.applyForm(entity, form);
        return ItemCatalogoMapper.toDto(repository.save(entity));
    }

    @Transactional
    public ItemCatalogoDto atualizar(Long id, ItemCatalogoFormDto form) {
        ItemCatalogo entity = buscarEntidade(id);
        ItemCatalogoMapper.applyForm(entity, form);
        return ItemCatalogoMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void excluir(Long id) {
        long vinculos = itemPropostaRepository.countByItemCatalogo_IdItemCatalogo(id);
        if (vinculos > 0) {
            throw new ConflictException(
                    "Não é possível excluir este item pois ele está vinculado a "
                            + vinculos + " proposta(s).");
        }
        repository.delete(buscarEntidade(id));
    }

    private ItemCatalogo buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
    }
}
