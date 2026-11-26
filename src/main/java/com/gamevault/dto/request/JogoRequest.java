package com.gamevault.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// O que o cliente ENVIA para cadastrar/atualizar um jogo.
// As anotacoes sao conferidas pelo @Valid no controller: se falhar -> 400 com a lista de campos.
public record JogoRequest(

        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 150, message = "O nome pode ter no maximo 150 caracteres")
        String nome,

        @NotBlank(message = "O genero e obrigatorio")
        @Size(max = 50, message = "O genero pode ter no maximo 50 caracteres")
        String genero,

        @Size(max = 30, message = "A dificuldade pode ter no maximo 30 caracteres")
        String dificuldade,

        @Min(value = 1950, message = "O ano de lancamento deve ser a partir de 1950")
        @Max(value = 2100, message = "O ano de lancamento e invalido")
        Integer anoLancamento,

        @NotNull(message = "A categoria e obrigatoria")
        Long categoriaId
) {}
