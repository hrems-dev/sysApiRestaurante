import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstadoModulosResponse, ImportacionExcelResponse, OnboardingRestauranteRequest, OnboardingRestauranteResponse } from '../models/onboarding.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class OnboardingService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/onboarding`;

  crearRestaurante(data: OnboardingRestauranteRequest): Observable<OnboardingRestauranteResponse> {
    return this.http.post<OnboardingRestauranteResponse>(`${this.url}`, data);
  }

  obtenerEstado(): Observable<OnboardingRestauranteResponse> {
    return this.http.get<OnboardingRestauranteResponse>(`${this.url}/estado`);
  }

  obtenerRestaurante(): Observable<OnboardingRestauranteResponse> {
    return this.http.get<OnboardingRestauranteResponse>(`${this.url}/restaurante`);
  }

  obtenerEstadoModulos(): Observable<EstadoModulosResponse> {
    return this.http.get<EstadoModulosResponse>(`${this.url}/estado-modulos`);
  }

  descargarPlantilla(): Observable<Blob> {
    return this.http.get(`${this.url}/plantilla-excel`, { responseType: 'blob' });
  }

  importarExcel(archivo: File): Observable<ImportacionExcelResponse> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    return this.http.post<ImportacionExcelResponse>(`${this.url}/importar-excel`, formData);
  }
}
