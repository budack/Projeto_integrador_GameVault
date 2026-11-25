package com.gamevault.service;

import com.gamevault.dto.request.CategoriaRequest;
import com.gamevault.dto.response.CategoriaResponse;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.exception.RecursoNaoEncontradoException;
import com.gamevault.model.Categoria;
import com.gamevault.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//  MINIMO - parte da Isabella (TODO Isabella: completar)
// Os nomes e assinaturas abaixo sao os que os controllers usam - manter iguais.
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
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
        // TODO Isabella: regras (nome obrigatorio, nome repetido...)
        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());
        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> listarJogos(Long categoriaId) {
        // TODO Isabella: trocar por uma consulta no JogoRepository (ex: findByCategoriaId)
        return buscarEntidade(categoriaId).getJogos().stream().map(JogoResponse::fromEntity).toList();
    }

    private Categoria buscarEntidade(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria " + id + " nao encontrada"));
    }
}
