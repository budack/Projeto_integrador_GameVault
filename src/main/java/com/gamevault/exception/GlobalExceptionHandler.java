package com.gamevault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


//  Transforma excecoes em respostas HTTP com JSON padronizado.
//  Assim os controllers ficam limpos: e so lancar a excecao certa.

@RestControllerAdvice
public class GlobalExceptionHandler {

//     ID nao existe -> 404
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(RecursoNaoEncontradoException e) {
        return resposta(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    // Regra de negocio violada -> 400
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> regraNegocio(RegraNegocioException e) {
        return resposta(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // Falhou no @Valid (campo obrigatorio vazio etc.) -> 400 com a lista de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException e) {
        Map<String, String> campos = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));
        return resposta(HttpStatus.BAD_REQUEST, "Dados invalidos", campos);
    }

    // JSON mal formatado -> 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> jsonInvalido(HttpMessageNotReadableException e) {
        return resposta(HttpStatus.BAD_REQUEST, "JSON invalido ou mal formatado", null);
    }

    // Email/senha errados no login -> 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> naoAutenticado(AuthenticationException e) {
        return resposta(HttpStatus.UNAUTHORIZED, "Email ou senha invalidos", null);
    }

    private ResponseEntity<Map<String, Object>> resposta(HttpStatus status, String mensagem, Object detalhes) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now().toString());
        corpo.put("status", status.value());
        corpo.put("erro", mensagem);
        if (detalhes != null) {
            corpo.put("campos", detalhes);
        }
        return ResponseEntity.status(status).body(corpo);
    }
}
