package com.gamevault.repository;

import com.gamevault.model.Jogo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

// JpaRepository              -> CRUD pronto (findAll, findById, save, deleteById, existsById...)
// JpaSpecificationExecutor   -> permite montar filtros combinaveis (ver JogoSpecifications)
public interface JogoRepository extends JpaRepository<Jogo, Long>, JpaSpecificationExecutor<Jogo> {

    // Consulta 1 (filtro no path): GET /categorias/{id}/jogos
    // O Spring gera o SQL sozinho pelo nome do metodo:
    //   SELECT * FROM jogos WHERE categoria_id = ?
    // @EntityGraph -> ja traz a categoria junto (1 consulta so, em vez de 1 por jogo)
    @EntityGraph(attributePaths = "categoria")
    List<Jogo> findByCategoriaId(Long categoriaId);

    // Mesmo findAll de sempre, mas trazendo a categoria junto
    @Override
    @EntityGraph(attributePaths = "categoria")
    List<Jogo> findAll();

    // Consulta 2 (multiplos parametros): GET /jogos?nome=...&genero=...
    @Override
    @EntityGraph(attributePaths = "categoria")
    List<Jogo> findAll(Specification<Jogo> spec);
}
