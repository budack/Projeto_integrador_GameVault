package com.gamevault.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//  Lado "1" do relacionamento 1:N -> uma Categoria tem varios Jogos.

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {

    @Id // ATENCAO: tem que ser jakarta.persistence.Id (e nao org.springframework.data.annotation.Id)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o PostgreSQL gera o id (1, 2, 3...)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;


//      mappedBy = "categoria" -> quem "manda" no relacionamento e o campo
//      "categoria" da classe Jogo (e la que fica a coluna categoria_id).
//      Aqui e so o caminho inverso, para navegar categoria.getJogos().

    @OneToMany(mappedBy = "categoria")
    private List<Jogo> jogos = new ArrayList<>();
}
