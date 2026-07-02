import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VentaRequest, VentaResponse } from '../models/venta.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class VentaService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/ventas`;

  findAll(): Observable<VentaResponse[]> {
    return this.http.get<VentaResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<VentaResponse> {
    return this.http.get<VentaResponse>(`${this.url}/${id}`);
  }

  findByIdPedido(idPedido: number): Observable<VentaResponse> {
    return this.http.get<VentaResponse>(`${this.url}/pedido/${idPedido}`);
  }

  create(data: VentaRequest): Observable<VentaResponse> {
    return this.http.post<VentaResponse>(`${this.url}`, data);
  }

  cerrarVenta(id: number): Observable<VentaResponse> {
    return this.http.put<VentaResponse>(`${this.url}/${id}/cerrar`, {});
  }

  anularVenta(id: number): Observable<VentaResponse> {
    return this.http.put<VentaResponse>(`${this.url}/${id}/anular`, {});
  }
}
