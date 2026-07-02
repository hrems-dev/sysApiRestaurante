export interface FacturaRequest {
  idVenta: number;
  tipoDocumento: string;
}

export interface FacturaResponse {
  idDocVenta: number;
  tipoDocumento: string;
  serie: string;
  numero: string;
  totalVenta: number;
  fechaEmision: string;
}

export interface ComprobanteResponse {
  idDocVenta: number;
  tipoDocumento: string;
  serie: string;
  numero: string;
  totalVenta: number;
  fechaEmision: string;
  estadoDocumento: string;
  idVenta: number;
  idPedido: number;
}
