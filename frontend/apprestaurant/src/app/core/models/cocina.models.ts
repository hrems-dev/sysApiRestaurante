export interface CocinaDetalleDTO {
  idDetalle: number;
  idProducto: number;
  nombreProducto: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  observacionDetalle: string;
}

export interface CocinaPedidoResponse {
  idPedido: number;
  mesa: string;
  productos: CocinaDetalleDTO[];
  estadoPedido: string;
  fechaHoraPedido: string;
  observacion: string;
}
