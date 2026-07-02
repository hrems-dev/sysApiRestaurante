import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductoRequest, ProductoResponse, ProductoStockRequest } from '../models/producto.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/productos`;

  findAll(): Observable<ProductoResponse[]> {
    return this.http.get<ProductoResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<ProductoResponse> {
    return this.http.get<ProductoResponse>(`${this.url}/${id}`);
  }

  findByCategoria(idCategoria: number): Observable<ProductoResponse[]> {
    return this.http.get<ProductoResponse[]>(`${this.url}/categoria/${idCategoria}`);
  }

  findByNombre(nombre: string): Observable<ProductoResponse[]> {
    return this.http.get<ProductoResponse[]>(`${this.url}/buscar/${nombre}`);
  }

  create(data: ProductoRequest): Observable<ProductoResponse> {
    return this.http.post<ProductoResponse>(`${this.url}`, data);
  }

  update(id: number, data: ProductoRequest): Observable<ProductoResponse> {
    return this.http.put<ProductoResponse>(`${this.url}/${id}`, data);
  }

  updateStock(id: number, data: ProductoStockRequest): Observable<ProductoResponse> {
    return this.http.patch<ProductoResponse>(`${this.url}/${id}/stock`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
