export interface ReporteVentasResponse {
  periodo: string;
  totalVentas: number;
  cantidadVentas: number;
  ventasPorDia: { [key: string]: number };
}

export interface ReportePedidosResponse {
  periodo: string;
  totalPedidos: number;
  pedidosPorEstado: { [key: string]: number };
}

export interface ReporteReservasResponse {
  periodo: string;
  totalReservas: number;
  reservasPorEstado: { [key: string]: number };
}

export interface ReporteEntregasResponse {
  nombreRepartidor: string;
  cantidadEntregas: number;
  tiempoPromedioMinutos: number;
}

export interface VentaDiariaResponse {
  fecha: string;
  totalVentas: number;
  cantidadVentas: number;
  totalEfectivo: number;
  totalTarjeta: number;
  totalYape: number;
}

export interface ReporteContableResponse {
  periodo: string;
  ingresos: number;
  egresos: number;
  saldo: number;
  cantidadVentas: number;
  cantidadPedidos: number;
  cantidadReservas: number;
}
