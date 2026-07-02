export interface LugarAtencionRequest {
  nombreLugar: string;
  tipoLugar: string;
  direccion?: string;
  capacidadMaxima?: number;
  observacion?: string;
}

export interface LugarAtencionResponse {
  idLugar: number;
  nombreLugar: string;
  tipoLugar: string;
  direccion: string;
  capacidadMaxima: number;
  estadoLugar: boolean;
  observacion: string;
}
