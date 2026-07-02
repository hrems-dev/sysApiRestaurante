import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservaRequest, ReservaResponse, ReservaPagoRequest, ReservaPagoResponse } from '../models/reserva.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ReservaService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/reservas`;

  findAll(): Observable<ReservaResponse[]> {
    return this.http.get<ReservaResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<ReservaResponse> {
    return this.http.get<ReservaResponse>(`${this.url}/${id}`);
  }

  findByUsuario(idUsuario: number): Observable<ReservaResponse[]> {
    return this.http.get<ReservaResponse[]>(`${this.url}/usuario/${idUsuario}`);
  }

  findByLugar(idLugar: number): Observable<ReservaResponse[]> {
    return this.http.get<ReservaResponse[]>(`${this.url}/lugar/${idLugar}`);
  }

  verificarDisponibilidad(lugar: number, fecha: string): Observable<{ disponible: boolean }> {
    const params = new HttpParams().set('lugar', lugar).set('fecha', fecha);
    return this.http.get<{ disponible: boolean }>(`${this.url}/disponibilidad`, { params });
  }

  create(data: ReservaRequest): Observable<ReservaResponse> {
    return this.http.post<ReservaResponse>(`${this.url}`, data);
  }

  confirmarReserva(id: number, montoAdelanto?: number): Observable<ReservaResponse> {
    return this.http.put<ReservaResponse>(`${this.url}/${id}/confirmar`, { montoAdelanto });
  }

  cancelarReserva(id: number): Observable<ReservaResponse> {
    return this.http.put<ReservaResponse>(`${this.url}/${id}/cancelar`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  createPago(data: ReservaPagoRequest): Observable<ReservaPagoResponse> {
    return this.http.post<ReservaPagoResponse>(`${this.url}/pago`, data);
  }

  findPagosByReserva(idReserva: number): Observable<ReservaPagoResponse[]> {
    return this.http.get<ReservaPagoResponse[]>(`${this.url}/pago/reserva/${idReserva}`);
  }
}
