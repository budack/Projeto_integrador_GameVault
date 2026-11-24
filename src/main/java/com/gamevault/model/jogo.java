package com.gamevault.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity



public class jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String genero;
    private String dificuldade;
    private Long ano_lancado;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
