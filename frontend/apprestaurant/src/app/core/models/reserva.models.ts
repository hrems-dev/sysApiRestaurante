export interface ReservaRequest {
  idUsuario: number;
  idLugar: number;
  fechaHoraReserva: string;
  cantidadPersonas: number;
  observacionReserva?: string;
}

export interface ReservaResponse {
  idReserva: number;
  nombreUsuario: string;
  nombreLugar: string;
  fechaHoraReserva: string;
  cantidadPersonas: number;
  estadoReserva: string;
  adelantoReserva: number;
  observacionReserva: string;
  fechaCreacion: string;
}

export interface ReservaPagoRequest {
  idReserva: number;
  idPago: number;
  montoAplicado: number;
}

export interface ReservaPagoResponse {
  idReservaPago: number;
  idReserva: number;
  idPago: number;
  montoAplicado: number;
  estadoPagoReserva: string;
  fechaAplicacion: string;
}
