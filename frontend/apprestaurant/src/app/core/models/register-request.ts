export interface RegisterRequest {
  username: string;
  nombres: string;
  apellidos: string;
  email: string;
  password: string;
  telefono?: string;
  rolNombre: string;
}
