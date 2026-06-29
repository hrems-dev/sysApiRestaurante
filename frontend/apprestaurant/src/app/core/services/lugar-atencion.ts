import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LugarAtencion, QRLugar } from '../models/lugar-atencion';

@Injectable({
  providedIn: 'root',
})
export class LugarAtencionService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/lugares';

  getAll(): Observable<LugarAtencion[]> {
    return this.http.get<LugarAtencion[]>(this.apiUrl);
  }

  getById(id: number): Observable<LugarAtencion> {
    return this.http.get<LugarAtencion>(`${this.apiUrl}/${id}`);
  }

  create(data: LugarAtencion): Observable<LugarAtencion> {
    return this.http.post<LugarAtencion>(this.apiUrl, data);
  }

  update(id: number, data: LugarAtencion): Observable<LugarAtencion> {
    return this.http.put<LugarAtencion>(`${this.apiUrl}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  generarQR(id: number): Observable<QRLugar> {
    return this.http.post<QRLugar>(`${this.apiUrl}/${id}/qr`, {});
  }
}
