
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';
import { LoginRequest } from '../../../shared/models/login-request.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email = '';
  senha = '';
  erro = '';
  carregando = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  entrar(): void {
    console.log(
      'AuthGuard executado. Autenticado:',
      this.authService.isAuthenticated()
    );

    if (!this.email || !this.senha) {
      console.log('2. Email ou senha estão vazios');
      this.erro = 'Preencha email e senha.';
      return;
    }

    console.log('3. Preparando chamada ao AuthService');

    this.carregando = true;
    this.erro = '';

    this.authService.login({
      email: this.email,
      senha: this.senha
    }).subscribe({
      next: (resposta) => {
        console.log('1. Login realizado:', resposta);

        console.log(
          '2. Token armazenado:',
          this.authService.getToken()
        );

        console.log(
          '3. Autenticado:',
          this.authService.isAuthenticated()
        );

        this.carregando = false;

        this.router.navigate(['/jogos']).then(resultado => {
          console.log('4. Navegação:', resultado);
          console.log('5. URL atual:', this.router.url);
        });
      },
    });
  }
}

