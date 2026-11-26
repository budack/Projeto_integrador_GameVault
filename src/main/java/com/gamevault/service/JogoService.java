package com.gamevault.service;

import com.gamevault.dto.request.JogoRequest;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.exception.RecursoNaoEncontradoException;
import com.gamevault.exception.RegraNegocioException;
import com.gamevault.model.Categoria;
import com.gamevault.model.Jogo;
import com.gamevault.repository.CategoriaRepository;
import com.gamevault.repository.JogoRepository;
import com.gamevault.repository.JogoSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Regras de negocio dos jogos.
// O controller so recebe/devolve HTTP; quem decide o que pode ou nao pode e aqui.
//
// Regras:
//  - nao cadastrar/atualizar jogo sem nome                 -> 400
//  - nao cadastrar/atualizar jogo com categoria inexistente -> 404
//  - nao atualizar jogo inexistente                        -> 404
//  - nao excluir jogo inexistente                          -> 404
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
        return paraResponse(jogoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public JogoResponse buscarPorId(Long id) {
        return JogoResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public JogoResponse cadastrar(JogoRequest request) {
        Jogo jogo = new Jogo();
        preencher(jogo, request);
        return JogoResponse.fromEntity(jogoRepository.save(jogo));
    }

    @Transactional
    public JogoResponse atualizar(Long id, JogoRequest request) {
        Jogo jogo = buscarEntidade(id); // 404 se o jogo nao existir
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

    // Consulta 2: GET /jogos?nome=Skyrim&genero=RPG
    // Os dois filtros sao opcionais e combinaveis: so entra no WHERE o que foi informado.
    @Transactional(readOnly = true)
    public List<JogoResponse> filtrar(String nome, String genero) {
        Specification<Jogo> filtro = Specification.allOf(
                JogoSpecifications.nomeContem(nome),
                JogoSpecifications.generoIgual(genero)
        );
        return paraResponse(jogoRepository.findAll(filtro));
    }

    // Consulta 1: GET /categorias/{id}/jogos
    @Transactional(readOnly = true)
    public List<JogoResponse> listarPorCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new RecursoNaoEncontradoException("Categoria " + categoriaId + " nao encontrada");
        }
        return paraResponse(jogoRepository.findByCategoriaId(categoriaId));
    }

    // ===== auxiliares =====

    private void preencher(Jogo jogo, JogoRequest request) {
        // O @Valid do controller ja barra isso, mas a regra fica garantida aqui tambem
        if (request.nome() == null || request.nome().isBlank()) {
            throw new RegraNegocioException("Nao e permitido cadastrar jogo sem nome");
        }
        if (request.categoriaId() == null) {
            throw new RegraNegocioException("A categoria e obrigatoria");
        }
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria " + request.categoriaId() + " nao encontrada"));

        jogo.setNome(request.nome().trim());
        jogo.setGenero(limpar(request.genero()));
        jogo.setDificuldade(limpar(request.dificuldade()));
        jogo.setAnoLancamento(request.anoLancamento());
        jogo.setCategoria(categoria);
    }

    private Jogo buscarEntidade(Long id) {
        return jogoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Jogo " + id + " nao encontrado"));
    }

    private List<JogoResponse> paraResponse(List<Jogo> jogos) {
        return jogos.stream().map(JogoResponse::fromEntity).toList();
    }

    private String limpar(String texto) {
        return texto == null ? null : texto.trim();
    }
}
