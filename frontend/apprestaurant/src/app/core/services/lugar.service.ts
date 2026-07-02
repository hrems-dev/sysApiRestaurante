import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LugarAtencionRequest, LugarAtencionResponse } from '../models/lugar.models';
import { QRLugarRequest, QRLugarResponse } from '../models/qr.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class LugarService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/lugares`;

  findAll(): Observable<LugarAtencionResponse[]> {
    return this.http.get<LugarAtencionResponse[]>(`${this.url}`);
  }

  findAllActive(): Observable<LugarAtencionResponse[]> {
    return this.http.get<LugarAtencionResponse[]>(`${this.url}/activos`);
  }

  findById(id: number): Observable<LugarAtencionResponse> {
    return this.http.get<LugarAtencionResponse>(`${this.url}/${id}`);
  }

  findByTipo(tipo: string): Observable<LugarAtencionResponse[]> {
    return this.http.get<LugarAtencionResponse[]>(`${this.url}/tipo/${tipo}`);
  }

  create(data: LugarAtencionRequest): Observable<LugarAtencionResponse> {
    return this.http.post<LugarAtencionResponse>(`${this.url}`, data);
  }

  update(id: number, data: LugarAtencionRequest): Observable<LugarAtencionResponse> {
    return this.http.put<LugarAtencionResponse>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}

@Injectable({ providedIn: 'root' })
export class QRService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/qr`;

  generarQR(data: QRLugarRequest): Observable<QRLugarResponse> {
    return this.http.post<QRLugarResponse>(`${this.url}/generar`, data);
  }

  obtenerQRPorLugar(idLugar: number): Observable<QRLugarResponse> {
    return this.http.get<QRLugarResponse>(`${this.url}/lugar/${idLugar}`);
  }

  validarQR(codigoQR: string): Observable<{ valido: boolean }> {
    return this.http.get<{ valido: boolean }>(`${this.url}/validar/${codigoQR}`);
  }

  desactivarQR(idQR: number): Observable<void> {
    return this.http.put<void>(`${this.url}/desactivar/${idQR}`, {});
  }
}
