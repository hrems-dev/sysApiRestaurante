import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PagoRequest, PagoResponse, ValidacionPagoRequest } from '../models/pago.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/pagos`;

  findAll(): Observable<PagoResponse[]> {
    return this.http.get<PagoResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<PagoResponse> {
    return this.http.get<PagoResponse>(`${this.url}/${id}`);
  }

  findByCodigo(codigoPago: string): Observable<PagoResponse> {
    return this.http.get<PagoResponse>(`${this.url}/codigo/${codigoPago}`);
  }

  findByEstado(estado: string): Observable<PagoResponse[]> {
    return this.http.get<PagoResponse[]>(`${this.url}/estado/${estado}`);
  }

  create(data: PagoRequest): Observable<PagoResponse> {
    return this.http.post<PagoResponse>(`${this.url}`, data);
  }

  validarPago(id: number, data: ValidacionPagoRequest): Observable<PagoResponse> {
    return this.http.put<PagoResponse>(`${this.url}/${id}/validar`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}

@Injectable({ providedIn: 'root' })
export class MetodoPagoService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/metodos-pago`;

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}`);
  }

  findAllActive(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/activos`);
  }

  findById(id: number): Observable<any> {
    return this.http.get<any>(`${this.url}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post<any>(`${this.url}`, data);
  }

  update(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
