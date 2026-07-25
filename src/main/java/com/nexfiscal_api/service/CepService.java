package com.nexfiscal_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.nexfiscal_api.dto.cep.CepLookupDto;
import com.nexfiscal_api.dto.cep.ViaCepResponse;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ResourceNotFoundException;

@Service
public class CepService {

    private final RestClient restClient;

    public CepService(@Value("${app.cep.viacep-base-url:https://viacep.com.br/ws}") String viaCepBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(viaCepBaseUrl)
                .build();
    }

    public CepLookupDto buscar(String cep) {
        String digits = onlyDigits(cep);
        if (digits.length() != 8) {
            throw new BusinessException("CEP deve conter 8 dígitos");
        }

        try {
            ViaCepResponse response = restClient.get()
                    .uri("/{cep}/json/", digits)
                    .retrieve()
                    .body(ViaCepResponse.class);

            if (response == null || Boolean.TRUE.equals(response.erro())) {
                throw new ResourceNotFoundException("CEP não encontrado");
            }

            return new CepLookupDto(
                    formatCep(digits),
                    nullToEmpty(response.logradouro()),
                    nullToEmpty(response.complemento()),
                    nullToEmpty(response.bairro()),
                    nullToEmpty(response.localidade()),
                    nullToEmpty(response.uf()).toUpperCase());
        } catch (RestClientException ex) {
            throw new BusinessException("Não foi possível consultar o CEP no momento");
        }
    }

    private static String onlyDigits(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }

    private static String formatCep(String digits) {
        return digits.substring(0, 5) + "-" + digits.substring(5);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
