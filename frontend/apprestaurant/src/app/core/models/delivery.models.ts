export interface DeliveryRequest {
  idPedido: number;
  idRepartidor: number;
  direccionEntrega: string;
}

export interface DeliveryResponse {
  idPedido: number;
  nombreRepartidor: string;
  direccionEntrega: string;
  estadoPedido: string;
  fechaHoraPedido: string;
}

export interface SeguimientoDeliveryResponse {
  idPedido: number;
  estadoPedido: string;
  fechaHoraPedido: string;
  tiempoTranscurrido: string;
}
