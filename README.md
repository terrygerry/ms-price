# API de Precios

Servicio encargado de gestionar los precios de los productos.

---

## Requisitos Técnicos

* **Java 17**
* **Maven 3.9.9**
* **Docker** (para empaquetado y ejecución en contenedores)
* **H2 Database** (configurado en memoria para desarrollo y pruebas, con soporte R2DBC)

---

## Tecnologías Principales

Este proyecto ha sido desarrollado utilizando las siguientes tecnologías y frameworks, enfocados en construir una API reactiva, robusta y eficiente:

* **Spring Boot 3.x:** Framework principal para construir aplicaciones Java empresariales y microservicios.
* **Spring WebFlux:** Módulo de Spring para construir APIs reactivas y no bloqueantes, ideal para manejar alta concurrencia de manera eficiente.
* **Spring Data R2DBC:** Proporciona un acceso a bases de datos reactivo y no bloqueante, complementando la naturaleza reactiva de Spring WebFlux.
* **H2 Database:** Base de datos relacional en memoria utilizada para el desarrollo y las pruebas, configurada con el driver **R2DBC**.
* **Lombok:** Herramienta para reducir el código *boilerplate* en Java, mejorando la legibilidad.
* **MapStruct:** Procesador de anotaciones para generar automáticamente código de mapeo de alto rendimiento entre DTOs y entidades, reduciendo errores manuales.
* **JUnit 5 & Mockito:** Frameworks para la escritura de pruebas unitarias y de integración, asegurando la calidad del código.
* **JaCoCo:** Herramienta para generar reportes de cobertura de código, ayudando a identificar áreas con poca cobertura de pruebas.
* **Spring Boot Validation:** Proporciona validación de datos de entrada declarativa y efectiva.
* **Springdoc-OpenAPI:** Para la generación automática de documentación de la API siguiendo el estándar OpenAPI (Swagger UI).
* **Docker:** Utilizado para empaquetar la aplicación en un contenedor ligero y portable, asegurando un entorno consistente.
* **GitHub Actions:** Implementado para automatizar el pipeline de Integración Continua (CI).
* **Render:** Plataforma de despliegue continuo (CD) para servicios web y APIs.

---

## Arquitectura y Patrones de Diseño

La arquitectura de esta API se ha diseñado siguiendo principios robustos para garantizar la mantenibilidad, escalabilidad, testabilidad y una clara separación de preocupaciones.

### Arquitectura Hexagonal (Ports and Adapters)

Adoptamos la Arquitectura Hexagonal para aislar la lógica de negocio central (Dominio) de las tecnologías externas (Base de Datos, API REST), manifestada en la siguiente estructura de paquetes:

* **Capa de Aplicación (`.application`):** Contiene la lógica de orquestación y los *puertos de entrada*. Aquí encontramos:
    * `dto` (Data Transfer Objects para requests y responses).
    * `mapper` (MapStruct para la transformación de datos).
    * `port.inbound` (Interfaces `UseCase` que definen las operaciones que la aplicación expone, como `GetProductPriceUseCase`).
    * `service` (Implementación de los `UseCase` y coordinación de la lógica de negocio).
* **Capa de Dominio (`.domain`):** El corazón de la aplicación, agnóstico a la infraestructura. Contiene:
    * `model` (Entidades de negocio como `PriceEntity` y `PriceSearchCriteria`).
    * `port.outbound` (Interfaces de los *puertos de salida* que el dominio necesita, como `PriceRepository`).
    * `service` (Lógica de dominio puro, ej., `PricePrioritizationService`).
* **Capa de Infraestructura (`.infrastructure`):** Implementa los *adaptadores* para interactuar con tecnologías externas. Aquí encontramos:
    * `controller` (Adaptadores de entrada para la API REST, como `PriceController`).
    * `exception` (Manejo global de excepciones, como `GlobalExceptionHandler`).
    * `repository` (Adaptadores de salida, implementaciones de `PriceRepository` para R2DBC, ej., `PriceR2dbcAdapter`).
    * `api.response`, `common` (Utilidades y respuestas de API).

### Filosofía Domain-Driven Design (DDD)

El diseño se enfoca en el **Dominio** del negocio, modelando los conceptos clave de precios y sus reglas, encapsulados en la capa `.domain`, que es el centro de la solución y totalmente independiente de las preocupaciones tecnológicas.

### Principios SOLID

Se han aplicado los principios **SOLID** para promover un código más legible, mantenible y extensible en todo el proyecto.

### Patrones de Diseño Adicionales

* **Dependency Injection:** Gestionado por Spring para una alta cohesión y bajo acoplamiento.
* **Repository Pattern:** Abstracción para las operaciones de persistencia.
* **Mapper Pattern:** Utilizado con MapStruct para mapeos eficientes.
---

## Instrucciones de Ejecución

1.  **Clonar el repositorio:** `git clone https://github.com/terrygerry/ms-price.git`
2.  **Construir con Maven:** Asegúrate de estar en el directorio raíz del proyecto y ejecuta: `mvn clean install`
3.  **Ejecutar la aplicación (Local):** `mvn spring-boot:run`
    * La API estará disponible en `http://localhost:8080`.
    * La documentación OpenAPI (Swagger UI) estará accesible en `http://localhost:8080/webjars/swagger-ui.html`.
4.  **(Opcional) Ejecutar con Docker (Local):**
    * Asegúrate de tener Docker Desktop ejecutándose.
    * Construir la imagen: `docker build -t ms-price-api .`
    * Ejecutar el contenedor: `docker run -p 8080:8080 ms-price-api`
        * Tu API será accesible en `http://localhost:8080`.
        * La documentación OpenAPI (Swagger UI) también estará disponible en `http://localhost:8080/webjars/swagger-ui.html`.
5.  **Servicio Desplegado en Render:**
    * La API está desplegada y accesible públicamente en: `https://ms-price.onrender.com`.
    * La documentación OpenAPI (Swagger UI) para el servicio desplegado se encuentra en: `https://ms-price.onrender.com/webjars/swagger-ui/index.html`

---

## Endpoints de la API

* **GET /prices**
    * **Descripción:** Obtiene el precio aplicable para un producto, marca y fecha, basándose en la lógica de priorización definida.
    * **Parámetros (Query):** `productId` (Integer, requerido), `brandId` (Integer, requerido), `dateFrom` (String, formato `YYYY-MM-DD-HH.mm.ss`, requerido).
    * **Ejemplo de Request:** `http://localhost:8080/api/prices?productId=35455&brandId=1&dateFrom=2020-06-14-16.00.00`
    * **Ejemplo de Response (200 OK):**
        ```json
        {
            "productId": 35455,
            "brandId": 1,
            "priceList": 4,
            "startDate": "2020-06-15T16:00:00",
            "endDate": "2020-12-31T23:59:59",
            "price": 38.95,
            "currency": "EUR"
        }
        ```
    * **Ejemplo de Response (400 Bad Request):**
        ```json
        {
            "status": 400,
            "message": "Invalid input parameters",
            "details": "string",
            "timestamp": "2025-05-19T11:30:00.000Z"
        }
        ```
    * **Consideraciones Importantes:**
        * Los datos de precios se cargan en la base de datos H2 en memoria al iniciar la aplicación (ver `data.sql`).
        * La lógica de negocio selecciona el precio con la más alta prioridad si hay múltiples precios aplicables para los criterios dados, de acuerdo con las reglas de negocio.

---

## Puntos Destacados de la Implementación

* **API Reactiva y No Bloqueante** construida con **Spring WebFlux y Spring Data R2DBC**.
* **Arquitectura Hexagonal (Ports and Adapters)** para una clara separación de preocupaciones y alta testabilidad.
* Aplicación de la filosofía **Domain-Driven Design (DDD)** para un modelado de dominio robusto y centrado en el negocio.
* Adherencia a los principios **SOLID** para un código mantenible y extensible.
* **Validación de datos de entrada** con **Bean Validation** para asegurar la calidad de los parámetros de la API.
* **Manejo de excepciones global** con `@RestControllerAdvice` para proporcionar respuestas de error consistentes y amigables.
* Uso de **MapStruct** para el mapeo eficiente y seguro entre DTOs y entidades.
* **Cobertura de código** medida con **JaCoCo**.
* Documentación de la API generada automáticamente con **Springdoc-OpenAPI (Swagger UI)**.
* **Pipeline de CI/CD** implementado con **GitHub Actions** (para construcción y publicación de imagen Docker) y **Render** (para despliegue continuo).

---