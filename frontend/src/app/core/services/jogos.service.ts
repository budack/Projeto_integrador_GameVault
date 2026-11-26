
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { JogoRequest } from '../../shared/models/jogo-request.model';
import { JogoResponse } from '../../shared/models/jogo-response.model';

@Injectable({
  providedIn: 'root'
})
export class JogosService {

  private readonly apiUrl = 'http://localhost:8080/jogos';

  constructor(private http: HttpClient) {}

  listar(): Observable<JogoResponse[]> {
    return this.http.get<JogoResponse[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<JogoResponse> {
    return this.http.get<JogoResponse>(`${this.apiUrl}/${id}`);
  }

  criar(dados: JogoRequest): Observable<JogoResponse> {
    return this.http.post<JogoResponse>(this.apiUrl, dados);
  }

  atualizar(id: number, dados: JogoRequest): Observable<JogoResponse> {
    return this.http.put<JogoResponse>(`${this.apiUrl}/${id}`, dados);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
