export interface ConfiguracionRequest {
  clave: string;
  valor: string;
  descripcion?: string;
}

export interface ConfiguracionResponse {
  idConfig: number;
  clave: string;
  valor: string;
  descripcion: string;
  estadoConfig: boolean;
}
