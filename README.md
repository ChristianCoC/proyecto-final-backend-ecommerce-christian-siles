# 🛒 E-Commerce Backend API — Proyecto Final Integrador

API RESTful desarrollada en **Java y Spring Boot 4** para la gestión integral de un sistema de comercio electrónico. El proyecto implementa arquitectura en capas, persistencia relacional en **PostgreSQL** mediante **Docker**, control transaccional de stock, validaciones de entrada, manejo global de excepciones y documentación interactiva con **Swagger UI (OpenAPI)**.

---

## 🚀 Tecnologías Utilizadas

* **Lenguaje:** Java 17 / 25
* **Framework:** Spring Boot 4 (Web, Data JPA, Validation)
* **Base de Datos:** PostgreSQL 17 (contenedorizado en Docker)
* **Administración de BD:** pgAdmin 4 (Docker)
* **Gestor de Dependencias:** Maven
* **Documentación de API:** SpringDoc OpenAPI 3 / Swagger UI

---

## 🏗️ Arquitectura y Organización del Proyecto

El código sigue una arquitectura modular limpia dentro del paquete base `com.techlab.ecommerce`:

```text
src/main/java/com/techlab/ecommerce/
├── config/         # Configuración global de CORS (CorsConfig) y carga inicial (DataInitializer)
├── controller/     # Controladores REST anotados con OpenAPI (/api/productos, /api/pedidos)
├── dto/            # Objetos de transferencia de datos (PedidoRequestDTO, ItemPedidoDTO, ErrorResponseDTO)
├── exception/      # Excepciones personalizadas y manejador global (@ControllerAdvice)
├── model/          # Entidades JPA (Categoria, Producto, Usuario, Pedido, LineaPedido, EstadoPedido)
├── repository/     # Interfaces JpaRepository y consultas personalizadas (@Query)
└── service/        # Lógica de negocio y gestión transaccional (@Transactional)
```

### Decisiones Técnicas Destacadas
* **Identificadores escalables (`Long`):** Todas las entidades principales utilizan claves primarias `Long` autogeneradas.
* **Encapsulamiento y reglas de dominio:** Atributos calculados o auditados (como `total` y `fecha` en `Pedido`) están protegidos contra modificaciones externas directas; la fecha se asigna automáticamente con `@CreationTimestamp`.
* **Transaccionalidad (`@Transactional`):** Al confirmar un pedido, el sistema valida el stock de cada ítem, descuenta las unidades y calcula el costo total. Si algún producto no tiene stock suficiente, se lanza `StockInsuficienteException` y se revierte la operación completa (*rollback*).
* **Manejo Global de Errores (`@ControllerAdvice`):** Respuestas HTTP estandarizadas (`400 Bad Request`, `404 Not Found`) utilizando `ErrorResponseDTO` sin exponer trazas internas ni comodines `<?>`.
* **Validaciones Bean Validation:** Control automático de campos obligatorios (`@NotBlank`, `@NotNull`), precios positivos (`@Positive`), stock no negativo (`@Min(0)`), correos (`@Email`) y direcciones web (`@URL`).

---

## ⚙️ Requisitos Previos

* [Java JDK 17 o superior](https://adoptium.net/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (o Docker Engine + Docker Compose)
* [Maven](https://maven.apache.org/) *(o usar el wrapper `./mvnw` incluido en el proyecto)*

---

## 🐳 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/<ChristianCoC>/proyecto-final-backend-ecommerce-christian-siles.git
cd proyecto-final-backend-ecommerce-christian-siles
```

### 2. Levantar PostgreSQL y pgAdmin con Docker
```bash
docker compose up -d
```
* **PostgreSQL:** `localhost:5432` (Base de datos: `ecommerce_db`)
* **pgAdmin 4:** `http://localhost:5050` (`admin@admin.com` / `admin`)

### 3. Ejecutar la aplicación Spring Boot
En Windows (PowerShell / CMD):
```bash
.\mvnw.cmd spring-boot:run
```
En Linux / macOS:
```bash
./mvnw spring-boot:run
```

> **Carga inicial automática:** Al iniciar con la base de datos vacía, `DataInitializer` inserta automáticamente un usuario de prueba, dos categorías y tres productos gourmet para que la API esté lista para probar de inmediato.

---

## 📖 Documentación Interactiva (Swagger UI)

Una vez iniciada la aplicación, accedé a la interfaz interactiva para explorar y probar todos los endpoints en vivo:

👉 **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔌 Endpoints Principales

### Productos (`/api/productos`)
| Método | Endpoint | Descripción | Código Éxito |
| :--- | :--- | :--- | :---: |
| `GET` | `/api/productos` | Lista todos los productos disponibles | `200 OK` |
| `GET` | `/api/productos/{id}` | Obtiene el detalle de un producto por ID | `200 OK` |
| `POST` | `/api/productos` | Crea un nuevo producto (con validaciones) | `201 Created` |
| `PUT` | `/api/productos/{id}` | Actualiza los datos de un producto existente | `200 OK` |
| `DELETE` | `/api/productos/{id}` | Elimina un producto por ID | `204 No Content` |

### Pedidos (`/api/pedidos`)
| Método | Endpoint | Descripción | Código Éxito |
| :--- | :--- | :--- | :---: |
| `POST` | `/api/pedidos` | Crea un pedido, valida y descuenta stock | `201 Created` |
| `GET` | `/api/pedidos/usuario/{usuarioId}` | Obtiene el historial de pedidos de un usuario | `200 OK` |

### Ejemplo de Request para Crear un Pedido (`POST /api/pedidos`)
```json
{
  "usuarioId": 1,
  "items": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 2,
      "cantidad": 1
    }
  ]
}
```

---

## 👨‍💻 Autor

**Christian Siles**  
Proyecto Final Integrador — Curso Back-End Java (Talento Tech / Buenos Aires Aprende).