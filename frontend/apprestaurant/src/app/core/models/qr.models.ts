export interface QRLugarRequest {
  idLugar: number;
  codigoQR: string;
  urlQR?: string;
}

export interface QRLugarResponse {
  idQR: number;
  idLugar: number;
  nombreLugar: string;
  codigoQR: string;
  urlQR: string;
  fechaGeneracion: string;
  estadoQR: boolean;
}
