package com.gamevault.controller;

import com.gamevault.dto.request.CategoriaRequest;
import com.gamevault.dto.response.CategoriaResponse;
import com.gamevault.dto.response.JogoResponse;
import com.gamevault.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // GET /categorias
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    // GET /categorias/{id} -> 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    // POST /categorias -> 201 (so ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponse> cadastrar(@RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse criada = categoriaService.cadastrar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    // GET /categorias/{id}/jogos -> jogos daquela categoria (filtro no path)
    @GetMapping("/{id}/jogos")
    public ResponseEntity<List<JogoResponse>> listarJogos(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.listarJogos(id));
    }
}
