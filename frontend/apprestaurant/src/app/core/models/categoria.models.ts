export interface CategoriaProductoRequest {
  nombreCategoria: string;
  descripcionCategoria?: string;
}

export interface CategoriaProductoResponse {
  idCategoria: number;
  nombreCategoria: string;
  descripcionCategoria: string;
  estadoCategoria: boolean;
}
