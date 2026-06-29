export interface LugarAtencion {
  idLugar?: number;
  nombreLugar: string;
  tipoLugar: string;
  direccion?: string;
  capacidadMaxima?: number;
  estadoLugar: boolean;
  observacion?: string;
}

export interface QRLugar {
  idQR?: number;
  codigoQR: string;
  urlQR?: string;
  estadoQR: boolean;
  lugarAtencion?: LugarAtencion;
}
