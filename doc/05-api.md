# API

## 1. Propósito

Este documento describe los endpoints REST implementados en el backend.  
Incluye recursos, métodos HTTP, rutas y comportamiento general.

---

## 2. Convenciones

### Formato
La API utiliza JSON para entrada y salida.

### Prefijo base

`/api`

### Métodos HTTP

- `GET`: consulta
- `POST`: creación
- `PUT`: actualización
- `DELETE`: eliminación

### Códigos de respuesta

- `200 OK`
- `201 Created`
- `204 No Content`
- `400 Bad Request`
- `500 Internal Server Error`

---

## 3. Recurso: Áreas

Base: `/api/areas`


Controlador: `AreaController`

### Endpoints

- **GET /**  
  Lista todas las áreas.

- **GET /{id}**  
  Consulta un área por id.

- **POST /**  
  Crea un área.

- **PUT /{id}**  
  Actualiza un área.

- **DELETE /{id}**  
  Elimina un área.

---

## 4. Recurso: Temas

Base: `/api/temas`


Controlador: `TemaController`

### Endpoints

- **GET /**  
  Lista todos los temas.

- **GET /{id}**  
  Consulta un tema por id.

- **GET /area/{areaId}**  
  Lista temas por área.

- **POST /**  
  Crea un tema.

- **PUT /{id}**  
  Actualiza un tema.

- **DELETE /{id}**  
  Elimina un tema.

---

## 5. Recurso: Tipos

Base: `/api/tipos`


Controlador: `TipoController`

### Endpoints

- **GET /**  
  Lista todos los tipos.

- **GET /{id}**  
  Consulta un tipo por id.

- **GET /tema/{temaId}**  
  Lista tipos por tema.

- **POST /**  
  Crea un tipo.

- **PUT /{id}**  
  Actualiza un tipo.

- **DELETE /{id}**  
  Elimina un tipo.

---

## 6. Recurso: Personas

Base: `/api/personas`

Controlador: `PersonaController`

### Endpoints

- **GET /**  
  Lista todas las personas.

- **GET /{id}**  
  Consulta una persona por id.

- **POST /**  
  Crea una persona.

- **PUT /{id}**  
  Actualiza una persona.

- **DELETE /{id}**  
  Elimina una persona.

---

(AQUI LOS DEMAS ENDPOINTS QUE SE AGREGUEN CON:
    ## X. Recurso: XX

    Base: `/api/XX`

    Controlador: `XXController`

    ### Endpoints
)

---

## 7. Endpoint de prueba

### GET /hello

Endpoint temporal para verificación del sistema.  
No forma parte de la API funcional.

---

## 8. Alcance

Este documento describe los endpoints y su estructura general.  
Las validaciones, reglas de negocio y detalles específicos se documentan por separado.