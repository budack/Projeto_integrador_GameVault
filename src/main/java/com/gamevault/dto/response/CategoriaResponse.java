package com.gamevault.dto.response;

import com.gamevault.model.Categoria;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
public record CategoriaResponse(
        Long id,
        String nome,
        String descricao
) {
    public static CategoriaResponse fromEntity(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }
}
