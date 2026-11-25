package com.gamevault.repository;

import com.gamevault.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
// TODO Isabella: metodo para buscar jogos por categoria (ex: findByCategoriaId)
// TODO Isabella: filtros combinaveis (ex: estender JpaSpecificationExecutor<Jogo>)
public interface JogoRepository extends JpaRepository<Jogo, Long> {
}
