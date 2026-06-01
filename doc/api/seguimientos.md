# API - Seguimientos y respuestas

## Base URL

Los endpoints se exponen bajo:

`/api/seguimientos`

Controllers:

- `SeguimientoController`
- `SeguimientoRespuestaController`
- `CategoriaSeguimientoController`

## DTO de seguimiento

`SeguimientoRequestDTO` contiene los datos de entrada para creación y actualización.

Campos principales:

| Campo | Descripción |
|---|---|
| `id` | No debe enviarse en creación. |
| `descripcion` | Obligatoria, máximo 200 caracteres normalizados. |
| `fechaEntrega` | Fecha límite. No puede ser anterior a la fecha actual. |
| `diasNotificacion` | Días previos para recordatorio. No puede ser negativo. |
| `notificarPartes` | Activa notificación a partes. |
| `notificarEstudiante` | Activa visibilidad/notificación para estudiante. Requiere estudiante activo en la consulta. |
| `alertaDisciplinaria` | Marca de alerta disciplinaria. |
| `categoriaSeguimientoId` | Categoría activa del seguimiento. |
| `consultaId` | Consulta asociada. |

## Endpoints de seguimientos

### Listar por consulta

`GET /api/seguimientos/consulta/{consultaId}`

Permiso: `VER_SEGUIMIENTOS`.

Retorna seguimientos asociados a una consulta, respetando alcance.

### Listar visibles para estudiante por consulta

`GET /api/seguimientos/consulta/{consultaId}/visibles-estudiante`

Permiso: `VER_SEGUIMIENTOS`.

Retorna seguimientos marcados como visibles/notificables para el estudiante de la consulta.

### Listar por autor

`GET /api/seguimientos/autor/{autorId}`

Permiso: `VER_SEGUIMIENTOS`.

Retorna seguimientos creados por un usuario autor.

### Listar alertas disciplinarias

`GET /api/seguimientos/alertas-disciplinarias`

Permiso: `VER_ALERTAS_DISCIPLINARIAS`.

Retorna seguimientos marcados como alerta disciplinaria.

### Listar por fecha de entrega

`GET /api/seguimientos/fecha-entrega?fechaEntrega=YYYY-MM-DD`

Permiso: `VER_SEGUIMIENTOS`.

El parámetro `fechaEntrega` usa formato ISO de fecha.

### Obtener seguimiento por id

`GET /api/seguimientos/{id}`

Permiso: `VER_SEGUIMIENTOS`.

### Crear seguimiento

`POST /api/seguimientos`

Permiso: `CREAR_SEGUIMIENTOS`.

Ejemplo:

```json
{
  "descripcion": "Aportar documento de soporte",
  "fechaEntrega": "2026-06-15",
  "diasNotificacion": 3,
  "notificarPartes": false,
  "notificarEstudiante": true,
  "alertaDisciplinaria": false,
  "categoriaSeguimientoId": 1,
  "consultaId": 20
}
```

Reglas:

- El seguimiento nace `PENDIENTE`.
- Se guarda como activo.
- Si `notificarEstudiante=true`, la consulta debe tener estudiante activo.
- Las notificaciones se sincronizan después de guardar.

### Actualizar seguimiento

`PUT /api/seguimientos/{id}`

Permiso: `EDITAR_SEGUIMIENTOS`.

Reglas:

- Solo seguimientos pendientes son editables.
- No se permite cambiar la consulta.
- Deben existir cambios reales.
- Se recalculan efectos de notificación según el estado.

### Cambiar estado

`PATCH /api/seguimientos/{id}/estado?estado=COMPLETADO`

Permiso: `EDITAR_SEGUIMIENTOS`.

Estados:

- `PENDIENTE`
- `COMPLETADO`
- `CANCELADO`

### Eliminar seguimiento

`DELETE /api/seguimientos/{id}`

Permiso: `ELIMINAR_SEGUIMIENTOS`.

La eliminación se maneja como desactivación lógica. Antes se cancelan notificaciones pendientes.

## Endpoints de respuestas

### Crear respuesta

`POST /api/seguimientos/{seguimientoId}/respuestas`

Permiso: `RESPONDER_SEGUIMIENTOS`.

Permite que el estudiante responda un seguimiento visible para él.

### Actualizar respuesta

`PUT /api/seguimientos/respuestas/{id}`

Permiso: `RESPONDER_SEGUIMIENTOS`.

Permite modificar una respuesta mientras esté pendiente.

### Obtener respuesta

`GET /api/seguimientos/respuestas/{id}`

Permiso: `VER_SEGUIMIENTOS`.

### Listar respuestas por seguimiento

`GET /api/seguimientos/{seguimientoId}/respuestas`

Permiso: `VER_SEGUIMIENTOS`.

### Listar respuestas pendientes

`GET /api/seguimientos/respuestas/pendientes`

Permiso: `APROBAR_RESPUESTAS_SEGUIMIENTO`.

### Aprobar o rechazar respuesta

`PATCH /api/seguimientos/respuestas/{id}/decision`

Permiso: `APROBAR_RESPUESTAS_SEGUIMIENTO`.

Cuerpo: `SeguimientoRespuestaDecisionDTO`.

Ejemplo de aprobación:

```json
{
  "estado": "APROBADA",
  "observacionRevision": "Respuesta revisada correctamente."
}
```

Ejemplo de rechazo:

```json
{
  "estado": "RECHAZADA",
  "observacionRevision": "Debe complementar la respuesta con los documentos solicitados."
}
```

Reglas:

- Solo se aceptan decisiones `APROBADA` o `RECHAZADA`.
- `RECHAZADA` exige observación.
- Observación máxima: 500 caracteres.

## Categorías de seguimiento

Las categorías organizan los seguimientos. El módulo cuenta con controller y service propios para administrar categorías activas, validaciones de duplicado y cambios de estado lógico.

## Errores funcionales frecuentes

| Caso | Resultado |
|---|---|
| Fecha de entrega anterior a la actual | Error de negocio. |
| Días de notificación negativos | Error de negocio. |
| Días de notificación sin fecha de entrega | Error de negocio. |
| `notificarEstudiante=true` sin estudiante activo | Error de negocio. |
| Editar seguimiento no pendiente | Error de negocio. |
| Rechazar respuesta sin observación | Error de negocio. |
| Decisión `PENDIENTE` | Error de negocio. |
