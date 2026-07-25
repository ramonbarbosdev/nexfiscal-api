package com.nexfiscal_api.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexfiscal_api.dto.ApiErrorResponse;
import com.nexfiscal_api.dto.FieldErrorDTO;
import com.nexfiscal_api.exception.BusinessException;
import com.nexfiscal_api.exception.ConflictException;
import com.nexfiscal_api.exception.ResourceNotFoundException;
import com.nexfiscal_api.exception.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<FieldErrorDTO> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> new FieldErrorDTO(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        return ResponseEntity.badRequest().body(ApiErrorResponse.builder()
                .status(400)
                .error("VALIDATION_ERROR")
                .message("Dados inválidos")
                .details(errors)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(ApiErrorResponse.builder()
                .status(400)
                .error("VALIDATION_ERROR")
                .message("Dados inválidos")
                .details(errors)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        return buildResponse(404, "NOT_FOUND", ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request) {
        return buildResponse(401, "UNAUTHORIZED", ex.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {
        return buildResponse(403, "ACCESS_DENIED", ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {
        return buildResponse(422, "BUSINESS_ERROR", ex.getMessage(), request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {
        return buildResponse(409, "CONFLICT", ex.getMessage(), request);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            org.springframework.dao.DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String detail = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiErrorResponse.builder()
                .status(409)
                .error("DATA_INTEGRITY")
                .message("Registro duplicado ou inconsistente.")
                .debugMessage(detail)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return ResponseEntity.status(500).body(ApiErrorResponse.builder()
                .status(500)
                .error("INTERNAL_ERROR")
                .message("Erro interno inesperado")
                .debugMessage(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            int status,
            String error,
            String message,
            HttpServletRequest request) {
        return ResponseEntity.status(status).body(ApiErrorResponse.builder()
                .status(status)
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
