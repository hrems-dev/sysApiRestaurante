export interface DetallePedidoRequest {
  idProducto: number;
  cantidad: number;
  precioUnitario: number;
  observacionDetalle?: string;
}

export interface PedidoRequest {
  idUsuario: number;
  idLugar?: number;
  tipoPedido: string;
  direccionEntrega?: string;
  observacionPedido?: string;
  detalles: DetallePedidoRequest[];
}

export interface DetallePedidoResponse {
  idDetalle: number;
  idProducto: number;
  nombreProducto: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  observacionDetalle: string;
  estadoDetalle: string;
}

export interface PedidoResponse {
  idPedido: number;
  nombreUsuario: string;
  nombreLugar: string;
  tipoPedido: string;
  estadoPedido: string;
  direccionEntrega: string;
  observacionPedido: string;
  fechaHoraPedido: string;
  totalPedido: number;
  pagado: boolean;
  detalles: DetallePedidoResponse[];
}

export interface CambioEstadoRequest {
  estado: string;
}
