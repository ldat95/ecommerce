# Proyecto - Inditex ecommerce

## Descripción del Proyecto

Este proyecto implementa un servicio REST que gestiona los precios de productos de acuerdo con su aplicación temporal y la prioridad de las reglas de precios. Los precios se consultan a través de un controlador que interactúa con una base de datos en memoria H2.

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de la aplicación.
- **H2 Database**: Base de datos en memoria para pruebas rápidas.
- **JUnit 5**: Framework para pruebas unitarias y de integración.
- **Spring Data JPA**: Para el acceso a datos mediante la implementación del repositorio.
- **JaCoCo**: Herramienta para la medición de la cobertura de código en las pruebas.
- **Maven**: Herramienta de gestión y construcción del proyecto.
- **Sonar**: Plugin utilizado para corregir cualquier alerta relevante. (Integrado en el IDE IntelliJ)

## Patrones y Arquitectura

- **Arquitectura Hexagonal**: Separa las preocupaciones de la aplicación en capas de acuerdo con los principios de la arquitectura hexagonal, asegurando que el núcleo de la lógica de negocio esté desacoplado de los detalles de infraestructura.
- **Scream Architecture**: El diseño sigue el principio de "scream" que busca mantener el código limpio, modular y fácil de entender, con una clara separación de responsabilidades.
- **SOLID**: Los principios de SOLID han sido aplicados para asegurar que el sistema sea flexible y escalable.
- **Clean Code**: Los principios Clean Code han sido aplicados para mejorar el rendimiento y entendimiento de la aplicación.


## Estructura del Proyecto

- El proyecto sigue una arquitectura de carpetas basada en la arquitectura hexagonal:

   ```bash
   src
   ├── main
   │   ├── java
   │   │   └── com.inditex.ecommerce.pricing
   │   │       ├── api                # Controladores REST
   │   │       ├── application        # Lógica de negocio (servicios)
   │   │       ├── domain             # Entidades y repositorios del dominio
   │   │       └── infrastructure     # Implementación de la infraestructura (repositorios, base de datos)
   │   └── resources
   │       ├── application.yml       # Configuración de la aplicación
   │       └── database              # Scripts de inicialización de la base de datos
   └── test
      ├── java
      │   └── com.inditex.ecommerce.pricing
      │       ├── api                # Tests de los controladores REST
      │       ├── application        # Tests de los diferentes servicios
      │       └── domain             # Tests de la lógica de negocio
      └── resources
          └── database               # Scripts de inicialización de la base de datos


## Instalación y Ejecución

### Requisitos previos

- **Java 17** o superior.
- **Maven 3.8** o superior.
- **Git** (para clonar el repositorio).

### Pasos para ejecutar el proyecto

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/ldat95/ecommerce.git

2. Compilar y ejecutar la aplicación:
    
    ```bash
    mvn clean spring-boot:run

3. Acceder a la API:

    ```bash
     La aplicación estará disponible en http://localhost:8080/api/prices.

###  Ejemplo de uso

Realiza una consulta GET para obtener el precio de un producto:

    curl "http://localhost:8080/api/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00"

Esto devolverá el precio correspondiente para el producto con productId=35455, brandId=1 y la fecha de aplicación 2020-06-14T10:00:00.

###  Ejecutar Pruebas

Puedes ejecutar todas las pruebas del proyecto con el siguiente comando:
    
    mvn test
Las pruebas están divididas en:

  - Pruebas unitarias: Testean los servicios y la lógica de negocio.
  - Pruebas de integración: Testean los controladores REST y la interacción con la base de datos en memoria H2.

###  Configuración de la base de datos
El proyecto utiliza una base de datos H2 en memoria para las pruebas. Los datos se inicializan mediante los scripts schema.sql y data.sql ubicados en src/main/resources/database/.

El archivo application.yml está configurado para habilitar la consola H2:

    spring:
      datasource:
         url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;
         driver-class-name: org.h2.Driver
         username: sa
         password:
      jpa:
         hibernate:
            ddl-auto: create
         show-sql: true
      h2:
         console:
            enabled: true
      sql:
         init:
            mode: always
            schema-locations: classpath:/database/schema.sql
            data-locations: classpath:/database/data.sql

###  Manejo de Excepciones
El sistema gestiona las excepciones de forma centralizada mediante @ControllerAdvice. Si no se encuentra el precio para una consulta, se lanzará una excepción PriceNotFoundException, que será capturada y gestionada de forma adecuada, devolviendo un mensaje de error con el estado HTTP 404 Not Found.
Todas las demás excepciones se gestionarán de forma genérica utilizando el mismo controlador y mostrando el mensaje de error.

###  Cobertura de Código
La cobertura de las pruebas se puede consultar utilizando JaCoCo. Al ejecutar las pruebas, se generará un informe de cobertura en target/site/jacoco/index.html:

    mvn test jacoco:report


###  Mejoras y Consideraciones
- Escalabilidad: Este diseño permite añadir nuevos tipos de reglas de precios o nuevas entidades sin afectar significativamente al código base, gracias a la arquitectura modular y desacoplada.
- Manejo de Excepciones: Se ha implementado un manejo de excepciones global para que cualquier error en la aplicación sea gestionado de manera coherente y centralizada.
- Git: Aunque este proyecto solo cuenta con un único commit, por diversas razones de desarrollo, lo ideal sería que tuviese múltiples de ellos,
    de forma que de forma descriptiva y visualmente, se pudiera seguir el flujo del desarrollo tomado. El ejemplo de estructura que seguiría, sería el siguiente:
  1. Configuración inicial: Conjunto de commits para configurar el entorno y establecer las bases del desarrollo.
     1. Dependencias
     2. Estructura de carpetas
  2. Modelado del dominio: Reglas de negocio y clases principales del dominio.
     1. Entidades
     2. Métodos necesarios de las mismas
     3. Tests unitarios
  3. Configuración de la base de datos: Establecer el acceso a datos y los datos iniciales.
     1. Integración JPA y H2
     2. Scripts
     3. Tests de integración con los repositorios
  4. Lógica de negocio: Creación de los servicios y la lógica para cálculos pertinentes.
     1. Servicios
     2. Tests de los servicios
  5. Creación del API REST:
     1. Creación de los controladores
     2. Manejo de excepciones
     3. Tests de integración
  6. Pruebas: Crear pruebas para los diferentes casos de uso y mejorar la cobertura
     1. Pruebas extras para mejorar la cobertura
  7. Optimización y documentacion: Limpiar el código y añadir la documentación necesaria.
     1. Creación de README
     2. Crear report de Jacoco
  8. Mejoras y consideraciones finales: 
     1. Ajustes basados en pruebas
     2. Ajustes basados en retroalimentación
     3. Ajustes basados en feedback técnico
