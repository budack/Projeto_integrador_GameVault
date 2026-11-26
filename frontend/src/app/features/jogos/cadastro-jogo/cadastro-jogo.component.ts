import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { JogosService } from '../../../core/services/jogos.service';
import { CategoriasService } from '../../../core/services/categorias.service';

import { JogoRequest } from '../../../shared/models/jogo-request.model';
import { CategoriaResponse } from '../../../shared/models/categoria-response.model';

@Component({
  selector: 'app-cadastro-jogo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro-jogo.component.html',
  styleUrl: './cadastro-jogo.component.css'
})
export class CadastroJogoComponent implements OnInit {

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
    private jogosService: JogosService,
    private categoriasService: CategoriasService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarCategorias();
  }

  carregarCategorias(): void {
    this.categoriasService.listar().subscribe({
      next: (dados) => {
        this.categorias = dados;
      },
      error: (erro) => {
        console.error('Erro ao carregar categorias:', erro);
        this.erro = 'Não foi possível carregar as categorias.';
      }
    });
  }

  cadastrar(): void {
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

    this.jogosService.criar(this.jogo).subscribe({
      next: () => {
        this.sucesso = 'Jogo cadastrado com sucesso!';
        this.carregando = false;

        setTimeout(() => {
          this.router.navigate(['/jogos']);
        }, 1200);
      },
      error: (erro) => {
        console.error('Erro ao cadastrar jogo:', erro);

        this.erro = 'Não foi possível cadastrar o jogo. Verifique suas permissões.';
        this.carregando = false;
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/jogos']);
  }
}