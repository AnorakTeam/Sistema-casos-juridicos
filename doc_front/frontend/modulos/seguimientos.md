# Módulo: Seguimientos

## Propósito

Permite crear, editar, responder y gestionar seguimientos asociados a consultas jurídicas. Incluye categorías, alertas disciplinarias, respuestas de estudiantes y revisión de respuestas por asesores.

Ver `doc/api/seguimientos.md` y `doc/reglas/seguimientos.md` para la especificación completa del backend.

## Pantalla y ruta

| Ruta | Componente principal | Descripción |
|---|---|---|
| `/tareas` | `SeguimientosForm` | Listado de seguimientos y gestión de respuestas |

## Componentes

```text
src/components/forms/consulta/SeguimientosForm.jsx
src/components/Calendar.js
src/components/CalendarModal.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder tareas` | Mostrar el ítem en el menú lateral. |
| `Ver seguimientos` | Cargar seguimientos según alcance. |
| `Crear seguimientos` | Mostrar botón de crear seguimiento. |
| `Editar seguimientos` | Mostrar botones de edición. |
| `Eliminar seguimientos` | Mostrar botón de desactivar seguimiento. |
| `Responder seguimientos` | Mostrar formulario de respuesta (estudiante). |
| `Aprobar respuestas de seguimiento` | Mostrar panel de revisión de respuestas. |
| `Ver alertas disciplinarias` | Mostrar seguimientos marcados como alerta. |
| `Gestionar categorías de seguimiento` | Mostrar gestión de categorías. |

## Endpoints consumidos

### Cargar seguimientos por consulta

```text
GET /api/seguimientos/consulta/{consultaId}
GET /api/seguimientos/consulta/{consultaId}/visibles-estudiante   ← solo para estudiantes
```

### Crear seguimiento

```text
POST /api/seguimientos
```

### Editar seguimiento

```text
PUT /api/seguimientos/{id}
```

### Cambiar estado de seguimiento

```text
PATCH /api/seguimientos/{id}/estado?estado=COMPLETADO
```

### Eliminar seguimiento

```text
DELETE /api/seguimientos/{id}
```

### Respuestas de seguimiento

```text
GET  /api/seguimientos/{seguimientoId}/respuestas
POST /api/seguimientos/{seguimientoId}/respuestas       ← crear respuesta (estudiante)
PUT  /api/seguimientos/respuestas/{respuestaId}         ← editar respuesta
POST /api/seguimientos/respuestas/{respuestaId}/decision ← aprobar o rechazar
```

### Respuestas pendientes de revisión

```text
GET /api/seguimientos/respuestas/pendientes
```

### Categorías de seguimiento

```text
GET /api/seguimientos/categorias/activas
```

### Consultas accesibles

```text
GET /api/consultas
```

## Calendario de seguimientos

`Calendar.js` muestra los seguimientos del usuario autenticado en un calendario visual. Cada seguimiento aparece en el día de su `fechaEntrega`.

### Endpoints del calendario

```text
GET /api/seguimientos/autor/{autorId}   ← todos los seguimientos del autor
```

El `autorId` se obtiene del `perfilId` del usuario en `/api/auth/me`.

### Visibilidad por rol

| Rol | Qué ve en el calendario |
|---|---|
| Administrador | Todos los seguimientos. |
| Asesor / Monitor | Seguimientos de consultas dentro de su alcance. |
| Estudiante | Solo seguimientos marcados con `notificarEstudiante = true`. |
| Conciliador | No ve seguimientos hasta que el módulo esté definido. |

El calendario es accesible desde el header global de la aplicación a través de `CalendarModal`.

## Comportamiento por rol en la pantalla de tareas

### Estudiante

- Carga seguimientos con el endpoint `/visibles-estudiante`.
- Puede responder seguimientos que lo permitan.
- No puede crear, editar ni eliminar seguimientos.

### Asesor / Monitor / Administrador

- Carga todos los seguimientos de las consultas en su alcance.
- Puede crear, editar, cambiar estado y eliminar seguimientos.
- Puede revisar y calificar respuestas de estudiantes.

## Estados

| Estado seguimiento | Descripción |
|---|---|
| `PENDIENTE` | Seguimiento activo en gestión. |
| `COMPLETADO` | Seguimiento finalizado. |
| `CANCELADO` | Seguimiento cancelado. |

| Estado respuesta | Descripción |
|---|---|
| `PENDIENTE` | Enviada, pendiente de revisión. |
| `APROBADA` | Revisada y aprobada. |
| `RECHAZADA` | Revisada y rechazada. |
