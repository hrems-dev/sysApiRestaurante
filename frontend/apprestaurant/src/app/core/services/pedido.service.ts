import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PedidoRequest, PedidoResponse, CambioEstadoRequest } from '../models/pedido.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class PedidoService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/pedidos`;

  findAll(): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<PedidoResponse> {
    return this.http.get<PedidoResponse>(`${this.url}/${id}`);
  }

  findByUsuario(idUsuario: number): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.url}/usuario/${idUsuario}`);
  }

  findByEstado(estado: string): Observable<PedidoResponse[]> {
    return this.http.get<PedidoResponse[]>(`${this.url}/estado/${estado}`);
  }

  create(data: PedidoRequest): Observable<PedidoResponse> {
    return this.http.post<PedidoResponse>(`${this.url}`, data);
  }

  cambiarEstado(id: number, data: CambioEstadoRequest): Observable<PedidoResponse> {
    return this.http.put<PedidoResponse>(`${this.url}/${id}/estado`, data);
  }

  marcarPagado(id: number): Observable<PedidoResponse> {
    return this.http.put<PedidoResponse>(`${this.url}/${id}/pagar`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
