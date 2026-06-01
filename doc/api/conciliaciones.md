# API - Conciliaciones y reuniones

## Base URL

`/api/conciliaciones`

Controller: `ConciliacionController`.

## Endpoints principales

### Listar conciliaciones

`GET /api/conciliaciones`

Permiso: `VER_CONCILIACIONES`.

Retorna lista de `ConciliacionResponseDTO`.

### Listar conciliaciones por consulta

`GET /api/conciliaciones/consulta/{consultaId}`

Permiso: `VER_CONCILIACIONES`.

### Obtener detalle

`GET /api/conciliaciones/{id}`

Permiso: `VER_CONCILIACIONES`.

Retorna `ConciliacionDetalleResponseDTO`.

### Crear conciliación desde consulta

`POST /api/conciliaciones/consulta/{consultaId}`

Permiso: `GESTIONAR_CONCILIACIONES`.

Tipo de contenido: multipart.

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `consultaId` | Path | Consulta origen. |
| `solicitud` | MultipartFile | Documento de solicitud. |

Reglas:

- La consulta no puede estar cerrada ni archivada.
- No debe existir conciliación activa no finalizada para la consulta.
- El sistema asigna responsables si encuentra perfiles disponibles.
- El estado se calcula según responsables.

### Programar reunión

`POST /api/conciliaciones/{id}/reunion`

Permiso: `PROGRAMAR_REUNIONES_CONCILIACION`.

Cuerpo: `ReunionConciliacionRequestDTO`.

```json
{
  "fechaReunion": "2026-06-20T09:00:00",
  "sedeId": 1,
  "observaciones": "Reunión inicial de conciliación."
}
```

Reglas:

- Fecha obligatoria.
- Sede obligatoria.
- Fecha futura.
- Conciliación no finalizada.
- Responsables mínimos existentes.

### Reprogramar reunión

`PUT /api/conciliaciones/{id}/reunion`

Permiso: `REPROGRAMAR_REUNIONES_CONCILIACION`.

Usa el mismo DTO de programación.

Reglas:

- Registra historial de reprogramación.
- Cancela notificaciones pendientes anteriores.
- Crea nuevas notificaciones y recordatorios.

### Asignar estudiante

`PATCH /api/conciliaciones/{id}/estudiante?estudianteId=10`

Permisos:

- `GESTIONAR_CONCILIACIONES`
- `CONCLUIR_CONCILIACIONES`

Reglas:

- Estudiante activo.
- Estudiante habilitado para conciliación.
- Conciliación no finalizada.

### Asignar conciliador

`PATCH /api/conciliaciones/{id}/conciliador?conciliadorId=5`

Permiso: `GESTIONAR_CONCILIACIONES`.

Reglas:

- Conciliador activo.
- Conciliación no finalizada.

### Cambiar estado

`PATCH /api/conciliaciones/{id}/estado?estado=ESPERANDO_REUNION`

Permisos:

- `GESTIONAR_CONCILIACIONES`
- `CONCLUIR_CONCILIACIONES`

Reglas:

- No permite estados finales.
- Los estados finales se aplican por endpoint de finalización con acta.
- `EN_ESPERA` se calcula automáticamente.
- `REUNION_PROGRAMADA` exige reunión programada.

### Finalizar conciliación

`POST /api/conciliaciones/{id}/finalizar`

Permisos:

- `GESTIONAR_CONCILIACIONES`
- `CONCLUIR_CONCILIACIONES`

Tipo de contenido: multipart.

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `estado` | RequestParam | `COMPLETO_CONCILIADO` o `COMPLETO_NO_CONCILIADO`. |
| `acta` | MultipartFile | Documento acta de finalización. |

Reglas:

- Solo permite estados finalizados.
- Guarda acta antes de asignar estado final.
- Registra fecha de finalización.
- Cancela notificaciones pendientes de reunión.

### Reemplazar solicitud

`POST /api/conciliaciones/{id}/solicitud`

Permiso: `GESTIONAR_CONCILIACIONES`.

Tipo de contenido: multipart.

Parámetro: `solicitud`.

### Desactivar conciliación

`DELETE /api/conciliaciones/{id}`

Permiso: `GESTIONAR_CONCILIACIONES`.

Respuesta: `204 No Content`.

Desactiva lógicamente la conciliación y cancela notificaciones pendientes.

## Estados técnicos

- `EN_ESPERA`
- `ESPERANDO_REUNION`
- `REUNION_PROGRAMADA`
- `COMPLETO_CONCILIADO`
- `COMPLETO_NO_CONCILIADO`

## Errores funcionales frecuentes

| Situación | Resultado |
|---|---|
| Crear sobre consulta cerrada | Error de negocio. |
| Crear sobre consulta archivada | Error de negocio. |
| Crear segunda conciliación activa no finalizada | Error de negocio. |
| Cambiar a estado final por endpoint normal | Error de negocio. |
| Finalizar sin estado final válido | Error de negocio. |
| Programar reunión sin fecha | Error de validación. |
| Programar reunión con fecha no futura | Error de negocio. |
| Programar reunión sin sede | Error de validación. |
| Estudiante no habilitado para conciliación | Error de negocio. |
| Conciliador inactivo | Error de negocio. |
