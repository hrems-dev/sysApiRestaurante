import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VentaDiariaResponse, ReporteContableResponse } from '../models/reporte.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ContabilidadService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/contabilidad`;

  ventasDiarias(fecha: string): Observable<VentaDiariaResponse> {
    const params = new HttpParams().set('fecha', fecha);
    return this.http.get<VentaDiariaResponse>(`${this.url}/ventas-diarias`, { params });
  }

  resumen(inicio: string, fin: string): Observable<ReporteContableResponse> {
    const params = new HttpParams().set('inicio', inicio).set('fin', fin);
    return this.http.get<ReporteContableResponse>(`${this.url}/resumen`, { params });
  }
}
