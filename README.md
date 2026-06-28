# sysApiRestaurante

Construccion de Sisytema

## objetivo

## alcance

## arquitectura

[ 1 - modulos](docs/modulos.md)<br>
[ 2 - bloques](docs/modulos.md) <br>
| [ a - bloque](docs/blocs/1-bloque.md) <br>
| [ b - bloque](docs/blocs/1-bloque.md) <br>
| [ c - bloque](docs/blocs/1-bloque.md)

## Base de datos

# Base de datos del restaurante

## 1. Rol

```sql
Rol(
  idRol INT AUTO_INCREMENT PK,
  nombreRol VARCHAR(50) NOT NULL,
  descripcionRol VARCHAR(150) NULL,
  estadoRol BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 2. Usuario

```sql
Usuario(
  idUsuario INT AUTO_INCREMENT PK,
  idRol INT NOT NULL FK,
  nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
  nombres VARCHAR(80) NOT NULL,
  apellidos VARCHAR(80) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  passwordHash VARCHAR(255) NOT NULL,
  telefono VARCHAR(20) NULL,
  estadoUsuario BOOLEAN NOT NULL DEFAULT TRUE,
  fechaCreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ultimoAcceso TIMESTAMP NULL
)
```

## 3. LugarAtencion

```sql
LugarAtencion(
  idLugar INT AUTO_INCREMENT PK,
  nombreLugar VARCHAR(80) NOT NULL,
  tipoLugar ENUM('mesa','salon','terraza','recojo','despacho') NOT NULL,
  direccion VARCHAR(200) NULL,
  capacidadMaxima INT NULL,
  estadoLugar BOOLEAN NOT NULL DEFAULT TRUE,
  observacion VARCHAR(255) NULL
)
```

## 4. QRLugar

```sql
QRLugar(
  idQR INT AUTO_INCREMENT PK,
  idLugar INT NOT NULL FK,
  codigoQR VARCHAR(255) NOT NULL UNIQUE,
  urlQR VARCHAR(255) NULL,
  fechaGeneracion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fechaCaducidad TIMESTAMP NULL,
  estadoQR BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 5. MetodoPago

```sql
MetodoPago(
  idMetodoPago INT AUTO_INCREMENT PK,
  nombreMetodo VARCHAR(60) NOT NULL UNIQUE,
  descripcionMetodo VARCHAR(150) NULL,
  estadoMetodo BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 6. CategoriaProducto

```sql
CategoriaProducto(
  idCategoria INT AUTO_INCREMENT PK,
  nombreCategoria VARCHAR(80) NOT NULL UNIQUE,
  descripcionCategoria VARCHAR(150) NULL,
  estadoCategoria BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 7. Producto

```sql
Producto(
  idProducto INT AUTO_INCREMENT PK,
  idCategoria INT NOT NULL FK,
  nombreProducto VARCHAR(100) NOT NULL,
  descripcionProducto VARCHAR(255) NULL,
  precioProducto DECIMAL(10,2) NOT NULL,
  stockProducto INT NOT NULL DEFAULT 0,
  imagenProducto VARCHAR(255) NULL,
  estadoProducto BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 8. Reserva

```sql
Reserva(
  idReserva INT AUTO_INCREMENT PK,
  idUsuario INT NOT NULL FK,
  idLugar INT NOT NULL FK,
  fechaHoraReserva DATETIME NOT NULL,
  cantidadPersonas INT NOT NULL,
  estadoReserva ENUM('pendiente','confirmada','cancelada','completada') NOT NULL DEFAULT 'pendiente',
  adelantoReserva DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  observacionReserva VARCHAR(255) NULL,
  fechaCreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
```

## 9. Pago

```sql
Pago(
  idPago INT AUTO_INCREMENT PK,
  idMetodoPago INT NOT NULL FK,
  codigoPago VARCHAR(100) NOT NULL UNIQUE,
  montoPago DECIMAL(10,2) NOT NULL,
  estadoPago ENUM('pendiente','aprobado','rechazado','reembolsado') NOT NULL DEFAULT 'pendiente',
  fechaPago TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  referenciaPago VARCHAR(120) NULL,
  validadoPorUsuario INT NULL FK
)
```

## 10. ReservaPago

```sql
ReservaPago(
  idReservaPago INT AUTO_INCREMENT PK,
  idReserva INT NOT NULL FK,
  idPago INT NOT NULL FK,
  montoAplicado DECIMAL(10,2) NOT NULL,
  estadoPagoReserva ENUM('pendiente','aprobado','rechazado') NOT NULL DEFAULT 'pendiente',
  fechaAplicacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
```

## 11. Pedido

```sql
Pedido(
  idPedido INT AUTO_INCREMENT PK,
  idUsuario INT NOT NULL FK,
  idLugar INT NULL FK,
  idRepartidor INT NULL FK,
  tipoPedido ENUM('local','delivery','recojo') NOT NULL DEFAULT 'local',
  estadoPedido ENUM('pendiente','aceptado','en_preparacion','en_camino','entregado','cancelado') NOT NULL DEFAULT 'pendiente',
  direccionEntrega VARCHAR(255) NULL,
  observacionPedido VARCHAR(255) NULL,
  fechaHoraPedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  totalPedido DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  pagado BOOLEAN NOT NULL DEFAULT FALSE
)
```

## 12. DetallePedido

```sql
DetallePedido(
  idDetalle INT AUTO_INCREMENT PK,
  idPedido INT NOT NULL FK,
  idProducto INT NOT NULL FK,
  cantidad INT NOT NULL DEFAULT 1,
  precioUnitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) GENERATED ALWAYS AS (cantidad * precioUnitario) STORED,
  observacionDetalle VARCHAR(255) NULL,
  estadoDetalle BOOLEAN NOT NULL DEFAULT TRUE
)
```

## 13. DocVenta

```sql
DocVenta(
  idDocVenta INT AUTO_INCREMENT PK,
  tipoDocumento ENUM('boleta','factura','ticket') NOT NULL DEFAULT 'boleta',
  serie VARCHAR(10) NOT NULL,
  numero VARCHAR(20) NOT NULL,
  fechaEmision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estadoDocumento BOOLEAN NOT NULL DEFAULT TRUE,
  UNIQUE(serie, numero)
)
```

## 14. Venta

```sql
Venta(
  idVenta INT AUTO_INCREMENT PK,
  idPedido INT NOT NULL UNIQUE FK,
  idPago INT NOT NULL FK,
  idDocVenta INT NOT NULL FK,
  totalVenta DECIMAL(10,2) NOT NULL,
  fechaVenta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estadoVenta ENUM('abierta','cerrada','anulada') NOT NULL DEFAULT 'cerrada'
)
```

## 15. Notificacion

```sql
Notificacion(
  idNotificacion INT AUTO_INCREMENT PK,
  idUsuario INT NOT NULL FK,
  idPedido INT NULL FK,
  idVenta INT NULL FK,
  tipoNotificacion ENUM('pedido','pago','reserva','delivery','sistema') NOT NULL DEFAULT 'sistema',
  titulo VARCHAR(120) NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  leido BOOLEAN NOT NULL DEFAULT FALSE,
  fechaNotificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
```

## 16. Recomendaciones de mejora

```text
- Separar nombres de campos en español y con consistencia.
- Usar un solo nombre para "LugarAtencion" en todo el sistema.
- No duplicar pago dentro de detalle_pedido.
- Mantener reserva, pedido, pago y venta como procesos distintos.
- Agregar índices en todas las llaves foráneas.
- Crear reglas UNIQUE para códigos, correos y usernames.
- Usar ENUM solo donde el valor sea realmente fijo.
```

## 17. Orden sugerido de creación

```text
1. Rol
2. LugarAtencion
3. MetodoPago
4. CategoriaProducto
5. Usuario
6. QRLugar
7. Producto
8. Reserva
9. Pago
10. ReservaPago
11. Pedido
12. DetallePedido
13. DocVenta
14. Venta
15. Notificacion
```
