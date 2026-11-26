export interface JogoRequest {
  nome: string;
  genero: string;
  dificuldade: string | null;
  anoLancamento: number | null;
  categoriaId: number;
  imagemUrl: string | null;
}