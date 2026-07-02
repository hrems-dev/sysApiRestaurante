export interface MesaRequest {
  nombreMesa: string;
  lugar: { idLugar: number } | null;
  capacidad: number;
  estadoMesa?: boolean;
}

export interface MesaResponse {
  idMesa: number;
  nombreMesa: string;
  lugar: { idLugar: number; nombreLugar: string };
  capacidad: number;
  estadoMesa: boolean;
}
