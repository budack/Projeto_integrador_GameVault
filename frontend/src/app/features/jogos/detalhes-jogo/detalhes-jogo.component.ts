import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { JogosService } from '../../../core/services/jogos.service';
import { JogoResponse } from '../../../shared/models/jogo-response.model';

@Component({
  selector: 'app-detalhes-jogo',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detalhes-jogo.component.html',
  styleUrl: './detalhes-jogo.component.css'
})
export class DetalhesJogoComponent implements OnInit {

  jogo: JogoResponse | null = null;
  carregando = true;
  erro = '';

  constructor(
    private jogosService: JogosService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!id || isNaN(id)) {
      this.erro = 'ID do jogo inválido.';
      this.carregando = false;
      return;
    }

    this.jogosService.buscarPorId(id).subscribe({
      next: (dados) => {
        this.jogo = dados;
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao buscar jogo:', erro);
        this.erro = 'Não foi possível carregar os detalhes do jogo.';
        this.carregando = false;
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/jogos']);
  }


excluir(): void {
  if (!this.jogo) {
    return;
  }

  const confirmar = confirm(
    `Tem certeza que deseja excluir "${this.jogo.nome}"?`
  );

  if (!confirmar) {
    return;
  }

  this.carregando = true;
  this.erro = '';

  this.jogosService.excluir(this.jogo.id).subscribe({
    next: () => {
      alert('Jogo excluído com sucesso!');
      this.router.navigate(['/jogos']);
    },
    error: (erro) => {
      console.error('Erro ao excluir jogo:', erro);
      this.erro = 'Não foi possível excluir o jogo. Verifique suas permissões.';
      this.carregando = false;
    }
  });
}

}