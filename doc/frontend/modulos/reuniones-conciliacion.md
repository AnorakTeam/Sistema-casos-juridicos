# Módulo: Reuniones de conciliación

## Propósito

Este documento describe la implementación frontend de la funcionalidad de programación y reprogramación de reuniones de conciliación.

Ver `doc/api/conciliaciones.md` y `doc/reglas/conciliaciones.md` para la especificación completa del backend.

## Componente

```text
src/components/forms/conciliacion/ReunionesConciliacionForm.jsx
```

La programación y reprogramación de reuniones se gestiona dentro del módulo de conciliaciones en la ruta `/conciliaciones`.

## Permisos

| Permiso | Uso |
|---|---|
| `Programar reuniones de conciliación` | Mostrar botón "Programar reunión" cuando `reunion` es `null`. |
| `Reprogramar reuniones de conciliación` | Mostrar botón "Reprogramar reunión" cuando `reunion` existe. |

Tener permiso no garantiza que el backend autorice la acción. El backend valida además que el usuario esté asignado a la conciliación.

## Flujo funcional

```text
1. El usuario abre el listado de conciliaciones.
2. El frontend carga las conciliaciones con GET /api/conciliaciones.
3. Cada conciliación incluye el campo "reunion" en la respuesta.
4. Si reunion es null y el usuario tiene permiso "Programar reuniones de conciliación":
   → Se muestra el botón "Programar reunión".
5. Si reunion existe y el usuario tiene permiso "Reprogramar reuniones de conciliación":
   → Se muestra el botón "Reprogramar reunión".
6. El usuario completa el formulario y confirma.
7. El frontend envía la petición al backend.
8. El backend guarda la reunión, actualiza el estado de la conciliación y registra el historial.
9. El frontend recarga la conciliación y muestra la información actualizada.
```

## Endpoints

### Consultar detalle de conciliación (incluye reunion)

```text
GET /api/conciliaciones/{id}
```

Permiso requerido: `Ver conciliaciones`.

Fragmento relevante de la respuesta:

```json
{
  "id": 2,
  "consultaId": 21,
  "estadoCodigo": "REUNION_PROGRAMADA",
  "estadoNombre": "Reunión programada",
  "reunion": {
    "conciliacionId": 2,
    "fechaReunion": "2026-06-10T14:30:00",
    "sedeId": 1,
    "sedeNombre": "Sede principal",
    "observaciones": "Primera programación de reunión de conciliación.",
    "fechaCreacion": "2026-05-27T22:30:00",
    "fechaActualizacion": null
  }
}
```

El campo `reunion` es `null` cuando la conciliación no tiene reunión registrada.

**El frontend usa `reunion.fechaReunion` como fuente de verdad para mostrar la fecha de la reunión. No debe usar `conciliacion.fechaConciliacion`.**

### Cargar sedes para el selector

```text
GET /api/sedes
```

Permiso requerido: sesión válida.

### Programar reunión

```text
POST /api/conciliaciones/{id}/reunion
```

Permiso requerido: `Programar reuniones de conciliación`.

Se usa cuando `reunion` es `null`.

### Reprogramar reunión

```text
PUT /api/conciliaciones/{id}/reunion
```

Permiso requerido: `Reprogramar reuniones de conciliación`.

Se usa cuando `reunion` ya existe.

**No existe un endpoint `GET /api/conciliaciones/{id}/reunion` separado. La información de la reunión se obtiene del campo `reunion` en el detalle de la conciliación.**

## Formulario

### Campos

| Campo | Tipo de control | Validación frontend | Campo API |
|---|---|---|---|
| Fecha y hora de reunión | `datetime-local` | Obligatorio. No puede ser en el pasado. | `fechaReunion` |
| Sede | Select de sedes activas | Obligatorio. | `sedeId` |
| Observaciones | Textarea | Opcional. Máximo 300 caracteres. | `observaciones` |

### Validaciones frontend antes de enviar

- `fechaReunion` no puede estar vacía.
- `fechaReunion` no puede ser una fecha pasada ni igual al momento actual.
- `sedeId` no puede estar vacío.
- `observaciones` no puede superar 300 caracteres.

Estas validaciones son adicionales a las del backend. El backend siempre valida por su cuenta.

### Request para programar o reprogramar

```json
{
  "fechaReunion": "2026-06-10T14:30:00",
  "sedeId": 1,
  "observaciones": "Primera programación de reunión de conciliación."
}
```

El campo `observaciones` es opcional. Si está vacío se puede omitir o enviar como `null`.

### Response esperado (201 para programar, 200 para reprogramar)

```json
{
  "conciliacionId": 2,
  "fechaReunion": "2026-06-10T14:30:00",
  "sedeId": 1,
  "sedeNombre": "Sede principal",
  "observaciones": "Primera programación de reunión de conciliación.",
  "fechaCreacion": "2026-05-27T22:30:00",
  "fechaActualizacion": null
}
```

## Manejo de errores

| HTTP | Situación | Comportamiento del frontend |
|---|---|---|
| `400` | Fecha pasada, sede vacía, observaciones demasiado largas, reunión ya existente cuando se intenta programar. | Mostrar toast con el mensaje exacto del backend. Mantener el formulario editable. |
| `401` | Sesión expirada. | Redirigir a `/`. |
| `403` | Sin permiso o conciliador no asignado a esta conciliación. | Mostrar toast de error sin redirigir. |
| `404` | Conciliación o sede no encontrada. | Mostrar mensaje y cerrar el formulario. |
| `500` | Error inesperado. | Mostrar mensaje general y permitir reintentar. |

## Lógica de visibilidad del botón

```javascript
// Mostrar "Programar reunión"
const puedeProgramar =
  conciliacion.reunion === null &&
  !estadoFinalizado(conciliacion.estadoCodigo) &&
  tienePermiso(user, PERMISOS.PROGRAMAR_REUNIONES_CONCILIACION);

// Mostrar "Reprogramar reunión"
const puedeReprogramar =
  conciliacion.reunion !== null &&
  !estadoFinalizado(conciliacion.estadoCodigo) &&
  tienePermiso(user, PERMISOS.REPROGRAMAR_REUNIONES_CONCILIACION);
```

Los botones no se muestran en conciliaciones con estado finalizado (`COMPLETO_CONCILIADO` o `COMPLETO_NO_CONCILIADO`).

## Lo que NO debe hacer el frontend

- No cambiar manualmente el estado de la conciliación a `REUNION_PROGRAMADA`. El backend lo hace al programar la reunión.
- No construir rutas internas de archivos del backend.
- No asumir que el usuario puede programar solo porque ve la conciliación. El alcance lo valida el backend.
- No duplicar la fecha de reunión en un estado local persistente aparte del campo `reunion` en el detalle.
- No llamar a un endpoint `GET /api/conciliaciones/{id}/reunion` porque no existe.

## Checklist de pruebas frontend

- [ ] El botón "Programar reunión" aparece cuando `reunion` es `null` y el usuario tiene permiso.
- [ ] El botón "Reprogramar reunión" aparece cuando `reunion` existe y el usuario tiene permiso.
- [ ] Ningún botón aparece en conciliaciones finalizadas.
- [ ] El formulario no envía si `fechaReunion` está vacía.
- [ ] El formulario no envía si `fechaReunion` es una fecha pasada.
- [ ] El formulario no envía si `sedeId` está vacío.
- [ ] El campo `observaciones` no acepta más de 300 caracteres.
- [ ] Al guardar correctamente, se muestra toast de éxito y se recarga la conciliación.
- [ ] El campo `reunion.fechaReunion` del detalle se usa para mostrar la fecha, no `conciliacion.fechaConciliacion`.
- [ ] El 403 muestra toast sin redirigir.
- [ ] El 400 muestra el mensaje del backend y mantiene el formulario abierto.
- [ ] El 401 redirige a `/`.
