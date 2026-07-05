# Documento General del Proyecto

## Portada

- Universidad: [Completar]
- Facultad: [Completar]
- Carrera: [Completar]
- Título del proyecto: Sistema de Gestión Integral para Restaurante
- Integrantes: [Completar]
- Docente: [Completar]
- Fecha: 05 de julio de 2026

---

## Índice

- [1. Introducción](#1-introducción)
- [2. Objetivo del proyecto](#2-objetivo-del-proyecto)
- [3. Arquitectura general](#3-arquitectura-general)
- [4. Módulos principales](#4-módulos-principales)
- [5. Flujo de negocio principal](#5-flujo-de-negocio-principal)
- [6. Tecnologías utilizadas](#6-tecnologías-utilizadas)
- [7. Manual de usuario](#7-manual-de-usuario)
- [8. Perfiles de usuario](#8-perfiles-de-usuario)
- [9. Glosario](#9-glosario)

---

# Capítulo 1. Documentación Técnica del Proyecto

## 1. Introducción

El proyecto SysApiRestaurante es una solución web orientada a la gestión integral de un restaurante. El sistema permite administrar usuarios, mesas, productos, pedidos, pagos, reservas, delivery, cocina, ventas, reportes y notificaciones desde una arquitectura basada en backend y frontend separados.

El objetivo principal es digitalizar los procesos operativos del restaurante, mejorar la experiencia del cliente y optimizar la coordinación entre el personal de cocina, meseros, repartidores y administración.

---

## 2. Objetivo del proyecto

El sistema busca:

- Automatizar la gestión de pedidos y pagos.
- Facilitar la operación del restaurante en tiempo real.
- Permitir la atención presencial y a domicilio.
- Integrar procesos de reservas, ventas y reportes.
- Proporcionar una base escalable para futuras mejoras del negocio.

---

## 3. Arquitectura general

El proyecto está conformado por tres capas principales:

1. Frontend Angular
    - Aplicación web para usuarios finales y operadores.
    - Permite la interacción con menús, pedidos, reservas, paneles de cocina, delivery y administración.

2. Backend Spring Boot
    - Expone APIs REST para la lógica del negocio.
    - Gestiona autenticación, validaciones, servicios, persistencia y reglas de negocio.

3. Base de datos PostgreSQL
    - Almacena usuarios, roles, productos, pedidos, pagos, reservas, ventas y notificaciones.
    - Se gestiona mediante JPA/Hibernate y contenedores Docker.

### Arquitectura propuesta

**Pegar imagen aquí**

**Figura 1. Arquitectura general del sistema**

La arquitectura está diseñada para separar responsabilidades entre:

- Frontend: experiencia de usuario e interacción visual.
- Backend: procesamiento y reglas de negocio.
- Base de datos: persistencia y consistencia de datos.
- Seguridad: autenticación con JWT y control por roles.

---

## 4. Módulos principales

El sistema está organizado por módulos funcionales, entre los más importantes se encuentran:

- Autenticación y usuarios
- Onboarding y configuración inicial
- Lugares de atención y mesas
- Gestión de productos y categorías
- Pedidos y detalle de pedidos
- Reservas
- Pagos y ventas
- Cocina
- Delivery y seguimiento
- Facturación y reportes
- Notificaciones

### 4.1 Módulos del backend

El backend está pensado bajo una estructura modular con capas como:

- Controller
- Service
- Repository
- Entity
- DTO
- Mapper
- Security

### 4.2 Módulos del frontend

La interfaz web agrupa funcionalidades por áreas como:

- auth
- dashboard
- pedidos
- productos
- categorias
- reservas
- mesas
- mesas-qr
- pagos
- delivery
- cocina
- facturacion
- reportes
- usuarios
- roles
- configuracion

---

## 5. Flujo de negocio principal

El flujo general del sistema funciona de la siguiente manera:

1. El cliente accede al sistema desde el menú público o desde una mesa identificada por QR.
2. Selecciona productos y crea un pedido.
3. El pedido se procesa y se envía a cocina.
4. Cocina actualiza el estado del pedido hasta marcarlo como listo.
5. El mesero o repartidor realiza la entrega o el seguimiento correspondiente.
6. Se registra el pago y se genera la venta asociada.
7. El sistema emite notificaciones y reportes de seguimiento.

### Diagrama de secuencia

**Pegar imagen aquí**

**Figura 2. Diagrama de secuencia del flujo principal**

---

## 6. Tecnologías utilizadas

### Backend

- Java 21
- Spring Boot 4.1.0
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- PostgreSQL
- Docker Compose
- Lombok
- Apache POI

### Frontend

- Angular 20
- TypeScript
- RxJS
- HTML5 QR Code
- SCSS

### Infraestructura

- Contenedores Docker
- Gradle
- Git

---

# Capítulo 2. Manual de Usuario

## 1. Introducción

Esta sección presenta una guía general para que los usuarios puedan interactuar con el sistema según su rol. La aplicación está orientada a facilitar la operación del restaurante desde una única plataforma.

---

## 2. Objetivo

Permitir que cada tipo de usuario realice sus tareas de manera eficiente, rápida y segura.

---

## 3. Perfiles del sistema

El sistema contempla los siguientes perfiles:

- Administrador
- Cliente
- Mesero
- Cocina
- Repartidor
- Contabilidad

---

## 4. Ingreso al aplicativo

El acceso al sistema se realiza mediante credenciales de usuario. El administrador configura los roles y permisos iniciales, mientras que el resto de usuarios accede según su función específica.

**Pegar imagen aquí**

**Figura 3. Pantalla de ingreso**

---

## 5. Perfil Administrador

### 5.1 Pantalla principal

El administrador puede visualizar el estado general del negocio, gestionar usuarios, definir roles, configurar módulos y revisar reportes.

**Pegar imagen aquí**

### 5.2 Menús principales

- Usuarios
- Roles
- Productos
- Pedidos
- Reservas
- Reportes
- Configuración

### 5.3 Funcionalidades principales

- Gestión de usuarios y permisos
- Administración de productos y categorías
- Control de pedidos y pagos
- Monitoreo de reportes y ventas
- Configuración del sistema

### 5.4 Preguntas frecuentes

- ¿Cómo creo un nuevo usuario?  
  Se accede al módulo de usuarios y se registra con el rol correspondiente.

- ¿Cómo revisar ventas?  
  Desde el módulo de reportes o contabilidad se pueden consultar los registros disponibles.

---

## 6. Perfil Cliente

### 6.1 Pantalla principal

El cliente puede visualizar el menú, realizar pedidos y, en algunos casos, acceder a reservas o pedidos por delivery.

**Pegar imagen aquí**

### 6.2 Menús

- Menú público
- Pedidos
- Reservas

### 6.3 Funcionalidades

- Explorar productos
- Agregar al carrito
- Enviar pedidos
- Consultar el estado del pedido

### 6.4 Preguntas frecuentes

- ¿Cómo realizo un pedido?  
  Se seleccionan los productos y se confirma la orden.

- ¿Cómo sé el estado de mi pedido?  
  El sistema muestra el estado de preparación, listo y entrega.

---

## 7. Perfil Mesero

### 7.1 Pantalla principal

El mesero gestiona pedidos en mesa, verifica estado de las órdenes y apoya la atención al cliente.

**Pegar imagen aquí**

### 7.2 Menús

- Pedidos
- Mesas
- Cobros

### 7.3 Funcionalidades

- Crear o actualizar pedidos
- Ver pedidos activos
- Marcar pedidos como servidos o entregados
- Apoyar el proceso de atención presencial

---

## 8. Perfil Cocina

### 8.1 Pantalla principal

La cocina visualiza la cola de pedidos pendientes y avanza los estados de preparación.

**Pegar imagen aquí**

### 8.2 Funcionalidades

- Ver pedidos en preparación
- Marcar pedidos como listos
- Organizar la preparación de los productos

---

## 9. Perfil Repartidor

### 9.1 Pantalla principal

El repartidor gestiona los pedidos delivery, su ruta de entrega y el estado de seguimiento.

**Pegar imagen aquí**

### 9.2 Funcionalidades

- Visualizar pedidos asignados
- Marcar como recogidos o entregados
- Seguimiento del delivery

---

## 10. Glosario

- JWT: mecanismo de autenticación basada en tokens.
- DTO: objeto de transferencia de datos.
- Endpoint: ruta expuesta por la API.
- API: interfaz de comunicación entre frontend y backend.
- Pedido: orden de consumo del cliente.
- Pago: registro de la transacción del pedido.
- Reserva: solicitud de atención futura en el restaurante.
- Venta: cierre o registro de una operación comercial.
- Delivery: modalidad de entrega a domicilio.
- QR: código para acceso rápido o identificación de mesa.

---

Este documento es un resumen general del proyecto y debe actualizarse conforme evolucione el sistema.
