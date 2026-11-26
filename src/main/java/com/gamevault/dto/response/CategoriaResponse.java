package com.gamevault.dto.response;

import com.gamevault.model.Categoria;

// O que a API DEVOLVE (nunca expomos a entidade direto).
public record CategoriaResponse(
        Long id,
        String nome,
        String descricao
) {
    public static CategoriaResponse fromEntity(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }
}
