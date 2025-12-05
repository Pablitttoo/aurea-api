# Aurea API (Backend)

RESTful API desarrollada con **Spring Boot** para gestionar la lógica de negocio, productos y usuarios de la aplicación móvil **AureaEcommerce**.

## 🛠 Tecnologías Principales
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.4.0
* **Base de Datos:** PostgreSQL (Alojada en Supabase)
* **Build Tool:** Gradle (Kotlin DSL)
* **Testing:** JUnit 5, Mockito & Jacoco

## 🚀 Cómo Ejecutar el Proyecto

1.  **Requisitos:** Tener instalado JDK 21.
2.  **Base de Datos:** Verificar que el archivo `src/main/resources/application.properties` contenga las credenciales de conexión a Supabase.
3.  **Comando de inicio:**
    Abre una terminal en la raíz del proyecto y ejecuta:
    ```bash
    ./gradlew bootRun
    ```
4.  La API iniciará en el puerto **8080**.

## 📄 Documentación de Endpoints
El proyecto incluye **Swagger UI** para visualizar y probar los endpoints fácilmente.
Una vez que la aplicación esté corriendo, visita:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

## ✅ Pruebas Unitarias
El proyecto cuenta con una alta cobertura de código (>90% en lógica de negocio). Para ejecutar los tests y generar el reporte de cobertura:

```bash
./gradlew test
