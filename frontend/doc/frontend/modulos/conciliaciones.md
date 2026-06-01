# Módulo: Conciliaciones

## Propósito

El módulo de conciliaciones permite gestionar conciliaciones asociadas a consultas jurídicas. Incluye la creación, seguimiento de estado, asignación de responsables, subida de documentos PDF y finalización.

Ver `doc/api/conciliaciones.md` y `doc/reglas/conciliaciones.md` para la documentación completa de backend.

## Pantallas y rutas

| Ruta | Componente principal | Descripción |
|---|---|---|
| `/conciliaciones` | `ConciliacionesForm` + `ReunionesConciliacionForm` | Listado de conciliaciones y gestión de reuniones |

## Componentes

```text
src/components/forms/conciliacion/ConciliacionesForm.jsx
src/components/forms/conciliacion/ReunionesConciliacionForm.jsx
```

## Permisos requeridos

| Permiso | Uso en frontend |
|---|---|
| `Acceder conciliaciones` | Mostrar el módulo en el menú lateral. |
| `Ver conciliaciones` | Cargar y mostrar el listado de conciliaciones. |
| `Gestionar conciliaciones` | Crear conciliaciones, asignar conciliador, reemplazar solicitud, desactivar. |
| `Concluir conciliaciones` | Acciones de flujo y finalización para conciliador asignado. |
| `Programar reuniones de conciliación` | Mostrar botón "Programar reunión". |
| `Reprogramar reuniones de conciliación` | Mostrar botón "Reprogramar reunión". |

## Endpoints consumidos

### Listado de conciliaciones

```text
GET /api/conciliaciones
```

Devuelve conciliaciones según el alcance del usuario. El frontend no filtra; el backend aplica las reglas de alcance.

### Detalle de conciliación

```text
GET /api/conciliaciones/{id}
```

Incluye el objeto `reunion` si la conciliación tiene una reunión programada.

### Crear conciliación

```text
POST /api/conciliaciones
Content-Type: multipart/form-data
```

Envía el formulario con la solicitud PDF y los datos de la conciliación.

### Asignar conciliador

```text
PATCH /api/conciliaciones/{id}/conciliador?conciliadorId={id}
```

### Cambiar estado operativo

```text
PATCH /api/conciliaciones/{id}/estado
```

### Reemplazar solicitud PDF

```text
PATCH /api/conciliaciones/{id}/solicitud
Content-Type: multipart/form-data
```

### Finalizar con acta PDF

```text
PATCH /api/conciliaciones/{id}/finalizar
Content-Type: multipart/form-data
```

### Desactivar conciliación

```text
DELETE /api/conciliaciones/{id}
```

### Programar reunión

```text
POST /api/conciliaciones/{id}/reunion
```

Ver `doc/frontend/modulos/reuniones-conciliacion.md` para la documentación completa.

### Reprogramar reunión

```text
PUT /api/conciliaciones/{id}/reunion
```

Ver `doc/frontend/modulos/reuniones-conciliacion.md` para la documentación completa.

## Estados de conciliación

El frontend usa `estadoCodigo` (no `estadoNombre`) para controlar la lógica de visibilidad de acciones.

| Código | Etiqueta visible | Acciones disponibles |
|---|---|---|
| `EN_ESPERA` | En espera | Asignar conciliador si aplica |
| `ESPERANDO_REUNION` | Esperando reunión | Programar reunión |
| `REUNION_PROGRAMADA` | Reunión programada | Reprogramar reunión, finalizar |
| `COMPLETO_CONCILIADO` | Completo - conciliado | Solo lectura |
| `COMPLETO_NO_CONCILIADO` | Completo - no conciliado | Solo lectura |

Los estados `COMPLETO_CONCILIADO` y `COMPLETO_NO_CONCILIADO` son estados finalizados. El frontend bloquea las acciones de flujo sobre conciliaciones finalizadas.

## Visibilidad de acciones por rol

| Acción | Administrador | Conciliador | Estudiante | Asesor/Monitor |
|---|---|---|---|---|
| Ver conciliaciones | Sí, según permisos | Sí, solo asignadas | Sí, según alcance | Sí, según alcance |
| Crear conciliación | Sí | No | No | No |
| Asignar conciliador | Sí | No | No | No |
| Programar reunión | Sí | Sí, solo asignado | No | No |
| Reprogramar reunión | Sí | Sí, solo asignado | No | No |
| Finalizar | Sí | Sí, solo asignado | No | No |
| Desactivar | Sí | No | No | No |

La visibilidad en el frontend se basa en permisos y en la función `esConciliador(user)`. El alcance real lo valida el backend.

## Alcance

El conciliador solo puede actuar sobre conciliaciones donde está asignado. El frontend puede ocultar acciones basándose en si el `conciliadorId` de la conciliación coincide con el `perfilId` del usuario, pero el backend siempre valida el alcance real.

## Documentos PDF

La conciliación maneja dos documentos PDF:

- **Solicitud PDF**: obligatoria al crear la conciliación. Se puede reemplazar después.
- **Acta PDF**: se envía al finalizar la conciliación.

El frontend usa `FILE_STORAGE_API_URL_BASE` para la descarga de documentos existentes y `API_URL_BASE` para la subida (a través de los endpoints de conciliación, no directamente al storage).

## Feedback al usuario

- Toast de éxito al crear, asignar, finalizar o desactivar.
- Toast de error con el mensaje del backend en caso de fallo.
- El listado se recarga automáticamente después de cada acción exitosa.
- Las conciliaciones finalizadas muestran un indicador visual diferenciado.
