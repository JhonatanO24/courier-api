<h1 align="center">📦 Courier API - Sistema de Envíos 📦</h1>

<p align="center">
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot Badge"/>
<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge"/>
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL Badge"/>
<img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white" alt="Kafka Badge"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Badge"/>
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger Badge"/>
</p>

---

## 🎯 Objetivo del Proyecto

<em>
Desarrollo de una API REST robusta para la gestión de servicios de mensajería y paquetería. El proyecto se enfoca en implementar una arquitectura hexagonal (Ports and Adapters) para garantizar un bajo acoplamiento, utilizando patrones de diseño como Strategy para la flexibilidad en modalidades de envío y Observer/Event-Driven mediante Apache Kafka para una comunicación asíncrona y escalable. (Reto Sofka)
</em>

---

## 🚀 Funcionalidades Principales

### 🏗️ Arquitectura Hexagonal
Diseño desacoplado que separa el dominio del negocio de los detalles de infraestructura (base de datos, mensajería, controladores).

### 🛠️ Estrategias de Envío (Patrón Strategy)
Gestión flexible de modalidades de envío (Express, Estándar, Internacional), permitiendo añadir nuevas modalidades sin modificar el núcleo de negocio.

### 📨 Event-Driven Architecture (Observer + Kafka)
Notificaciones asíncronas disparadas mediante eventos. Al cambiar el estado de un envío, el sistema emite eventos a través de Apache Kafka para servicios externos o microservicios de notificación.

### 🗄️ Persistencia Avanzada
Uso de PostgreSQL 16 con tipos JSONB para metadata dinámica y transaccionalidad garantizada.

---

## 🛠 Tecnologías Utilizadas

| Capa | Tecnología |
|------|-----------|
| ☕ **Lenguaje** | Java 21 (LTS) |
| 🍃 **Framework** | Spring Boot 4.x |
| 🗄️ **Persistencia** | Spring Data JPA + Hibernate |
| 🐘 **Base de Datos** | PostgreSQL 16 |
| 📨 **Broker** | Apache Kafka 3.8 (KRaft) |
| ✈️ **Migraciones** | Flyway |
| 🌐 **Documentación**| SpringDoc OpenAPI 3 |
| 🐳 **Contenedores** | Docker Compose |

---

## 📁 Estructura del Proyecto (Hexagonal)

```plaintext
📦 com.courier
├── 📂 customers/           → 👤 Gestión de clientes
├── 📂 shipments/           → 📦 Lógica de envíos (Patrón Strategy)
├── 📂 shared/              → 🔗 Componentes comunes y eventos
└── 📂 notifications/       → 🔔 Consumidores de eventos (Kafka)
