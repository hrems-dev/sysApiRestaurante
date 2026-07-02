export interface ProductoRequest {
  idCategoria: number;
  nombreProducto: string;
  descripcionProducto?: string;
  precioProducto: number;
  stockProducto?: number;
  imagenProducto?: string;
}

export interface ProductoResponse {
  idProducto: number;
  idCategoria: number;
  nombreCategoria: string;
  nombreProducto: string;
  descripcionProducto: string;
  precioProducto: number;
  stockProducto: number;
  imagenProducto: string;
  estadoProducto: boolean;
}

export interface ProductoStockRequest {
  stock: number;
}
