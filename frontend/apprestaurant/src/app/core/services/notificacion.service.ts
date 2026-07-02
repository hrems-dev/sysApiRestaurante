import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificacionRequest, NotificacionResponse } from '../models/notificacion.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/notificaciones`;

  listarPorUsuario(idUsuario: number): Observable<NotificacionResponse[]> {
    return this.http.get<NotificacionResponse[]>(`${this.url}/usuario/${idUsuario}`);
  }

  obtenerNoLeidas(idUsuario: number): Observable<NotificacionResponse[]> {
    return this.http.get<NotificacionResponse[]>(`${this.url}/usuario/${idUsuario}/no-leidas`);
  }

  contarNoLeidas(idUsuario: number): Observable<number> {
    return this.http.get<number>(`${this.url}/usuario/${idUsuario}/contar`);
  }

  create(data: NotificacionRequest): Observable<NotificacionResponse> {
    return this.http.post<NotificacionResponse>(`${this.url}`, data);
  }

  marcarLeida(id: number): Observable<NotificacionResponse> {
    return this.http.put<NotificacionResponse>(`${this.url}/${id}/leer`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
