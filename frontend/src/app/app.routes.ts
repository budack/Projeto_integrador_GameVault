import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [

    {
        path: 'login',
        loadComponent: () =>
            import('./features/auth/login/login.component')
                .then(m => m.LoginComponent)
    },

    {
        path: '',
        canActivate: [authGuard],
        children: [
            {
                path: 'jogos',
                loadComponent: () =>
                    import('./features/jogos/lista-jogos/lista-jogos.component')
                        .then(m => m.ListaJogosComponent)
            },
            {
                path: 'jogos/novo',
                loadComponent: () =>
                    import('./features/jogos/cadastro-jogo/cadastro-jogo.component')
                        .then(m => m.CadastroJogoComponent)
            },
            {
                path: 'jogos/:id/editar',
                loadComponent: () =>
                    import('./features/jogos/editar-jogo/editar-jogo.component')
                        .then(m => m.EditarJogoComponent)
            },
            {
                path: 'jogos/:id',
                loadComponent: () =>
                    import('./features/jogos/detalhes-jogo/detalhes-jogo.component')
                        .then(m => m.DetalhesJogoComponent)
            },
            {
                path: '',
                redirectTo: 'jogos',
                pathMatch: 'full'
            }
        ]
    },

    {
        path: '**',
        redirectTo: 'login'
    },

    {
        path: 'jogos/novo',
        loadComponent: () =>
            import('./features/jogos/cadastro-jogo/cadastro-jogo.component')
                .then(m => m.CadastroJogoComponent)
    },

];
