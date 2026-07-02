import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FacturaRequest, FacturaResponse, ComprobanteResponse } from '../models/facturacion.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class FacturacionService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/facturacion`;

  listarComprobantes(): Observable<ComprobanteResponse[]> {
    return this.http.get<ComprobanteResponse[]>(`${this.url}/comprobantes`);
  }

  obtenerComprobante(id: number): Observable<ComprobanteResponse> {
    return this.http.get<ComprobanteResponse>(`${this.url}/comprobantes/${id}`);
  }

  emitirComprobante(data: FacturaRequest): Observable<FacturaResponse> {
    return this.http.post<FacturaResponse>(`${this.url}/emitir`, data);
  }
}
