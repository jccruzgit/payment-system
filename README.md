# Sistema de Orquestación de Pagos

Este proyecto consiste en dos servicios desarrollados con Spring Boot:

- **orchestrator**: recibe y valida solicitudes de pago desde el frontend, orquesta el proceso con el servicio externo y persiste la transacción.
- **payment-processor**: simula un sistema externo (como un ERP) que valida y aplica el pago recibido.

---

## 📦 Estructura del repositorio
payment-system/
├── orchestrator/ # Servicio principal
├── payment-processor/ # Servicio externo simulado
└── Payment-System.postman_collection.json
└── Solución prueba Técnica Backend.docx

## Cómo ejecutar los servicios

1. Clonar el repositorio:
```bash
git clone https://github.com/jccruzgit/payment-system.git
cd payment-system

## Ejecutar el servicio externo simulado
cd payment-processor
./mvnw spring-boot:run
## Escucha en http://localhost:8081

## Ejecutar el orquestador
cd ../orchestrator
./mvnw spring-boot:run
# Escucha en http://localhost:8080

## Endpoints disponibles

## Orquestador
POST /api/payments → recibe una solicitud de pago y la procesa.

## Procesador
POST /api/apply-payment → simula la validación y aplicación del pago.

## Pruebas con Postman
Importar el archivo Payment-System.postman_collection.json en Postman para probar los request:

PaymentRequest
ApplyPaymentRequest

## Documentación Swagger
## Acceder a la documentación automática en:

Orquestador: http://localhost:8080/swagger-ui.html

## Requisitos
Java 17+
Maven