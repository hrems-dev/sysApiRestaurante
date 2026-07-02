import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfiguracionRequest, ConfiguracionResponse } from '../models/configuracion.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ConfiguracionService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/configuracion`;

  obtenerTodas(): Observable<ConfiguracionResponse[]> {
    return this.http.get<ConfiguracionResponse[]>(`${this.url}`);
  }

  obtenerPorClave(clave: string): Observable<ConfiguracionResponse> {
    return this.http.get<ConfiguracionResponse>(`${this.url}/${clave}`);
  }

  crear(data: ConfiguracionRequest): Observable<ConfiguracionResponse> {
    return this.http.post<ConfiguracionResponse>(`${this.url}`, data);
  }

  actualizar(id: number, data: ConfiguracionRequest): Observable<ConfiguracionResponse> {
    return this.http.put<ConfiguracionResponse>(`${this.url}/${id}`, data);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
