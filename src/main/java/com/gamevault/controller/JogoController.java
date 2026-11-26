package com.gamevault.controller;

import com.gamevault.dto.request.JogoRequest;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.service.JogoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


//  Endpoints de jogos.
//  GET             -> qualquer usuario logado (ADMIN ou COMUM)
//  POST/PUT/DELETE -> so ADMIN (@PreAuthorize)

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }


//      GET /jogos -> lista todos
//      GET /jogos?nome=Skyrim&genero=RPG -> lista filtrando (parametros opcionais)

    @GetMapping
    public ResponseEntity<List<JogoResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String genero) {

        if (nome == null && genero == null) {
            return ResponseEntity.ok(jogoService.listarTodos());
        }
        return ResponseEntity.ok(jogoService.filtrar(nome, genero));
    }

//     GET /jogos/{id} -> 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<JogoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jogoService.buscarPorId(id));
    }

    // POST /jogos -> 201 Created + header Location com a URL do novo jogo
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JogoResponse> cadastrar(@RequestBody @Valid JogoRequest request) {
        JogoResponse criado = jogoService.cadastrar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    // PUT /jogos/{id} -> 200 ou 404
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JogoResponse> atualizar(@PathVariable Long id,
                                                  @RequestBody @Valid JogoRequest request) {
        return ResponseEntity.ok(jogoService.atualizar(id, request));
    }

    // DELETE /jogos/{id} -> 204 No Content ou 404
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        jogoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
