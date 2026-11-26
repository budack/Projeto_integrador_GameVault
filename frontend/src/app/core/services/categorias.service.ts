
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CategoriaRequest } from '../../shared/models/categoria-request.model';
import { CategoriaResponse } from '../../shared/models/categoria-response.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {

  private readonly apiUrl = 'http://localhost:8080/categorias';

  constructor(private http: HttpClient) {}

  listar(): Observable<CategoriaResponse[]> {
    return this.http.get<CategoriaResponse[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<CategoriaResponse> {
    return this.http.get<CategoriaResponse>(`${this.apiUrl}/${id}`);
  }

  criar(dados: CategoriaRequest): Observable<CategoriaResponse> {
    return this.http.post<CategoriaResponse>(this.apiUrl, dados);
  }
}
