# Inventario de API

Esta carpeta documenta los endpoints REST expuestos por el backend del sistema de gestión de casos jurídicos.

La documentación está orientada al consumo desde frontend, pruebas funcionales e integración entre módulos. Cada documento describe rutas, métodos HTTP, permisos, datos de entrada, respuestas esperadas y reglas relevantes aplicadas por backend.

## Base general

```text
/api
```

El frontend consume esta base mediante la configuración centralizada en `API_URL_BASE`.

## Autenticación y sesión

Los endpoints protegidos requieren cookie de sesión válida. El frontend envía solicitudes protegidas usando:

```javascript
credentials: "include"
```

La autenticación se documenta en:

```text
api/autenticacion.md
```

## Convenciones de contenido

| Tipo | Uso |
|---|---|
| JSON | Endpoints de consulta, creación, actualización y cambio de estado. |
| `multipart/form-data` | Endpoints con carga de documentos o archivos. |
| PDF | Descarga de reportes o documentos almacenados cuando aplica. |

## Documentos de API

| Documento | Módulo |
|---|---|
| `autenticacion.md` | Login, sesión, usuario actual, logout y recuperación/restablecimiento de contraseña. |
| `usuarios-roles-permisos.md` | Usuarios del sistema, roles, permisos, asignación de permisos y cambio de perfil. |
| `perfiles.md` | Administrativos, asesores, monitores, estudiantes y conciliadores. |
| `personas.md` | Personas naturales, empresas y búsqueda de personas activas. |
| `catalogos.md` | Tipos de documento, sedes, áreas, temas, tipos, órganos de control y especialidades. |
| `consultas.md` | Consultas jurídicas, responsables, cambio de estado, archivo y desarchivo. |
| `procesos.md` | Procesos asociados a consulta, radicado, estados y catálogos procesales. |
| `seguimientos.md` | Seguimientos, respuestas, revisión, categorías y notificaciones. |
| `conciliaciones.md` | Conciliaciones, responsables, reuniones, actas, estados y documentos. |
| `estadisticas.md` | Estadísticas por semestre, rango libre, perfil y reportes PDF. |
| `archivos.md` | Carga, descarga, listado y gestión de archivos. |
| `auditoria.md` | Consulta de registros de auditoría. |

## Permisos

La API combina permisos funcionales con reglas de alcance. La presencia de un permiso habilita una acción general, pero el backend puede aplicar controles adicionales según el perfil activo, la consulta relacionada, los responsables asignados o el estado funcional del recurso.

Ejemplos:

| Módulo | Control aplicado |
|---|---|
| Consultas | Permiso funcional y alcance por responsable o perfil. |
| Procesos | Alcance heredado desde la consulta asociada. |
| Seguimientos | Alcance según consulta, autor, estudiante y permisos de revisión. |
| Conciliaciones | Alcance por conciliador, estudiante asignado y permisos administrativos. |
| Estadísticas | Permisos `VER_REPORTES` y `VER_CONSULTAS` según endpoint. |
| Auditoría | Permiso administrativo de consulta de auditoría. |

## Respuestas y errores

Las respuestas de error se encuentran normalizadas por el backend mediante el manejador global de excepciones. Las reglas generales se describen en:

```text
../05-estandar-api-errores.md
```

Estados HTTP usados de forma habitual:

| Estado | Uso |
|---|---|
| `200 OK` | Consulta o actualización exitosa. |
| `201 Created` | Creación exitosa cuando el controller lo define. |
| `204 No Content` | Operación sin cuerpo de respuesta. |
| `400 Bad Request` | Validación de negocio o datos inválidos. |
| `401 Unauthorized` | Sesión ausente o inválida. |
| `403 Forbidden` | Usuario autenticado sin permiso o alcance suficiente. |
| `404 Not Found` | Recurso no encontrado o no disponible operacionalmente. |

## Relación con frontend

Los documentos de API se complementan con:

```text
frontend/configuracion-api.md
frontend/servicios-api.md
frontend/manejo-errores.md
frontend/modulos/*.md
```

El frontend no sustituye las validaciones del backend; únicamente organiza formularios, navegación, consumo de API y retroalimentación visual al usuario.
