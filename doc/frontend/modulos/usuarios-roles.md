# Módulo: Usuarios y roles

## Propósito

Permite crear usuarios del sistema, cambiar roles, gestionar permisos por rol, consultar logs de auditoría, y administrar estudiantes, asesores, monitores y conciliadores.

Ver `doc/api/usuarios-roles-permisos.md` y `doc/api/perfiles.md` para la especificación completa del backend.

## Pantallas y rutas

| Ruta | Componente | Descripción |
|---|---|---|
| `/roles` | `UsuarioSistemaForm` | Creación de usuarios del sistema |
| `/admin` | `RolePermissionsForm` + `CambiarRolUsuarioForm` + `AuditLogsTable` | Gestión de permisos, roles y auditoría (tabs) |
| `/estudiantes` | `EstudiantesForm` + `ImportarEstudiantesForm` | Gestión de estudiantes con cargue masivo |
| `/asesoresymonitores` | `AsesoresYMonitoresForm` | Gestión de asesores y monitores |

## Componentes

```text
src/components/forms/AdminUsuarios/UsuarioSistemaForm.jsx
src/components/forms/AdminUsuarios/RolePermissionsForm.jsx
src/components/forms/AdminUsuarios/CambiarRolUsuarioForm.jsx
src/components/forms/AdminUsuarios/AuditLogsTable.jsx
src/components/forms/usuarios/EstudiantesForm.jsx
src/components/forms/usuarios/ImportarEstudiantesForm.jsx
src/components/forms/usuarios/AsesoresYMonitoresForm.jsx
src/components/forms/usuarios/ConciliadorForm.jsx
```

## Permisos

| Permiso | Uso |
|---|---|
| `Acceder roles` | Acceder a `/roles`. |
| `Ver usuarios` | Consultar lista de usuarios. |
| `Crear usuarios` | Crear nuevos usuarios del sistema. |
| `Editar usuarios` | Editar datos de usuarios. |
| `Cambiar estado usuarios` | Desactivar/reactivar usuarios. |
| `Asignar rol usuarios` | Cambiar el rol de un usuario. |
| `Ver roles` | Consultar roles existentes. |
| `Crear roles` / `Editar roles` | Crear o editar roles. |
| `Asignar permisos a roles` | Gestionar los permisos de un rol. |
| `Acceder estudiantes` | Acceder a `/estudiantes`. |
| `Ver estudiantes` | Listar estudiantes. |
| `Cambiar estado estudiantes` | Desactivar estudiantes. |
| `Acceder asesores y monitores` | Acceder a `/asesoresymonitores`. |
| `Ver asesores y monitores` | Listar asesores y monitores. |

## Endpoints consumidos

### Usuarios

```text
GET   /api/{rol}               ← donde {rol} es el tipo: asesores, monitores, etc.
POST  /api/{rol}               ← creación del usuario según tipo
```

### Importación masiva de estudiantes

```text
POST /api/estudiantes/importar   multipart/form-data (campo: archivo)
```

Devuelve un resumen `{ exitosos, fallidos, totalFilas, errores[] }`. Los errores de formato devuelven 400 con texto plano.

### Roles y permisos

```text
GET  /api/roles/activos
GET  /api/permisos/activos
GET  /api/permisos
POST /api/permisos                       ← crear permiso nuevo si no existe
GET  /api/roles/{id}                     ← permisos actuales del rol
POST /api/roles/{rolId}/permisos/{permisoId}    ← agregar permiso
DELETE /api/roles/{rolId}/permisos/{permisoId}  ← quitar permiso
```

### Cambio de rol y perfil (CambiarRolUsuarioForm)

`CambiarRolUsuarioForm` es un flujo complejo que permite cambiar el rol y el tipo de perfil de un usuario existente. Carga usuarios, roles, tipos de documento, sedes y asesores para construir el formulario.

```text
GET  /api/usuarios-sistema/activos          ← lista usuarios activos
GET  /api/roles/activos
GET  /api/tipos-documento/activos
GET  /api/sedes
GET  /api/asesores/activos
GET  /api/areas
GET  /api/{perfilActual.endpointActual}/{perfilId}   ← obtiene el perfil actual del usuario
PUT  /api/usuarios-sistema/{id}/perfil/{perfilDestino.endpoint}  ← cambia rol y perfil
```

El endpoint de cambio de perfil varía según el tipo de perfil de destino (asesor, estudiante, monitor, administrativo).

### Auditoría

```text
GET /api/audit
GET /api/audit?page=0&size=20&...  ← soporta filtros por fecha, usuario, acción
```

### Estudiantes

```text
GET   /api/estudiantes
PATCH /api/estudiantes/{id}/activo?activo=false
```

### Conciliadores (ConciliadorForm)

`ConciliadorForm` registra conciliadores usando `useApiForm` con el endpoint de personas, ya que los conciliadores son personas del sistema con un rol específico.

```text
POST /api/personas    ← crea la persona base del conciliador
```

### Asesores y monitores

```text
GET /api/asesores
GET /api/monitores
```

## Creación de usuarios por tipo

`UsuarioSistemaForm` adapta los campos mostrados según el tipo de perfil seleccionado:

| Tipo | Campos adicionales |
|---|---|
| Estudiante | Código estudiantil, asesor asignado. Modo "Cargue masivo" disponible como tab. |
| Asesor / Monitor | Campos de perfil de asesor o monitor. |
| Conciliador | Campos específicos de conciliador. |
| Administrativo | Sin campos adicionales de perfil. |

## Cargue masivo de estudiantes

Cuando se selecciona el tipo "Estudiante", el formulario muestra dos tabs:

- **Crear uno**: formulario individual estándar.
- **Cargue masivo**: `ImportarEstudiantesForm` con drag & drop de archivo `.xlsx`.

El botón "Descargar plantilla" genera un CSV con encabezados y fila de ejemplo directamente en el navegador, sin llamar al backend.

### Formato del archivo Excel

Los encabezados deben ser exactamente en este orden:

```text
nombre | tipoDocumentoId | documento | email | telefono | usuario | sedeId | codigo | asesorId | activo | conciliacion
```

Los campos numéricos (`documento`, `telefono`, `codigo`) deben estar en formato número entero, no notación científica.

## Gestión de permisos de roles (RolePermissionsForm)

Ver `doc/frontend/formularios-validaciones.md` para la descripción del algoritmo de diff.

### Protección de acceso propio

El formulario no permite quitarle al propio rol del usuario el acceso a la página Administración. Si se intenta, muestra un mensaje de error en la UI.

### Permisos compartidos entre páginas

Si dos páginas comparten un permiso (ej: `Ver catálogos` en Recepción y Nueva Consulta), al desmarcar una página el permiso no se quita si la otra página sigue marcada.
