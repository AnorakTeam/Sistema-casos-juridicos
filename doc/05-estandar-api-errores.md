# Estándar de API y manejo de errores

## Convención general

El backend expone una API REST bajo el prefijo:

```text
/api
```

Los controllers usan métodos HTTP según la operación:

| Método | Uso general |
|---|---|
| `GET` | Consulta de recursos, listados, detalle, reportes y descargas. |
| `POST` | Creación, acciones con cuerpo multipart o acciones funcionales que crean artefactos. |
| `PUT` | Actualización de datos generales. |
| `PATCH` | Cambio de estado, activación/desactivación o acciones parciales. |
| `DELETE` | Eliminación lógica o desactivación según módulo. |

## Separación entre edición y ciclo de vida

El código fuente distingue edición de datos generales y cambios de estado. En módulos como consultas, procesos, seguimientos, conciliaciones, roles, permisos y perfiles, los cambios de estado o activación usan endpoints `PATCH` específicos.

Esto evita que un `PUT` de datos generales modifique campos de control funcional.

## Autenticación en API

Salvo endpoints públicos declarados en `SecurityConfig`, las peticiones requieren autenticación. El token JWT viaja en cookie HTTP-only y el frontend debe enviar peticiones con:

```javascript
credentials: "include"
```

## Endpoints públicos

La configuración de seguridad permite como públicos:

```text
POST /api/auth/login
POST /api/auth/logout
POST /api/auth/solicitar-recuperacion
POST /api/auth/restablecer-password
GET  /v3/api-docs/**
GET  /swagger-ui/**
GET  /swagger-ui.html
```

También permite `OPTIONS /**` para preflight CORS.

## ErrorResponseDTO

El backend usa un DTO estándar para errores:

```json
{
  "fecha": "2026-01-01T10:00:00",
  "estado": 400,
  "error": "Error de negocio",
  "mensaje": "Mensaje descriptivo",
  "ruta": "/api/recurso"
}
```

Cuando hay errores de validación puede incluir `detalles`:

```json
{
  "fecha": "2026-01-01T10:00:00",
  "estado": 400,
  "error": "Error de validación",
  "mensaje": "Uno o más campos no son válidos",
  "ruta": "/api/recurso",
  "detalles": {
    "campo": "Mensaje de validación"
  }
}
```

## Manejador global

`GlobalExceptionHandler` administra respuestas para:

- `BusinessException`;
- errores de validación de DTO con `@Valid`;
- violaciones de restricciones;
- parámetros inválidos;
- parámetros obligatorios faltantes;
- cuerpos JSON inválidos;
- métodos HTTP no permitidos;
- acceso denegado;
- errores no controlados.

## Códigos HTTP principales

| Código | Uso |
|---|---|
| `200 OK` | Consulta o acción exitosa con cuerpo. |
| `204 No Content` | Acción exitosa sin cuerpo, por ejemplo cambio de contraseña. |
| `400 Bad Request` | Regla de negocio, validación o solicitud inválida. |
| `401 Unauthorized` | Sesión inexistente, inválida o no autenticada. |
| `403 Forbidden` | Usuario autenticado sin permiso o sin alcance. |
| `405 Method Not Allowed` | Método HTTP no soportado para el recurso. |
| `500 Internal Server Error` | Error no controlado. El detalle técnico queda en logs. |

## Manejo frontend

El frontend incluye utilidades en `src/lib/api.js` para leer respuestas, extraer título de error, extraer detalles y construir mensajes de interfaz.

El frontend debe mostrar el mensaje del backend cuando exista y usar detalles de validación cuando el objeto `detalles` venga presente.

## Endpoints por módulo

Los contratos detallados se documentan en `doc/api/`. La relación general de controllers observados es:

| Módulo | Prefijo API |
|---|---|
| Autenticación | `/api/auth` |
| Usuarios | `/api/usuarios-sistema` |
| Roles | `/api/roles` |
| Permisos | `/api/permisos` |
| Auditoría | `/api/audit` |
| Archivos | `/api/files` |
| Catálogos | `/api/areas`, `/api/temas`, `/api/tipos`, `/api/sedes`, entre otros |
| Personas | `/api/personas`, `/api/empresas`, `/api/condiciones`, entre otros |
| Perfiles | `/api/administrativos`, `/api/asesores`, `/api/estudiantes`, `/api/monitores`, `/api/conciliadores` |
| Consultas | `/api/consultas` |
| Procesos | `/api/procesos`, `/api/organos-control`, `/api/especialidades` |
| Seguimientos | `/api/seguimientos` |
| Conciliaciones | `/api/conciliaciones` |
| Estadísticas | `/api/estadisticas` |

## Archivos y multipart

Las operaciones de archivos y algunos flujos documentales usan `multipart/form-data`, por ejemplo:

- carga general de archivos;
- creación de conciliación con solicitud;
- finalización de conciliación con acta;
- reemplazo de solicitud;
- importación de estudiantes desde archivo;
- respuestas de seguimiento con archivos cuando aplica.
