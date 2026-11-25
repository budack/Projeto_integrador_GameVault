package com.gamevault.exception;

// Lance esta excecao quando um ID nao existir -> a API responde 404.
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
