package com.nexfiscal_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.nexfiscal_api.dto.cnpj.BrasilApiCnpjResponse;
import com.nexfiscal_api.dto.cnpj.CnpjLookupDto;
import com.nexfiscal_api.dto.common.PartyAddressDto;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ResourceNotFoundException;

@Service
public class CnpjService {

    private final RestClient restClient;

    public CnpjService(
            @Value("${app.cnpj.brasilapi-base-url:https://brasilapi.com.br/api/cnpj/v1}") String brasilApiBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(brasilApiBaseUrl)
                .build();
    }

    public CnpjLookupDto buscar(String cnpj) {
        String digits = onlyDigits(cnpj);
        if (digits.length() != 14) {
            throw new BusinessException("CNPJ deve conter 14 dígitos");
        }

        try {
            BrasilApiCnpjResponse response = restClient.get()
                    .uri("/{cnpj}", digits)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, res) -> {
                        throw new ResourceNotFoundException("CNPJ não encontrado");
                    })
                    .body(BrasilApiCnpjResponse.class);

            if (response == null) {
                throw new ResourceNotFoundException("CNPJ não encontrado");
            }

            return new CnpjLookupDto(
                    nullToEmpty(response.razaoSocial()),
                    nullToEmpty(response.nomeFantasia()),
                    formatCnpj(digits),
                    nullToEmpty(response.email()),
                    formatPhone(response.dddTelefone1()),
                    new PartyAddressDto(
                            nullToEmpty(response.logradouro()),
                            nullToEmpty(response.numero()),
                            nullToEmpty(response.complemento()),
                            nullToEmpty(response.bairro()),
                            nullToEmpty(response.municipio()),
                            nullToEmpty(response.uf()).toUpperCase(),
                            formatCep(response.cep())));
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (RestClientException ex) {
            throw new BusinessException("Não foi possível consultar o CNPJ no momento");
        }
    }

    private static String onlyDigits(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }

    private static String formatCnpj(String digits) {
        return digits.substring(0, 2) + "."
                + digits.substring(2, 5) + "."
                + digits.substring(5, 8) + "/"
                + digits.substring(8, 12) + "-"
                + digits.substring(12);
    }

    private static String formatCep(String value) {
        String digits = onlyDigits(value);
        if (digits.length() != 8) {
            return "";
        }
        return digits.substring(0, 5) + "-" + digits.substring(5);
    }

    private static String formatPhone(String value) {
        String digits = onlyDigits(value);
        if (digits.length() < 10) {
            return "";
        }
        if (digits.length() == 10) {
            return "(" + digits.substring(0, 2) + ") " + digits.substring(2, 6) + "-" + digits.substring(6);
        }
        return "(" + digits.substring(0, 2) + ") " + digits.substring(2, 7) + "-" + digits.substring(7, 11);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
