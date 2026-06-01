# Frontend - Módulo de usuarios, roles, permisos y perfiles

## 1. Propósito del módulo

Este módulo reúne las pantallas frontend relacionadas con creación de usuarios, administración de roles y permisos, cambio de perfil, auditoría administrativa y gestión visual de perfiles internos del consultorio jurídico.

El frontend separa estas funcionalidades en varias rutas y componentes:

```text
/roles
/admin
/estudiantes
/asesoresymonitores
```

## 2. Rutas y componentes

| Ruta | Componente principal | Propósito |
|---|---|---|
| `/roles` | `UsuarioSistemaForm` | Creación de usuarios del sistema y perfiles asociados. |
| `/admin` | `RolePermissionsForm`, `CambiarRolUsuarioForm`, `AuditLogsTable`, catálogos | Administración de permisos, cambio de rol/perfil y auditoría. |
| `/estudiantes` | `EstudiantesForm` | Consulta y cambio de estado de estudiantes visibles por alcance. |
| `/asesoresymonitores` | `AsesoresYMonitoresForm` | Consulta y cambio de estado de asesores y monitores. |

## 3. Archivos relacionados

```text
src/components/forms/AdminUsuarios/UsuarioSistemaForm.jsx
src/components/forms/AdminUsuarios/RolePermissionsForm.jsx
src/components/forms/AdminUsuarios/CambiarRolUsuarioForm.jsx
src/components/forms/AdminUsuarios/AuditLogsTable.jsx
src/components/forms/usuarios/EstudiantesForm.jsx
src/components/forms/usuarios/ImportarEstudiantesForm.jsx
src/components/forms/usuarios/AsesoresYMonitoresForm.jsx
src/components/forms/usuarios/ConciliadorForm.jsx
src/app/(dashboard)/roles/page.js
src/app/(dashboard)/admin/page.js
src/app/(dashboard)/estudiantes/page.js
src/app/(dashboard)/asesoresymonitores/page.js
src/lib/permission.js
src/lib/authz.js
```

## 4. Permisos usados

| Permiso | Uso frontend |
|---|---|
| `Acceder roles` | Permite entrar a `/roles`. |
| `Crear usuarios` | Permite crear usuarios/perfiles desde `UsuarioSistemaForm`. |
| `Ver usuarios` | Permite consultar usuarios en componentes administrativos. |
| `Asignar rol usuarios` | Permite cambiar rol o perfil en `CambiarRolUsuarioForm`. |
| `Ver roles` | Permite listar roles. |
| `Asignar permisos a roles` | Permite guardar cambios de permisos por rol. |
| `Acceder administración` | Permite entrar a `/admin`. |
| `Acceder estudiantes` | Permite entrar a `/estudiantes`. |
| `Ver estudiantes` | Permite cargar estudiantes. |
| `Cambiar estado estudiantes` | Permite desactivar estudiantes desde la vista. |
| `Acceder asesores y monitores` | Permite entrar a `/asesoresymonitores`. |
| `Ver asesores y monitores` | Permite listar asesores y monitores. |
| `Gestionar asesores y monitores` | Permite desactivar asesores o monitores. |
| `Gestionar administradores` | Habilita acciones reservadas para gestión de administrativos. |

## 5. Creación de usuarios del sistema

La ruta `/roles` renderiza `UsuarioSistemaForm`. Este formulario permite crear perfiles de tipo:

```text
administrativo
asesor
estudiante
monitor
conciliador
```

El formulario carga datos auxiliares según el tipo de perfil:

```text
GET /api/tipos-documento/activos
GET /api/sedes
GET /api/areas
GET /api/asesores/activos
```

El endpoint de creación varía según el tipo seleccionado:

```text
POST /api/administrativo
POST /api/asesor
POST /api/estudiante
POST /api/monitor
POST /api/conciliador
```

El componente usa un solo formulario base y muestra campos adicionales según el perfil seleccionado.

## 6. Importación masiva de estudiantes

Cuando el tipo seleccionado es estudiante, el formulario permite alternar entre creación individual y cargue masivo mediante `ImportarEstudiantesForm`.

Endpoint consumido:

```text
POST /api/estudiantes/importar
Content-Type: multipart/form-data
```

El campo enviado es:

```text
archivo
```

El componente valida que el archivo sea Excel (`.xlsx` o `.xls`) y muestra resultado de importación con conteos de exitosos, fallidos y errores por fila cuando el backend los entrega.

## 7. Gestión de roles y permisos

`RolePermissionsForm` se encuentra en `/admin`, dentro de la pestaña `Permisos`.

Endpoints usados:

```text
GET /api/roles/activos
GET /api/permisos/activos
GET /api/permisos
POST /api/permisos
GET /api/roles/{id}
PATCH /api/roles/{rolId}/permisos/{permisoId}
DELETE /api/roles/{rolId}/permisos/{permisoId}
```

El formulario organiza permisos por páginas del sistema. Al marcar una página, calcula los permisos necesarios para navegación y operación. También conserva permisos no gestionados por el formulario para no sobrescribir asignaciones externas.

## 8. Cambio de rol y perfil

`CambiarRolUsuarioForm` permite seleccionar un usuario, revisar su perfil actual y cambiarlo a otro tipo de perfil.

Carga inicial:

```text
GET /api/usuarios-sistema/activos
GET /api/roles/activos
GET /api/tipos-documento/activos
GET /api/sedes
GET /api/asesores/activos
GET /api/areas
```

Para consultar datos actuales del perfil, usa endpoints de perfiles según el tipo:

```text
GET /api/administrativos/{perfilId}
GET /api/asesores/{perfilId}
GET /api/estudiantes/{perfilId}
GET /api/monitores/{perfilId}
GET /api/conciliadores/{perfilId}
```

Para cambiar de perfil, usa:

```text
PATCH /api/usuarios-sistema/{usuarioId}/perfil/{perfilDestino}
```

El perfil destino corresponde a:

```text
administrativo
asesor
estudiante
monitor
conciliador
```

La lógica de cambio de perfil se valida en backend. El frontend reúne la información necesaria y presenta el formulario correspondiente.

## 9. Auditoría administrativa

`AuditLogsTable` se muestra en `/admin`, pestaña `Auditoría`.

Endpoint usado:

```text
GET /api/audit?page={page}&size={size}&username={username}&sortBy={sortBy}&sortDir={sortDir}
```

La tabla consume la paginación del backend con índice base cero. Desde la interfaz se maneja página visual base uno y se envía `page - 1` al backend. Permite búsqueda por `username`, ordenamiento por `timestamp` o `username`, y visualización de detalles en diálogo modal.

## 10. Vista de estudiantes

`EstudiantesForm` se muestra en la ruta:

```text
/estudiantes
```

Carga usuario actual con `/api/auth/me` y luego consulta estudiantes según perfil:

```text
GET /api/estudiantes/activos
GET /api/estudiantes/activos/asesor/{perfilId}
```

El segundo endpoint se usa cuando el usuario autenticado es asesor y debe ver estudiantes asociados a su alcance.

La desactivación usa:

```text
PATCH /api/estudiantes/{id}/activo?activo=false
```

## 11. Vista de asesores y monitores

`AsesoresYMonitoresForm` se muestra en:

```text
/asesoresymonitores
```

Carga:

```text
GET /api/asesores/activos
GET /api/monitores/activos
```

La desactivación usa el endpoint correspondiente al tipo:

```text
PATCH /api/asesores/{id}/activo?activo=false
PATCH /api/monitores/{id}/activo?activo=false
```

## 12. Relación con backend

El frontend aplica controles visuales de permisos y formularios, pero las reglas centrales se aplican en backend. Entre ellas:

- creación de usuario asociado al perfil;
- sincronización entre perfil y usuario del sistema;
- cambio de perfil mediante Strategy;
- bloqueo de desactivación de responsables con consultas operativas;
- validación de roles y permisos.

## 13. Alcance de la documentación

Este documento describe la implementación frontend actual de usuarios, roles, permisos y perfiles. La especificación backend se documenta en:

```text
doc/backend/perfiles.md
doc/api/perfiles.md
doc/api/usuarios-roles-permisos.md
doc/reglas/permisos.md
```
