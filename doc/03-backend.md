# Backend

## 1. Propósito

El backend implementa la lógica de negocio y expone una API REST.  
Recibe solicitudes, valida datos, aplica reglas funcionales, gestiona la persistencia y retorna respuestas estructuradas.

---

## 2. Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL

> Nota: H2 se utilizó únicamente para pruebas iniciales y no forma parte de la configuración final.

---

## 3. Arquitectura

El backend sigue una arquitectura en capas organizada por dominio.

### Capas

- **Controller**: recibe solicitudes HTTP y expone endpoints.
- **Service**: contiene la lógica de negocio y validaciones.
- **Repository**: acceso a datos mediante JPA.
- **Model**: entidades persistentes.
- **DTO**: estructuras de entrada y salida.
- **Exception**: manejo centralizado de errores.

---

## 4. Flujo de procesamiento

    Cliente → Controller → Service → Repository → Base de datos

1. El cliente envía una petición HTTP.
2. El controller valida la entrada.
3. El service aplica reglas de negocio.
4. El repository interactúa con la base de datos.
5. La respuesta retorna al cliente.

---

## 5. Estructura del proyecto

src/main/java/co/edu/ufps/legal_cases/
├── controller
├── dto
├── exception
├── model
├── repository
├── service
└── App


### Descripción

- **controller**: endpoints REST.
- **dto**: objetos de transferencia.
- **exception**: manejo de errores.
- **model**: entidades JPA.
- **repository**: acceso a datos.
- **service**: lógica de negocio.

---

## 6. Implementación

### Entidades
Representan tablas y relaciones en la base de datos usando JPA.

### DTO
Se usan para:
- definir entrada y salida de la API,
- evitar exponer entidades,
- aplicar validaciones.

### Controllers
- reciben solicitudes,
- validan entrada (`@Valid`),
- delegan al service,
- retornan JSON.

### Services
- aplican reglas de negocio,
- validan lógica funcional,
- normalizan datos,
- convierten entre entidad y DTO.

### Repositories
- manejan operaciones CRUD,
- soportan consultas específicas.

---

## 7. Persistencia

La persistencia se implementa con Spring Data JPA sobre PostgreSQL.

### Características

- mapeo objeto-relacional (JPA),
- generación de tablas a partir de entidades,
- consultas derivadas por nombre de método,
- manejo de relaciones entre entidades.

---

## 8. Manejo de errores

Se implementa un manejo centralizado mediante `@RestControllerAdvice`.

### Tipos de errores

- validación (DTO),
- negocio (`BusinessException`),
- internos (`Exception`).

### Objetivo

- respuestas consistentes,
- mensajes claros,
- diferenciación de errores.

---

## 9. Validación de datos

### Validación estructural
Se aplica en DTO con anotaciones:

- `@NotBlank`
- `@Size`
- `@NotNull`
- etc.

### Validación funcional
Se aplica en servicios:

- duplicados,
- relaciones,
- restricciones del negocio.

---

## 10. Módulos implementados

- gestión de personas,
- áreas,
- temas,
- tipos.
- (COLOCAR LOS QUE FALTAN CUANDO ESTEN LISTOS)

---

## 11. Diseño

### Separación de responsabilidades
Cada capa tiene una función específica.

### Bajo acoplamiento
Las capas se comunican mediante contratos claros.

### Escalabilidad
Permite agregar nuevos módulos sin afectar la estructura.

### Trazabilidad
Las reglas de negocio están centralizadas en servicios.

---

## 12. Documentación relacionada

- `modelo-datos.md`
- `api.md`
- `validaciones-y-reglas.md`
- `pruebas.md`

---

## 13. Alcance

Este documento describe la arquitectura y organización del backend.  
El detalle de endpoints, reglas y modelos se documenta por separado.