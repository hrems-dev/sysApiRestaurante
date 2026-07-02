export interface OnboardingRestauranteRequest {
  nombreRestaurante: string;
  ruc?: string;
  direccion?: string;
  telefono?: string;
  email?: string;
  logoUrl?: string;
}

export interface OnboardingRestauranteResponse {
  idRestaurante: number;
  nombreRestaurante: string;
  ruc: string;
  direccion: string;
  telefono: string;
  email: string;
  logoUrl: string;
  estadoRestaurante: boolean;
  mensaje: string;
}

export interface ImportacionExcelResponse {
  totalImportados: number;
  totalErrores: number;
  errores: string[];
  mensaje: string;
}

export interface EstadoModulosResponse {
  restauranteRegistrado: boolean;
  datosBasicosRegistrados: boolean;
  configuracionCompleta: boolean;
}
