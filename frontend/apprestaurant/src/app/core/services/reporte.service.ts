import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReporteVentasResponse, ReportePedidosResponse, ReporteReservasResponse, ReporteEntregasResponse } from '../models/reporte.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ReporteService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/reportes`;

  reporteVentas(inicio: string, fin: string): Observable<ReporteVentasResponse> {
    const params = new HttpParams().set('inicio', inicio).set('fin', fin);
    return this.http.get<ReporteVentasResponse>(`${this.url}/ventas`, { params });
  }

  reportePedidos(inicio: string, fin: string): Observable<ReportePedidosResponse> {
    const params = new HttpParams().set('inicio', inicio).set('fin', fin);
    return this.http.get<ReportePedidosResponse>(`${this.url}/pedidos`, { params });
  }

  reporteReservas(inicio: string, fin: string): Observable<ReporteReservasResponse> {
    const params = new HttpParams().set('inicio', inicio).set('fin', fin);
    return this.http.get<ReporteReservasResponse>(`${this.url}/reservas`, { params });
  }

  reporteEntregas(inicio: string, fin: string): Observable<ReporteEntregasResponse[]> {
    const params = new HttpParams().set('inicio', inicio).set('fin', fin);
    return this.http.get<ReporteEntregasResponse[]>(`${this.url}/entregas`, { params });
  }
}
