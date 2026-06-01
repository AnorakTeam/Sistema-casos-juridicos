# API - Procesos

## Base URL

Todos los endpoints del módulo se exponen bajo:

`/api/procesos`

El controller responsable es `ProcesoController`.

## Seguridad

Los endpoints usan autorización por permisos con `@PreAuthorize`.

| Permiso | Uso |
|---|---|
| `VER_PROCESOS` | Consulta de procesos. |
| `GESTIONAR_PROCESOS` | Creación, edición, cambios de estado, activación y eliminación lógica. |

## DTO principal

`ProcesoDTO` es el contrato principal:

| Campo | Tipo | Obligatorio | Observación |
|---|---|---|---|
| `id` | Long | No en creación | Identificador del proceso. |
| `numeroRadicado` | String | Condicional | Opcional si el estado es `PENDIENTE`; obligatorio si el estado es final. |
| `departamentoId` | Long | Sí | Departamento del trámite. |
| `consultaId` | Long | Sí | Consulta asociada. |
| `especialidadId` | Long | No | Especialidad del órgano de control. |
| `organoControlId` | Long | No | Órgano de control. Requerido cuando se informa especialidad. |
| `estado` | EstadoProceso | No en creación | Controlado por endpoint de estado. |
| `activo` | Boolean | No en creación | Controlado por endpoint de activo. |

## Estados aceptados

`EstadoProceso`:

- `PENDIENTE`
- `SENTENCIA_FAVORABLE`
- `SENTENCIA_DESFAVORABLE`
- `DESISTIMIENTO`
- `RECHAZO`
- `PRESCRIPCION`

Todo estado diferente de `PENDIENTE` se considera final para efectos de radicado.

## Listar procesos

`GET /api/procesos`

Permisos:

- `VER_PROCESOS`
- `GESTIONAR_PROCESOS`

Respuesta: lista de `ProcesoDTO`.

Uso típico: mostrar procesos activos o consultables según alcance del usuario.

## Obtener proceso por id

`GET /api/procesos/{id}`

Permisos:

- `VER_PROCESOS`
- `GESTIONAR_PROCESOS`

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador del proceso. |

Respuesta: `ProcesoDTO`.

## Crear proceso

`POST /api/procesos`

Permiso:

- `GESTIONAR_PROCESOS`

Cuerpo: `ProcesoDTO`.

Ejemplo de creación pendiente sin radicado:

```json
{
  "numeroRadicado": null,
  "departamentoId": 1,
  "consultaId": 15,
  "organoControlId": 2,
  "especialidadId": 4
}
```

Reglas aplicadas:

1. `id` no debe enviarse.
2. `departamentoId` es obligatorio.
3. `consultaId` es obligatorio.
4. El proceso se crea en estado `PENDIENTE`.
5. `numeroRadicado` puede ser nulo cuando el proceso está pendiente.
6. Si se informa radicado, debe tener 23 caracteres.
7. Si se informa radicado, debe ser único.
8. Si se informa especialidad, debe corresponder al órgano de control.
9. La consulta asociada debe permitir operación operativa.

Respuesta: `201 Created` con `ProcesoDTO`.

## Actualizar proceso

`PUT /api/procesos/{id}`

Permiso:

- `GESTIONAR_PROCESOS`

Cuerpo: `ProcesoDTO`.

Reglas:

1. No se permite cambiar el `id`.
2. No se permite cambiar la consulta asociada.
3. La consulta asociada debe permitir operación operativa.
4. El radicado se valida según el estado actual del proceso.
5. Si se cambia el radicado, se valida unicidad.
6. Si se informa especialidad, debe pertenecer al órgano de control.
7. Deben existir cambios reales para actualizar.

Ejemplo:

```json
{
  "id": 10,
  "numeroRadicado": "12345678901234567890123",
  "departamentoId": 1,
  "consultaId": 15,
  "organoControlId": 2,
  "especialidadId": 4,
  "estado": "PENDIENTE",
  "activo": true
}
```

## Cambiar estado funcional

`PATCH /api/procesos/{id}/estado?estado=SENTENCIA_FAVORABLE`

Permiso:

- `GESTIONAR_PROCESOS`

Parámetros:

| Parámetro | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador del proceso. |
| `estado` | EstadoProceso | Nuevo estado funcional. |

Reglas:

1. El estado es obligatorio.
2. No puede ser igual al estado actual.
3. La consulta asociada debe permitir operación operativa.
4. Si el estado nuevo es final, el proceso debe tener radicado válido.
5. El radicado debe tener exactamente 23 caracteres.

Respuesta: `ProcesoDTO` actualizado.

## Cambiar marca activo

`PATCH /api/procesos/{id}/activo?activo=false`

Permiso:

- `GESTIONAR_PROCESOS`

Reglas:

1. `activo` es obligatorio.
2. No puede ser igual al valor actual.
3. Cambia la marca de eliminación lógica.

Este endpoint no modifica el estado funcional del proceso.

## Eliminar proceso

`DELETE /api/procesos/{id}`

Permiso:

- `GESTIONAR_PROCESOS`

Respuesta: `204 No Content`.

La eliminación se maneja como desactivación lógica para conservar trazabilidad.

## Validaciones de error frecuentes

| Situación | Resultado esperado |
|---|---|
| Crear con `id` | Error de negocio. |
| Crear sin departamento | Error de validación. |
| Crear sin consulta | Error de validación. |
| Finalizar sin radicado | Error de negocio. |
| Radicado con longitud distinta de 23 | Error de negocio. |
| Radicado duplicado | Error de negocio. |
| Especialidad sin órgano de control | Error de negocio. |
| Especialidad que no pertenece al órgano | Error de negocio. |
| Cambiar consulta en edición | Error de negocio. |
| Cambiar estado a mismo estado | Error de negocio. |

## Relación con catálogos de proceso

El módulo se complementa con órganos de control y especialidades. La especialidad depende del órgano de control. La API de procesos espera identificadores existentes y activos para estos catálogos.

## Consideraciones para frontend

El frontend debe tratar `numeroRadicado` como campo opcional mientras el proceso esté pendiente. Cuando el usuario seleccione o intente llevar el proceso a un estado final, debe proveer radicado válido. Aunque el frontend ayude al usuario, la validación definitiva pertenece al backend.
