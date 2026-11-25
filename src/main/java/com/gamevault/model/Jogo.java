package com.gamevault.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lado "N" do relacionamento 1:N -> varios Jogos pertencem a uma Categoria.

@Entity
@Table(name = "jogos")
@Getter
@Setter
@NoArgsConstructor
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 50)
    private String genero;

    @Column(length = 30)
    private String dificuldade;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;


//      @ManyToOne -> muitos jogos para uma categoria.
//      @JoinColumn -> cria a coluna "categoria_id" (chave estrangeira) na tabela jogos.
//      LAZY -> so busca a categoria no banco quando alguem realmente precisar dela.

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
