package com.gamevault.dto.request;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
// TODO Isabella: adicionar validacoes (@NotBlank no nome, @NotNull na categoriaId...)
public record JogoRequest(
        String nome,
        String genero,
        String dificuldade,
        Integer anoLancamento,
        Long categoriaId
) {}
