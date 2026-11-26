package com.gamevault.service;

import com.gamevault.dto.request.CategoriaRequest;
import com.gamevault.dto.response.CategoriaResponse;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.exception.RecursoNaoEncontradoException;
import com.gamevault.exception.RegraNegocioException;
import com.gamevault.model.Categoria;
import com.gamevault.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Regras de negocio das categorias.
//  - nome obrigatorio           -> 400
//  - nome repetido              -> 400
//  - categoria inexistente      -> 404
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final JogoService jogoService;

    public CategoriaService(CategoriaRepository categoriaRepository, JogoService jogoService) {
        this.categoriaRepository = categoriaRepository;
        this.jogoService = jogoService;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll().stream().map(CategoriaResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        return CategoriaResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public CategoriaResponse cadastrar(CategoriaRequest request) {
        if (request.nome() == null || request.nome().isBlank()) {
            throw new RegraNegocioException("Nao e permitido cadastrar categoria sem nome");
        }
        String nome = request.nome().trim();
        if (categoriaRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException("Ja existe uma categoria com o nome '" + nome + "'");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(request.descricao() == null ? null : request.descricao().trim());
        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria));
    }

    // GET /categorias/{id}/jogos -> usa a consulta findByCategoriaId do JogoRepository
    @Transactional(readOnly = true)
    public List<JogoResponse> listarJogos(Long categoriaId) {
        return jogoService.listarPorCategoria(categoriaId);
    }

    private Categoria buscarEntidade(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria " + id + " nao encontrada"));
    }
}
