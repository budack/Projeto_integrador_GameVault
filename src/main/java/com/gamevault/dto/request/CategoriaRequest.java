package com.gamevault.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// O que o cliente ENVIA para cadastrar uma categoria.
public record CategoriaRequest(

        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 100, message = "O nome pode ter no maximo 100 caracteres")
        String nome,

        @Size(max = 255, message = "A descricao pode ter no maximo 255 caracteres")
        String descricao
) {}
