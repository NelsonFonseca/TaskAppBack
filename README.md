# TaskAppBack
Este proyecto es un backend de una aplicación de gestión de tareas desarrollado con **Java** y **Spring Boot**. La aplicación permite realizar operaciones CRUD (crear, leer, actualizar y eliminar tareas) y utiliza **H2** como base de datos en memoria para simplificar la configuración y el despliegue en entornos de desarrollo.

## Tabla de Contenidos
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)
- [Ejecución en Local](#ejecución-en-local)
- [Decisiones Técnicas](#decisiones-técnicas)
- [Posibles Mejoras](#posibles-mejoras)

---

## Estructura del Proyecto

```bash
src/main/java/com/infolaft/task
  ├── controller
  ├── service
      ├── impl
  ├── repository
  ├── model
  └── TaskApplication.java

```

---

## Requisitos

Para ejecutar este proyecto, necesitas:

- **Java 17** o superior
- **Maven** 3.6 o superior
- **Docker** (opcional, si deseas usar `docker-compose`)

---

## Ejecución en Local

### 1. Clonar el Repositorio

```bash
git clone https://github.com/NelsonFonseca/TaskAppBack.git
cd TaskAppBack
```

### 2. Compilar y Empaquetar la Aplicación

Utiliza el siguiente comando para compilar y empaquetar el proyecto en un archivo .jar:
```bash
./mvnw clean package
```

### 3. Ejecutar la Aplicación con H2 en Memoria

Ejecuta el siguiente comando para iniciar la aplicación localmente:
```bash
java -jar target/task-0.0.1-SNAPSHOT.jar
```

### 4. Acceso a la API y Consola H2

- **API REST:** La API estará disponible en http://localhost:9005.
- **Documentación Swagger:** Accede a la documentación swagger del api en http://localhost:9005/swagger-ui.html.
- **Consola de H2:** Accede a la base de datos en memoria en http://localhost:9005/h2-console.
  - **JDBC URL:** jdbc:h2:mem:testdb
  - **Usuario:** sa
  - **Contraseña:** (dejar en blanco)

### 5. (Opcional) Ejecutar con Docker y Docker Compose

Si deseas ejecutar el backend en un contenedor Docker, asegúrate de que el archivo Dockerfile y docker-compose.yml estén en el directorio raíz del proyecto.

Construye la imagen de Docker:
```bash
docker build -t task-backend .
```

Ejecuta los servicios con docker-compose:
```bash
docker-compose up -d
```

---

## Decisiones Técnicas

1. **Framework de Java:** Elegimos **Spring Boot** por su flexibilidad para construir aplicaciones RESTful y su amplio ecosistema de bibliotecas para manejo de datos y configuración automática.
2. **Base de Datos H2 en Memoria:** H2 es una base de datos ligera que permite realizar pruebas sin necesidad de configurar un servidor de base de datos externo. Es ideal para desarrollo y pruebas locales. Además, al habilitar la consola H2, los desarrolladores pueden verificar el estado de los datos en tiempo real.
3. **Despliegue en Docker:** Usamos Docker para simplificar la configuración y ejecución en distintos entornos. Con Docker, la aplicación se ejecuta en un contenedor aislado que incluye todas las dependencias necesarias.

---

## Posibles Mejoras
1. **Persistencia con una Base de Datos Externa:** En producción, es recomendable utilizar una base de datos externa, como **MySQL** o **PostgreSQL**, para persistir los datos. Esto permite conservar los datos entre reinicios y en caso de errores inesperados.
2. **Pruebas Unitarias y de Integración:** Actualmente, la configuración incluye algunas pruebas unitarias básicas. Podrían añadirse más pruebas para cubrir todos los endpoints y validar cada caso de uso. Además, se pueden implementar pruebas de integración para verificar la interacción de los componentes.
3. **Autenticación y Autorización:** Se podría añadir un sistema de autenticación (por ejemplo, JWT) para asegurar los endpoints, especialmente en un contexto de producción
5. **Variables de Entorno:** En lugar de valores fijos en el archivo de configuración, es recomendable usar variables de entorno para datos sensibles (como credenciales de la base de datos) y configuración específica de cada entorno.



