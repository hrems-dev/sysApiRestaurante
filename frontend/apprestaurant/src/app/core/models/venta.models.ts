export interface VentaRequest {
  idPedido: number;
  idPago: number;
  tipoDocumento: string;
}

export interface VentaResponse {
  idVenta: number;
  idPedido: number;
  idPago: number;
  idDocVenta: number;
  tipoDocumento: string;
  serie: string;
  numero: string;
  totalVenta: number;
  fechaVenta: string;
  estadoVenta: string;
}

export interface DocVentaRequest {
  tipoDocumento: string;
  serie: string;
  numero: string;
}

export interface DocVentaResponse {
  idDocVenta: number;
  tipoDocumento: string;
  serie: string;
  numero: string;
  fechaEmision: string;
  estadoDocumento: string;
}
