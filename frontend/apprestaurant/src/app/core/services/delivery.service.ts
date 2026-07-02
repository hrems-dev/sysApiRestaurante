import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DeliveryRequest, DeliveryResponse, SeguimientoDeliveryResponse } from '../models/delivery.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class DeliveryService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/delivery`;

  listarPedidosDelivery(): Observable<DeliveryResponse[]> {
    return this.http.get<DeliveryResponse[]>(`${this.url}`);
  }

  obtenerSeguimiento(idPedido: number): Observable<SeguimientoDeliveryResponse> {
    return this.http.get<SeguimientoDeliveryResponse>(`${this.url}/seguimiento/${idPedido}`);
  }

  asignarRepartidor(data: DeliveryRequest): Observable<DeliveryResponse> {
    return this.http.post<DeliveryResponse>(`${this.url}/asignar`, data);
  }

  actualizarEstado(idPedido: number, estado: string): Observable<void> {
    return this.http.put<void>(`${this.url}/${idPedido}/estado`, { estado });
  }
}
