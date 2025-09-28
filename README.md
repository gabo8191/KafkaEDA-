# 🏗️ EDA Microservices with Kafka

Este proyecto implementa una **Arquitectura Orientada a Eventos (EDA)** usando **Apache Kafka** y **Spring Boot** con **3 microservicios independientes**.

## 📋 **Arquitectura**

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Customer      │    │     Login       │    │     Order       │
│   Service       │    │    Service      │    │    Service      │
│   :8080         │    │    :8081        │    │    :8082        │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │     Kafka       │
                    │   :29092        │
                    └─────────────────┘
```

## 🚀 **Microservicios**

### **👥 Customer Service (Puerto 8080)**

- **Endpoints:**
  - `POST /customers` - Crear customer
  - `PUT /customers` - Actualizar customer
  - `DELETE /customers/{document}` - Eliminar customer
- **Topics Kafka:**
  - `addcustomer_events`
  - `editcustomer_events`
  - `deletecustomer_events`

### **🔐 Login Service (Puerto 8081)**

- **Endpoints:**
  - `POST /logins` - Crear login
  - `PUT /logins` - Actualizar login
- **Topics Kafka:**
  - `addlogin_events`
  - `editlogin_events`

### **📦 Order Service (Puerto 8082)**

- **Endpoints:**
  - `POST /orders` - Crear order
  - `PUT /orders` - Actualizar order
- **Topics Kafka:**
  - `addorder_events`
  - `editorder_events`

## 🛠️ **Tecnologías**

- **Java 17**
- **Spring Boot 3.5.6**
- **Apache Kafka (KRaft mode)**
- **MySQL Database**
- **Docker & Docker Compose**
- **Lombok**
- **JPA/Hibernate**

## 📦 **Instalación y Ejecución**

### **Opción 1: Script Automático (Recomendado)**

```bash
# Ejecutar script de build y deployment
build-and-run.bat
```

### **Opción 2: Manual**

1. **Build de microservicios:**

```bash
# Customer Service
cd microservice-customer
mvn clean package -DskipTests
cd ..

# Login Service
cd microservice-login
mvn clean package -DskipTests
cd ..

# Order Service
cd microservice-order
mvn clean package -DskipTests
cd ..
```

2. **Ejecutar con Docker Compose:**

```bash
docker-compose up --build
```

## 🧪 **Testing**

### **Customer Service (Puerto 8080)**

```bash
# Crear customer
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{
    "document": "12345678",
    "firstname": "Juan",
    "lastname": "Pérez",
    "address": "Calle 123 #45-67",
    "phone": "3001234567",
    "email": "juan.perez@email.com"
  }'

# Actualizar customer
curl -X PUT http://localhost:8080/customers \
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
curl -X DELETE http://localhost:8080/customers/12345678
```

### **Login Service (Puerto 8081)**

```bash
# Crear login
curl -X POST http://localhost:8081/logins \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "12345678",
    "password": "mipassword123"
  }'

# Actualizar login
curl -X PUT http://localhost:8081/logins \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "customerId": "12345678",
    "password": "nuevopassword456"
  }'
```

### **Order Service (Puerto 8082)**

```bash
# Crear order
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1001,
    "customerId": "12345678",
    "status": "PENDING"
  }'

# Actualizar order
curl -X PUT http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1001,
    "customerId": "12345678",
    "status": "CONFIRMED"
  }'
```

## 🗄️ **Base de Datos**

**MySQL Database:** `customer_orders`

- **Host:** localhost:3306
- **Username:** root
- **Password:** root

### **Tablas:**

- `customer` - Información de clientes
- `login` - Credenciales de acceso
- `order` - Órdenes de compra

## 📡 **Kafka Topics**

Todos los topics se crean automáticamente al iniciar Docker Compose:

- `addcustomer_events`
- `editcustomer_events`
- `deletecustomer_events`
- `addlogin_events`
- `editlogin_events`
- `addorder_events`
- `editorder_events`

## 🚀 **Instalación y Ejecución**

### **Prerrequisitos**

- Docker y Docker Compose (solo para Kafka)
- Java 17+
- Maven 3.6+
- Base de datos MySQL

### **Pasos de Instalación**

1. **Iniciar Kafka con Docker:**

   ```bash
   start-kafka.bat
   ```

   O manualmente:

   ```bash
   docker-compose up -d
   ```

2. **Ejecutar microservicios localmente (en terminales separadas):**

   **Customer Service (Puerto 8080):**

   ```bash
   cd microservice-customer
   mvn spring-boot:run
   ```

   **Login Service (Puerto 8081):**

   ```bash
   cd microservice-login
   mvn spring-boot:run
   ```

   **Order Service (Puerto 8082):**

   ```bash
   cd microservice-order
   mvn spring-boot:run
   ```

3. **Detener Kafka cuando termines:**

   ```bash
   stop-kafka.bat
   ```

## 🔧 **Configuración**

### **Configuración Local**

- **Kafka Bootstrap Server:** `localhost:29092`
- **Base de datos:** `jdbc:mysql://localhost:3306/customer_orders`
- **Usuario BD:** `root`
- **Contraseña BD:** `root`

### **Puertos**

- **Customer Service:** 8080
- **Login Service:** 8081
- **Order Service:** 8082
- **Kafka:** 29092

## 🎯 **Flujo EDA**

1. **Cliente** envía request HTTP al microservicio
2. **Controller** recibe request y envía evento a Kafka
3. **Event Producer** serializa y publica evento en topic
4. **Event Consumer** consume evento del topic
5. **Service** procesa evento y actualiza base de datos
6. **Cliente** recibe confirmación de operación

## 🏗️ **Estructura del Proyecto**

```
EDA-Kafka/
├── microservice-customer/     # Customer Microservice
├── microservice-login/        # Login Microservice
├── microservice-order/        # Order Microservice
├── docker-compose.yaml        # Docker orchestration
├── build-and-run.bat         # Build script
└── README.md                 # This file
```

## 🚨 **Notas Importantes**

- **No hay operaciones GET** - Solo operaciones de escritura (POST, PUT, DELETE)
- **Comunicación asíncrona** - Todos los microservicios se comunican via Kafka
- **Desacoplamiento total** - Cada microservicio es independiente
- **Base de datos compartida** - Todos usan la misma BD para simplicidad
- **KRaft mode** - Kafka sin Zookeeper (versión moderna)

## 🎉 **¡Listo para usar!**

1. Ejecuta `start-kafka.bat` para iniciar Kafka
2. Ejecuta los microservicios localmente con `mvn spring-boot:run`
3. ¡Disfruta de tu arquitectura EDA funcionando!
