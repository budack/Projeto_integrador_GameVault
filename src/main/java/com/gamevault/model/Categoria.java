package com.gamevault.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;

@Entity

public class Categoria {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @OneToMany
    private String descricao;
}
