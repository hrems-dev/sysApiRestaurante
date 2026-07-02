export interface PagoRequest {
  idMetodoPago: number;
  codigoPago: string;
  montoPago: number;
  referenciaPago?: string;
}

export interface PagoResponse {
  idPago: number;
  nombreMetodo: string;
  codigoPago: string;
  montoPago: number;
  estadoPago: string;
  fechaPago: string;
  referenciaPago: string;
}

export interface MetodoPagoRequest {
  nombreMetodo: string;
  descripcionMetodo?: string;
}

export interface MetodoPagoResponse {
  idMetodoPago: number;
  nombreMetodo: string;
  descripcionMetodo: string;
  estadoMetodo: boolean;
}

export interface ValidacionPagoRequest {
  codigoPago: string;
  estadoPago: string;
}
