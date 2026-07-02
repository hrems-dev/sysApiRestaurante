import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoriaProductoRequest, CategoriaProductoResponse } from '../models/categoria.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/categorias-producto`;

  findAll(): Observable<CategoriaProductoResponse[]> {
    return this.http.get<CategoriaProductoResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<CategoriaProductoResponse> {
    return this.http.get<CategoriaProductoResponse>(`${this.url}/${id}`);
  }

  create(data: CategoriaProductoRequest): Observable<CategoriaProductoResponse> {
    return this.http.post<CategoriaProductoResponse>(`${this.url}`, data);
  }

  update(id: number, data: CategoriaProductoRequest): Observable<CategoriaProductoResponse> {
    return this.http.put<CategoriaProductoResponse>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
