# Navegación y permisos

El frontend controla la visibilidad de menús, botones y acciones según los permisos del usuario autenticado.

La verificación de permisos en el frontend es visual. La autorización real siempre la ejecuta el backend en cada endpoint.

Ver `doc/04-permisos-roles-alcance.md` para la descripción completa de roles, permisos y alcance del sistema.

## Componentes de navegación

```text
src/components/navigation/PermissionSidebar.jsx
src/components/app-sidebar.jsx
src/lib/permission.js
src/lib/authz.js
```

## Cómo se obtienen los permisos

Al montarse, `PermissionSidebar` llama a `GET /api/auth/me` y extrae el array `permisos` del usuario. Los ítems del menú se filtran con las funciones de `src/lib/authz.js`.

```javascript
import { tienePermiso, tieneAlgunPermiso, tieneTodosLosPermisos } from "@/lib/authz";
import { PERMISOS } from "@/lib/permission";

tienePermiso(user, PERMISOS.VER_CONSULTAS)           // verifica un permiso
tieneAlgunPermiso(user, [PERMISOS.A, PERMISOS.B])    // verifica al menos uno
tieneTodosLosPermisos(user, [PERMISOS.A, PERMISOS.B]) // verifica todos
```

Las comparaciones son insensibles a mayúsculas y tildes.

## Permisos de navegación

Controlan qué ítems aparecen en el menú lateral. Se configuran en `PermissionSidebar.jsx`.

| Ítem del menú | Ruta | Permiso requerido | Modo |
|---|---|---|---|
| Inicio | `/inicio` | `Acceder inicio` | any |
| Recepción | `/recepcion` | `Acceder recepción` | all |
| Personas | `/personas` | `Acceder personas` | all |
| Nueva consulta | `/nuevaconsulta` | `Acceder nueva consulta` | all |
| Consultas jurídicas | `/consultasjuridicas` | `Acceder consultas jurídicas` | all |
| Tareas | `/tareas` | `Acceder tareas` | all |
| Nuevo proceso | `/nuevoproceso` | `Acceder procesos` + `Gestionar procesos` | all |
| Procesos | `/procesos` | `Acceder procesos` | all |
| Conciliaciones | `/conciliaciones` | `Acceder conciliaciones` | all |
| Estudiantes | `/estudiantes` | `Acceder estudiantes` | all |
| Asesores y monitores | `/asesoresymonitores` | `Acceder asesores y monitores` | all |
| Roles | `/roles` | `Acceder roles` | all |
| Estadísticas | `/estadisticas` | `Ver reportes` | any |
| Administración | `/admin` | `Acceder administración` | all |
| Eliminación | `/eliminacion` | `Acceder eliminación` | all |

El modo `any` muestra el ítem si el usuario tiene al menos uno de los permisos. El modo `all` requiere todos los permisos listados.

## Permisos de acción

Controlan botones y acciones dentro de cada pantalla. Se verifican en el formulario correspondiente.

### Consultas jurídicas

| Acción | Permiso requerido |
|---|---|
| Crear consulta | `Crear consultas` |
| Editar consulta | `Editar consultas` |
| Cambiar estado | `Cambiar estado consultas` |
| Archivar | `Archivar consultas` |
| Asignar responsables | `Asignar responsables consulta` |

### Seguimientos

| Acción | Permiso requerido |
|---|---|
| Crear seguimiento | `Crear seguimientos` |
| Editar seguimiento | `Editar seguimientos` |
| Eliminar seguimiento | `Eliminar seguimientos` |
| Responder seguimiento | `Responder seguimientos` |
| Aprobar respuesta | `Aprobar respuestas de seguimiento` |
| Ver alertas disciplinarias | `Ver alertas disciplinarias` |

### Conciliaciones

| Acción | Permiso requerido |
|---|---|
| Ver conciliaciones | `Ver conciliaciones` |
| Gestionar conciliaciones | `Gestionar conciliaciones` |
| Programar reunión | `Programar reuniones de conciliación` |
| Reprogramar reunión | `Reprogramar reuniones de conciliación` |
| Concluir conciliación | `Concluir conciliaciones` |

### Usuarios y roles

| Acción | Permiso requerido |
|---|---|
| Ver usuarios | `Ver usuarios` |
| Crear usuarios | `Crear usuarios` |
| Cambiar rol | `Asignar rol usuarios` |
| Ver roles | `Ver roles` |
| Crear y editar roles | `Crear roles` / `Editar roles` |
| Asignar permisos a roles | `Asignar permisos a roles` |

### Personas

| Acción | Permiso requerido |
|---|---|
| Ver personas | `Ver personas` |
| Crear persona | `Crear personas` |
| Editar persona | `Editar personas` |
| Desactivar/reactivar | `Cambiar estado personas` |

### Estadísticas

| Acción | Permiso requerido |
|---|---|
| Ver estadísticas globales | `Ver reportes` |
| Ver estadísticas por perfil | `Ver consultas` |

## Verificación de tipo de perfil

Las funciones de `authz.js` permiten verificar el tipo de perfil del usuario:

```javascript
import { esAdministrativo, esAsesor, esMonitor, esEstudiante, esConciliador } from "@/lib/authz";
```

Esto se usa cuando la lógica depende del rol además de los permisos, por ejemplo en estadísticas y calendario.

## Regla ante 403 inesperado

Aunque un botón no esté visible porque el usuario no tiene el permiso, el backend siempre valida permisos y alcance en cada endpoint. Si el backend devuelve `403` en una acción que el usuario intentó de todas formas, el frontend muestra un toast de error sin redirigir:

```javascript
if (res.status === 403) {
  toast.error("Sin permiso", { description: "No tienes permiso para esta acción." });
  return;
}
```

No se redirige en el 403 para que el usuario no pierda el contexto de la pantalla en la que estaba.
