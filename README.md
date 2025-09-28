# Microservicios con Kafka

Arquitectura Orientada a Eventos (EDA) usando Apache Kafka y Spring Boot con 3 microservicios independientes.

## Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                    API GATEWAY (Spring Cloud Gateway)          │
│                           :8080                                │
└─────────────────────┬───────────────────────────────────────────┘
                      │
    ┌─────────────────┼─────────────────┐    ┌─────────────────┐
    │                 │                 │    │                 │
┌───▼───┐    ┌────────▼────────┐    ┌───▼───┐    ┌─────────────▼─┐
│Customer│    │     Login       │    │ Order │    │     Kafka     │
│Service │    │    Service      │    │Service│    │   :29092      │
│:8081   │    │    :8082        │    │:8083  │    │               │
└────────┘    └─────────────────┘    └───────┘    └───────────────┘
```

## Microservicios

### API Gateway (Puerto 8080)

- Punto único de entrada para todos los microservicios
- Routing inteligente basado en rutas
- CORS habilitado para todas las rutas
- Logging centralizado de todas las requests

### Customer Service (Puerto 8081)

- `POST /api/customers` - Crear customer
- `PUT /api/customers` - Actualizar customer
- `DELETE /api/customers/{document}` - Eliminar customer
- Topic Kafka: `customer_events`

### Login Service (Puerto 8082)

- `POST /api/logins` - Crear login
- `PUT /api/logins` - Actualizar login
- Topic Kafka: `login_events`

### Order Service (Puerto 8083)

- `POST /api/orders` - Crear order
- `PUT /api/orders` - Actualizar order
- Topic Kafka: `order_events`

## Tecnologías

- Java 17
- Spring Boot 3.5.6
- Apache Kafka (KRaft mode)
- MySQL Database
- Docker & Docker Compose
- Lombok
- JPA/Hibernate

## Instalación y Ejecución

### Prerrequisitos

- Docker y Docker Compose (solo para Kafka)
- Java 17+
- Maven 3.6+
- Base de datos MySQL

### Pasos de Instalación

1. **Iniciar Kafka con Docker:**

   ```bash
   start-kafka.bat
   ```

   O manualmente:

   ```bash
   docker-compose up -d
   ```

2. **Ejecutar microservicios localmente (en terminales separadas):**

   **Customer Service (Puerto 8081):**

   ```bash
   cd microservice-customer
   mvn spring-boot:run
   ```

   **Login Service (Puerto 8082):**

   ```bash
   cd microservice-login
   mvn spring-boot:run
   ```

   **Order Service (Puerto 8083):**

   ```bash
   cd microservice-order
   mvn spring-boot:run
   ```

   **API Gateway (Puerto 8080):**

   ```bash
   cd gateway
   mvn spring-boot:run
   ```

3. **Detener Kafka cuando termines:**

   ```bash
   stop-kafka.bat
   ```

## Configuración

### Configuración Local

- **Kafka Bootstrap Server:** `localhost:29092`
- **Base de datos:** `jdbc:mysql://localhost:3306/customer_orders`
- **Usuario BD:** `root`
- **Contraseña BD:** `root`

### Puertos

- **API Gateway:** 8080
- **Customer Service:** 8081
- **Login Service:** 8082
- **Order Service:** 8083
- **Kafka:** 29092

## Testing

### Customer Service

```bash
# Crear customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "document": "12345678",
    "firstname": "Juan",
    "lastname": "Pérez",
    "address": "Calle 123 #45-67",
    "phone": "3001234567",
    "email": "juan.perez@email.com",
    "password": "mipassword123"
  }'

# Actualizar customer
curl -X PUT http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "document": "12345678",
    "firstname": "Juan Carlos",
    "lastname": "Pérez García",
    "address": "Calle 456 #78-90",
    "phone": "3007654321",
    "email": "juan.carlos@email.com"
  }'

# Eliminar customer
curl -X DELETE http://localhost:8080/api/customers/12345678
```

### Login Service

```bash
# Crear login
curl -X POST http://localhost:8080/api/logins \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "12345678",
    "password": "mipassword123"
  }'

# Actualizar login
curl -X PUT http://localhost:8080/api/logins \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "customerId": "12345678",
    "password": "nuevopassword456"
  }'
```

### Order Service

```bash
# Crear order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1001,
    "customerId": "12345678",
    "status": "PENDING"
  }'

# Actualizar order
curl -X PUT http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1001,
    "customerId": "12345678",
    "status": "CONFIRMED"
  }'
```

## Base de Datos

**MySQL Database:** `customer_orders`

- **Host:** localhost:3306
- **Username:** root
- **Password:** root

### Tablas

- `customer` - Información de clientes
- `login` - Credenciales de acceso
- `orders` - Órdenes de compra

## Kafka Topics

Todos los topics se crean automáticamente al iniciar Docker Compose:

- `customer_events`
- `login_events`
- `order_events`

## Flujo EDA

1. **Cliente** envía request HTTP al Gateway
2. **Gateway** enruta request al microservicio correspondiente
3. **Controller** recibe request y envía evento a Kafka
4. **Event Producer** serializa y publica evento en topic
5. **Event Consumer** consume evento del topic
6. **Service** procesa evento y actualiza base de datos
7. **Cliente** recibe confirmación de operación

## Estructura del Proyecto

```
EDA-Kafka/
├── microservice-customer/     # Customer Microservice
├── microservice-login/        # Login Microservice
├── microservice-order/        # Order Microservice
├── gateway/                   # API Gateway
├── docker-compose.yaml        # Docker orchestration
├── start-kafka.bat           # Start Kafka script
├── stop-kafka.bat            # Stop Kafka script
└── README.md                 # This file
```

## Notas Importantes

- **No hay operaciones GET** - Solo operaciones de escritura (POST, PUT, DELETE)
- **Comunicación asíncrona** - Todos los microservicios se comunican via Kafka
- **Desacoplamiento total** - Cada microservicio es independiente
- **Base de datos compartida** - Todos usan la misma BD para simplicidad
- **KRaft mode** - Kafka sin Zookeeper (versión moderna)
