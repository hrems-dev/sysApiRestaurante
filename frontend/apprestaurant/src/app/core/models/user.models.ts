export interface UserModels {
  idUsuario: string;
  idRol: string;
  nombreUsuario: string;
  nombres: string;
  apellidos: string;
  email: string;
  telefono?: string;
  estadoUsuario: boolean;
  fechaCreacion?: string;
  ultimoAcceso?: string | null;
}
