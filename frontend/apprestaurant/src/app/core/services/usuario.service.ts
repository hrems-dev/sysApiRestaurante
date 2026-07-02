import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModels } from '../models/user.models';
import { RoleModels } from '../models/role.models';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/usuarios`;

  findAll(): Observable<UserModels[]> {
    return this.http.get<UserModels[]>(`${this.url}`);
  }

  findById(id: number): Observable<UserModels> {
    return this.http.get<UserModels>(`${this.url}/${id}`);
  }

  save(data: any): Observable<UserModels> {
    return this.http.post<UserModels>(`${this.url}`, data);
  }

  update(id: number, data: any): Observable<UserModels> {
    return this.http.put<UserModels>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}

@Injectable({ providedIn: 'root' })
export class RolService {
  private http = inject(HttpClient);
  private api = inject(ApiService);

  private url = `${this.api.baseUrl}/roles`;

  findAll(): Observable<RoleModels[]> {
    return this.http.get<RoleModels[]>(`${this.url}`);
  }

  findById(id: number): Observable<RoleModels> {
    return this.http.get<RoleModels>(`${this.url}/${id}`);
  }

  save(data: any): Observable<RoleModels> {
    return this.http.post<RoleModels>(`${this.url}`, data);
  }

  update(id: number, data: any): Observable<RoleModels> {
    return this.http.put<RoleModels>(`${this.url}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
