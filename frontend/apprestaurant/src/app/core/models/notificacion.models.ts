export interface NotificacionRequest {
  idUsuario: number;
  idPedido?: number;
  idVenta?: number;
  tipoNotificacion: string;
  titulo: string;
  descripcion: string;
}

export interface NotificacionResponse {
  idNotificacion: number;
  idUsuario: number;
  idPedido: number;
  idVenta: number;
  tipoNotificacion: string;
  titulo: string;
  descripcion: string;
  leido: boolean;
  fechaNotificacion: string;
}
