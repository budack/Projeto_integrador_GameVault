package com.gamevault.exception;

// Lance esta excecao quando uma regra de negocio for violada -> a API responde 400.
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
