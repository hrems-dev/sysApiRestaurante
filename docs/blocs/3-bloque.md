# Bloque 3: módulos necesarios

## 1. Cocina

### Carpeta

```text
modules/cocina/
```

### Necesario

- Servicios para listar pedidos pagados.
- Servicios para cambiar estado a en_preparacion.
- Servicios para cambiar estado a listo.
- Servicios para marcar entregado.
- DTOs de vista de cocina.
- Controlador de cocina.
- Pantalla de cocina en tiempo real.
- Filtros por mesa, estado y tipo de pedido.

### Tablas relacionadas

- `Pedido`
- `DetallePedido`
- `Pago`
- `Venta`

---

## 2. Contabilidad

### Carpeta

```text
modules/contabilidad/
```

### Necesario

- Servicios para ventas diarias.
- Servicios para pagos confirmados.
- Servicios para pagos en efectivo validados.
- Servicios para pagos pendientes.
- Servicios para anulaciones.
- Servicios para reembolsos.
- Servicios para cierre de caja.
- DTOs financieros.
- Controlador de contabilidad.
- Panel de reportes contables.

### Tablas relacionadas

- `Pago`
- `Venta`
- `DocVenta`
- `ReservaPago`
- `Pedido`

---

## 3. Delivery

### Carpeta

```text
modules/delivery/
```

### Necesario

- Entidad o lógica sobre pedido delivery.
- Asignación de repartidor.
- Seguimiento de estados.
- Cambio a en_camino.
- Cambio a entregado.
- Controlador de delivery.
- Vista de seguimiento.
- Integración con usuario repartidor y pedido.

### Tablas relacionadas

- `Pedido`
- `Usuario`

---

## 4. Facturación

### Carpeta

```text
modules/facturacion/
```

### Necesario

- Emisión de boleta.
- Emisión de factura.
- Emisión de ticket.
- Generación de serie y correlativo.
- Controlador de facturación.
- DTOs de comprobante.
- Integración con venta y documento.

### Tablas relacionadas

- `Venta`
- `DocVenta`

---

## 5. Reportes

### Carpeta

```text
modules/reportes/
```

### Necesario

- Reporte de ventas.
- Reporte de pedidos.
- Reporte de reservas.
- Reporte de mesas.
- Reporte de entregas.
- Reporte por usuario.
- Reporte por turno.
- DTOs de resultados.
- Controlador de reportes.

### Tablas relacionadas

- `Pedido`
- `Pago`
- `Reserva`
- `Venta`
- `LugarAtencion`
- `Usuario`

---

## 6. Notificaciones operativas

### Carpeta

```text
modules/notificacion/
```

### Necesario

- Notificación de pedido recibido.
- Notificación de pago validado.
- Notificación de reserva confirmada.
- Notificación de pedido en cocina.
- Notificación de entrega finalizada.
- Cambio de estado leída/no leída.

### Tablas relacionadas

- `Notificacion`

---

# Orden interno del bloque 3

```text
1. cocina
2. contabilidad
3. delivery
4. facturacion
5. reportes
6. notificacion
```

---

# Dependencias del bloque 3

```text
Necesita bloque 1:
- Usuario
- LugarAtencion
- Seguridad

Necesita bloque 2:
- Producto
- Pedido
- DetallePedido
- MetodoPago
- Pago
- Reserva
- ReservaPago
- Venta
- DocVenta
```

---

# Qué construir en este bloque

## Backend

```text
- Servicios operativos
- Controladores de cocina, contabilidad, delivery, facturación y reportes
- Reglas de cambio de estado
- DTOs de consulta y resumen
- Lógica de cierre y trazabilidad
```

## Frontend

```text
- Panel cocina
- Panel contabilidad
- Panel delivery
- Facturación
- Reportes
- Notificaciones de estados
```

---

# Flujo funcional del bloque 3

```text
Pedido pagado -> Cocina
Pedido preparado -> Entregado
Pedido entregado -> Venta cerrada
Venta cerrada -> Documento de venta
Documento de venta -> Reporte contable
```

---

# Resultado esperado del bloque 3

```text
- control de cocina
- control financiero
- delivery
- facturación
- reportes
- notificaciones
```
