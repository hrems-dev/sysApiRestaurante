import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MesaRequest, MesaResponse } from '../models/mesa.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class MesaService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/mesas`;

  findAll(): Observable<MesaResponse[]> {
    return this.http.get<MesaResponse[]>(`${this.url}`);
  }

  findById(id: number): Observable<MesaResponse> {
    return this.http.get<MesaResponse>(`${this.url}/${id}`);
  }

  findByLugar(idLugar: number): Observable<MesaResponse[]> {
    return this.http.get<MesaResponse[]>(`${this.url}/lugar/${idLugar}`);
  }

  save(data: MesaRequest): Observable<MesaResponse> {
    return this.http.post<MesaResponse>(`${this.url}`, data);
  }

  update(id: number, data: MesaRequest): Observable<MesaResponse> {
    return this.http.put<MesaResponse>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
