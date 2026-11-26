package com.gamevault.repository;

import com.gamevault.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Usado para nao deixar cadastrar duas categorias com o mesmo nome
    boolean existsByNomeIgnoreCase(String nome);
}
