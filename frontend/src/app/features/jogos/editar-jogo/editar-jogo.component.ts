
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { JogosService } from '../../../core/services/jogos.service';
import { CategoriasService } from '../../../core/services/categorias.service';

import { JogoRequest } from '../../../shared/models/jogo-request.model';
import { JogoResponse } from '../../../shared/models/jogo-response.model';
import { CategoriaResponse } from '../../../shared/models/categoria-response.model';

@Component({
  selector: 'app-editar-jogo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-jogo.component.html',
  styleUrl: './editar-jogo.component.css'
})
export class EditarJogoComponent implements OnInit {

  id!: number;

  categorias: CategoriaResponse[] = [];

  jogo: JogoRequest = {
    nome: '',
    genero: '',
    dificuldade: '',
    anoLancamento: null,
    categoriaId: 0,
    imagemUrl: ''
  };

  carregando = false;
  erro = '';
  sucesso = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private jogosService: JogosService,
    private categoriasService: CategoriasService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    if (!Number.isInteger(this.id) || this.id <= 0) {
      this.erro = 'ID de jogo inválido.';
      return;
    }

    this.carregarCategorias();
    this.carregarJogo();
  }

  carregarCategorias(): void {
    this.categoriasService.listar().subscribe({
      next: dados => this.categorias = dados,
      error: erro => {
        console.error(erro);
        this.erro = 'Não foi possível carregar as categorias.';
      }
    });
  }

  carregarJogo(): void {
    this.carregando = true;

    this.jogosService.buscarPorId(this.id).subscribe({
      next: (dados: JogoResponse) => {
        this.jogo = {
          nome: dados.nome,
          genero: dados.genero,
          dificuldade: dados.dificuldade,
          anoLancamento: dados.anoLancamento,
          categoriaId: dados.categoria.id,
          imagemUrl: dados.imagemUrl
        };

        this.carregando = false;
      },
      error: erro => {
        console.error(erro);
        this.erro = 'Não foi possível carregar os dados do jogo.';
        this.carregando = false;
      }
    });
  }

  atualizar(): void {
    this.erro = '';
    this.sucesso = '';

    if (
      !this.jogo.nome.trim() ||
      !this.jogo.genero.trim() ||
      !this.jogo.categoriaId
    ) {
      this.erro = 'Preencha o nome, gênero e categoria.';
      return;
    }

    this.carregando = true;

    this.jogosService.atualizar(this.id, this.jogo).subscribe({
      next: () => {
        this.sucesso = 'Jogo atualizado com sucesso!';
        this.carregando = false;

        setTimeout(() => {
          this.router.navigate(['/jogos', this.id]);
        }, 1200);
      },
      error: erro => {
        console.error(erro);
        this.erro = 'Não foi possível atualizar o jogo. Verifique suas permissões.';
        this.carregando = false;
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/jogos', this.id]);
  }
}
