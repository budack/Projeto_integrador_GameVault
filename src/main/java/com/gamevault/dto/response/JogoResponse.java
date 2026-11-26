package com.gamevault.dto.response;

import com.gamevault.model.Jogo;

// O que a API DEVOLVE (nunca expomos a entidade direto).
public record JogoResponse(
        Long id,
        String nome,
        String genero,
        String dificuldade,
        Integer anoLancamento,
        CategoriaResponse categoria
) {
    public static JogoResponse fromEntity(Jogo jogo) {
        return new JogoResponse(
                jogo.getId(),
                jogo.getNome(),
                jogo.getGenero(),
                jogo.getDificuldade(),
                jogo.getAnoLancamento(),
                CategoriaResponse.fromEntity(jogo.getCategoria())
        );
    }
}
