package com.gamevault.dto.request;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
// TODO Isabella: adicionar validacoes (@NotBlank no nome...)
public record CategoriaRequest(
        String nome,
        String descricao
) {}
