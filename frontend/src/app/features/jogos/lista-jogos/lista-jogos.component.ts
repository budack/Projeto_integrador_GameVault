
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';


import { JogosService } from '../../../core/services/jogos.service';
import { JogoResponse } from '../../../shared/models/jogo-response.model';

@Component({
  selector: 'app-lista-jogos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], templateUrl: './lista-jogos.component.html',
  styleUrl: './lista-jogos.component.css'
})
export class ListaJogosComponent implements OnInit {

  jogos: JogoResponse[] = [];

  busca = '';
  generoSelecionado = '';

  carregando = false;
  erro = '';

  constructor(
    private jogosService: JogosService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.carregarJogos();
  }

  carregarJogos(): void {
    this.carregando = true;
    this.erro = '';

    this.jogosService.listar().subscribe({
      next: (dados) => {
        this.jogos = dados;
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao carregar jogos:', erro);
        this.erro = 'Não foi possível carregar os jogos.';
        this.carregando = false;
      }
    });
  }

  get generos(): string[] {
    return [...new Set(
      this.jogos.map(jogo => jogo.genero)
    )].sort();
  }

  get jogosFiltrados(): JogoResponse[] {
    return this.jogos.filter(jogo => {

      const correspondeNome = jogo.nome
        .toLowerCase()
        .includes(this.busca.toLowerCase());

      const correspondeGenero =
        !this.generoSelecionado ||
        jogo.genero === this.generoSelecionado;

      return correspondeNome && correspondeGenero;
    });
  }

  sair(): void {
    this.authService.logout();
  }
}
