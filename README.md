# tp-backend-agencia-vehiculos

## Arquitectura y Descripción de la Aplicación

La aplicación sigue una arquitectura de microservicios que incluye dos microservicios principales, cada uno con su base de datos independiente. Ambos microservicios están conectados a través de un API Gateway que se encarga del ruteo y redireccción de las solicitudes hacia el correspondiente servicio, además de gestionar la autenticación y autorización usando Keycloak.

1. **Microservicio de Pruebas de Manejo**: Maneja las operaciones relacionadas con las pruebas de manejo de vehículos. Este microservicio contiene los endpoints necesarios para crear, finalizar, y consultar las pruebas, así como para actualizar las posiciones de los vehículos durante las pruebas, y poder generar reportes sobre las mismas.
2. **Microservicio de Notificaciones**: Permite enviar notificaciones a destinatarios sobre alertas ocurridas durante las pruebas de manejo (como posiciones fuera de la distancía máxima permitida por la agencia, o dentro de zonas restringidas), así como el envío de promociones a varios destinatarios en simultaneo. 

![arquitectura-tpi](https://github.com/user-attachments/assets/db036102-b52f-49b3-92fa-6d51f55f5e35)

---

## Microservicio 1: Pruebas de Manejo
### Endpoints del Microservicio de Pruebas de Manejo:

#### 1. **POST /api/pruebas/nueva**
   - **Descripción**: Crea una nueva prueba de manejo para un vehículo.
   - **Roles permitidos**: `EMPLEADO`

#### 2. **GET /api/pruebas/en-curso**
   - **Descripción**: Consulta las pruebas de manejo que están en curso.
   - **Roles permitidos**: `EMPLEADO`

#### 3. **PUT /api/pruebas/finalizar**
   - **Descripción**: Finaliza una prueba de manejo.
   - **Roles permitidos**: `EMPLEADO`

#### 4. **POST /api/pruebas/posicion/actualizar**
   - **Descripción**: Actualiza la posición actual de un vehículo durante una prueba de manejo.
   - **Roles permitidos**: `VEHICULO`

#### 5. **GET /api/pruebas/reportes/kilometros**
   - **Descripción**: Genera un reporte con el total de kilómetros recorridos por determinado vehículo en determinado periodo.
   - **Roles permitidos**: `ADMIN`
   - **Respuesta**: Reporte de los kilómetros recorridos durante las pruebas.

#### 6. **GET /api/pruebas/reportes/incidentes**
   - **Descripción**: Genera un reporte con las pruebas que en donde ocurrieron incidentes durante las pruebas de manejo.
   - **Roles permitidos**: `ADMIN`

#### 7. **GET /api/pruebas/reportes/pruebas-vehiculo**
   - **Descripción**: Genera un reporte detallado de las pruebas realizadas por un vehículo específico.
   - **Roles permitidos**: `ADMIN`

### Diagrama de la Base de Datos
![Diagrama de Base de Datos - Agencia](https://github.com/user-attachments/assets/62c2d0fb-06db-4410-917d-69041c9c9d51)
---

## Microservicio 2: Notificaciones
### Endpoints del Microservicio de Notificaciones:

#### 1. **POST /api/notificaciones/nueva**
   - **Descripción**: Envia una nueva notificación (la almacena en la BD).
   - **Roles permitidos**: `EMPLEADO`

#### 1. **POST /api/notificaciones/nueva-promocion**
   - **Descripción**: Envia una misma notificación de promocion a varios destinatarios (las almacena en la BD).
   - **Roles permitidos**: `EMPLEADO`

### Diagrama de la Base de Datos
![Diagrama de Base de Datos - Notificaciones](https://github.com/user-attachments/assets/d98b656d-9e19-4831-be4c-e8c18421bb61)
---

## Seguridad y Autenticación

La autenticación y autorización en la aplicación se gestionan utilizando **Keycloak**, que actúa como servidor de autenticación. Los microservicios y el API Gateway están configurados para validar los tokens JWT emitidos por Keycloak.

### Roles definidos en Keycloak:
- **Admin**: Permite y tiene acceso a la genración de reportes.
- **Empleado**: Permite realizar tareas como crear, finalizar y gestionar las pruebas, así como enviar notificaciones.
- **Vehículo**: Permite actualizar la posición de los vehículos en las pruebas.

El API Gateway gestiona la seguridad y las rutas de los microservicios, asegurando que solo los usuarios con los roles apropiados puedan acceder a ciertos endpoints.

---


