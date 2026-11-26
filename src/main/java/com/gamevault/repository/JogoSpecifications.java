package com.gamevault.repository;

import com.gamevault.model.Jogo;
import org.springframework.data.jpa.domain.Specification;

// Cada metodo e um "pedaco" de WHERE. No service a gente junta so os que foram informados.
// Se o parametro vier vazio, o metodo devolve null e o Spring simplesmente ignora aquele filtro.
//
// Exemplo: GET /jogos?nome=sky&genero=rpg  vira
//   WHERE lower(nome) LIKE '%sky%' AND lower(genero) = 'rpg'
public final class JogoSpecifications {

    private JogoSpecifications() {
    }

    // nome contem o texto (ignorando maiusculas/minusculas)
    public static Specification<Jogo> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        String padrao = "%" + nome.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), padrao);
    }

    // genero igual (ignorando maiusculas/minusculas)
    public static Specification<Jogo> generoIgual(String genero) {
        if (genero == null || genero.isBlank()) {
            return null;
        }
        String valor = genero.trim().toLowerCase();
        return (root, query, cb) -> cb.equal(cb.lower(root.get("genero")), valor);
    }
}
