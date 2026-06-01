# Módulo: Catálogos

## Propósito

Permite administrar los parámetros base del sistema: áreas jurídicas, temas y tipos de consulta. Los catálogos forman una jerarquía: Área → Tema → Tipo.

Ver `doc/api/catalogos.md` para la especificación completa del backend.

## Pantalla y ruta

| Ruta | Componente | Descripción |
|---|---|---|
| `/admin` | `AreaForm` + `TemaForm` + `TipoForm` | Gestión de catálogos dentro de la pantalla de administración (tabs) |

## Componentes

```text
src/components/forms/catalogos/AreaForm.jsx
src/components/forms/catalogos/TemaForm.jsx
src/components/forms/catalogos/TipoForm.jsx
```

Los tres formularios se presentan como tabs dentro de la pantalla `/admin`, junto con los tabs de permisos, cambio de rol y auditoría.

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder administración` | Acceder a la pantalla `/admin`. |
| `Gestionar catálogos` | Crear, editar y desactivar áreas, temas y tipos. |
| `Ver catálogos` | Consultar catálogos sin modificar. |

## Endpoints consumidos

### Áreas

```text
GET    /api/areas
POST   /api/areas
PUT    /api/areas/{id}
PATCH  /api/areas/{id}/activo?activo=false
```

### Temas

```text
GET    /api/temas
GET    /api/temas/area/{areaId}
POST   /api/temas
PUT    /api/temas/{id}
PATCH  /api/temas/{id}/activo?activo=false
```

### Tipos

```text
GET    /api/tipos
GET    /api/tipos/tema/{temaId}
POST   /api/tipos
PUT    /api/tipos/{id}
PATCH  /api/tipos/{id}/activo?activo=false
```

## Comportamiento de cada formulario

Los tres formularios comparten el mismo patrón:

1. Cargar la lista existente con paginación.
2. Formulario de creación en la parte superior.
3. Botones "Editar" y "Desactivar" por fila.
4. Al editar, el formulario se rellena con los datos del ítem y cambia el botón a "Actualizar".
5. El diálogo `ConfirmActionDialog` confirma antes de desactivar.

### Manejo de errores de red

`cargarAreas()`, `cargarTemas()` y `cargarTipos()` están envueltos en `try/catch`. Si la petición falla por error de red, se muestra un toast de error sin romper la pantalla.

Del mismo modo, `onSubmit` tiene `try/catch` completo. Los errores de red muestran toast; los errores del backend (400, 409) muestran el mensaje exacto del backend.

### Respuesta a 403

Las acciones sin permiso en los catálogos muestran un toast de error en lugar de redirigir, para que el administrador no pierda el contexto de la pantalla.
