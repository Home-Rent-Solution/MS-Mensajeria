MS-Mensajeria

Microservicio encargado de gestionar la comunicación entre usuarios dentro de la plataforma Home-Rent-Solution.

## Funcionalidades

* Registrar mensajes
* Consultar mensajes
* Actualizar mensajes
* Eliminar mensajes
* Buscar mensajes por emisor
* Buscar mensajes por receptor
* Consultar conversaciones entre usuarios
----------------------------------------------------

## Endpoints Principales

### Obtener todos los mensajes

GET /api/v1/mensajes

### Obtener mensaje por ID

GET /api/v1/mensajes/{id}

### Crear mensaje

POST /api/v1/mensajes

### Actualizar mensaje

PUT /api/v1/mensajes/{id}

### Eliminar mensaje

DELETE /api/v1/mensajes/{id}

### Buscar mensajes por emisor

GET /api/v1/mensajes/emisor/{id}

### Buscar mensajes por receptor

GET /api/v1/mensajes/receptor/{id}

### Buscar conversación entre usuarios

GET /api/v1/mensajes/conversacion?emisor={id}&receptor={id}

-----------------------------------------------------------
## Integraciones

Este microservicio utiliza OpenFeign para comunicarse con:

* MS-Inquilinos
* MS-Reservas
-----------------------------------------------------------

## Tecnologías

* Java 25
* Spring Boot
* Spring Data JPA
* MySQL
* OpenFeign
* OpenAPI / Swagger
* Maven
