import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CocinaPedidoResponse } from '../models/cocina.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class CocinaService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/cocina`;

  listarPendientes(): Observable<CocinaPedidoResponse[]> {
    return this.http.get<CocinaPedidoResponse[]>(`${this.url}/pendientes`);
  }

  listarPreparacion(): Observable<CocinaPedidoResponse[]> {
    return this.http.get<CocinaPedidoResponse[]>(`${this.url}/preparacion`);
  }

  listarListos(): Observable<CocinaPedidoResponse[]> {
    return this.http.get<CocinaPedidoResponse[]>(`${this.url}/listos`);
  }

  cambiarEstado(idPedido: number, estado: string): Observable<void> {
    return this.http.put<void>(`${this.url}/${idPedido}/estado`, { estado });
  }
}
