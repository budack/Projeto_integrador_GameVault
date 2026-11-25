package com.gamevault.service;

import com.gamevault.dto.request.JogoRequest;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.exception.RecursoNaoEncontradoException;
import com.gamevault.model.Categoria;
import com.gamevault.model.Jogo;
import com.gamevault.repository.CategoriaRepository;
import com.gamevault.repository.JogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
// Os nomes e assinaturas abaixo sao os que o JogoController usa - manter iguais.
@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final CategoriaRepository categoriaRepository;

    public JogoService(JogoRepository jogoRepository, CategoriaRepository categoriaRepository) {
        this.jogoRepository = jogoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> listarTodos() {
        return jogoRepository.findAll().stream().map(JogoResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public JogoResponse buscarPorId(Long id) {
        return JogoResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public JogoResponse cadastrar(JogoRequest request) {
        // TODO Isabella: regra "nao cadastrar jogo sem nome"
        Jogo jogo = new Jogo();
        preencher(jogo, request);
        return JogoResponse.fromEntity(jogoRepository.save(jogo));
    }

    @Transactional
    public JogoResponse atualizar(Long id, JogoRequest request) {
        Jogo jogo = buscarEntidade(id);
        preencher(jogo, request);
        return JogoResponse.fromEntity(jogoRepository.save(jogo));
    }

    @Transactional
    public void excluir(Long id) {
        if (!jogoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Jogo " + id + " nao encontrado");
        }
        jogoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> filtrar(String nome, String genero) {
        // TODO Isabella: implementar o filtro de verdade (Etapa 7, consulta 2).
        // Por enquanto ignora os filtros e devolve tudo.
        return listarTodos();
    }

    private void preencher(Jogo jogo, JogoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria " + request.categoriaId() + " nao encontrada"));
        jogo.setNome(request.nome());
        jogo.setGenero(request.genero());
        jogo.setDificuldade(request.dificuldade());
        jogo.setAnoLancamento(request.anoLancamento());
        jogo.setCategoria(categoria);
    }

    private Jogo buscarEntidade(Long id) {
        return jogoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Jogo " + id + " nao encontrado"));
    }
}
