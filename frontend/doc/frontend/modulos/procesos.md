# Módulo: Procesos judiciales

## Propósito

Permite crear y gestionar procesos judiciales asociados a consultas jurídicas. Incluye radicación, estado, órgano de control y especialidad.

Ver `doc/api/procesos.md` para la especificación completa del backend.

## Pantallas y rutas

| Ruta | Componente principal | Descripción |
|---|---|---|
| `/nuevoproceso` | `NuevoProcesosForm` | Formulario de registro de proceso nuevo |
| `/procesos` | `ProcesosForm` | Listado y gestión de procesos |

## Componentes

```text
src/components/forms/procesos/NuevoProcesosForm.jsx
src/components/forms/procesos/ProcesosForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder procesos` | Mostrar el ítem en el menú lateral. |
| `Ver procesos` | Cargar el listado de procesos. |
| `Gestionar procesos` | Crear, editar, cambiar estado y eliminar procesos. |

El ítem "Nuevo proceso" en el menú requiere ambos: `Acceder procesos` y `Gestionar procesos`.

## Endpoints consumidos

### Listado de procesos

```text
GET /api/procesos
```

### Crear proceso

```text
POST /api/procesos
```

### Editar proceso

```text
PUT /api/procesos/{id}
```

### Cambiar estado

```text
PATCH /api/procesos/{id}/estado?estado=CODIGO
```

### Eliminar (desactivar)

```text
DELETE /api/procesos/{id}
```

### Catálogos para el formulario

```text
GET /api/departamentos
GET /api/organos-control
GET /api/especialidades
GET /api/consultas            ← para asociar el proceso a una consulta
```

Los catálogos de departamentos, órganos de control y especialidades solo se cargan si el usuario tiene los permisos correspondientes para verlos.
