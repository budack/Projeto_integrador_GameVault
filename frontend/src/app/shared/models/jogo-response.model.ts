import { CategoriaResponse } from './categoria-response.model';

export interface JogoResponse {
  id: number;
  nome: string;
  genero: string;
  dificuldade: string | null;
  anoLancamento: number | null;
  categoria: CategoriaResponse;
  imagemUrl: string | null;
}