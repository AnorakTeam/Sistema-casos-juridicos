# API - Usuarios del sistema, roles, permisos y cambio de perfil

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Propósito

Esta sección documenta los endpoints relacionados con cuentas de acceso, roles, permisos y cambio de perfil. El sistema separa las operaciones de seguridad en tres grupos:

- usuarios del sistema;
- roles;
- permisos.

Además, implementa endpoints específicos para cambiar el perfil activo de un usuario y actualizar su rol asociado.

---

## 2. Usuarios del sistema

Ruta base:

```http
/api/usuarios-sistema
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/usuarios-sistema` | Lista usuarios del sistema | `VER_USUARIOS`, `GESTIONAR_USUARIOS` |
| GET | `/api/usuarios-sistema/activos` | Lista usuarios activos | `VER_USUARIOS`, `GESTIONAR_USUARIOS` |
| GET | `/api/usuarios-sistema/{id}` | Obtiene usuario por id | `VER_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/activo?activo=true|false` | Cambia estado activo | `CAMBIAR_ESTADO_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/perfil/administrativo` | Cambia perfil a administrativo | `ASIGNAR_ROL_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/perfil/estudiante` | Cambia perfil a estudiante | `ASIGNAR_ROL_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/perfil/asesor` | Cambia perfil a asesor | `ASIGNAR_ROL_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/perfil/monitor` | Cambia perfil a monitor | `ASIGNAR_ROL_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/usuarios-sistema/{id}/perfil/conciliador` | Cambia perfil a conciliador | `ASIGNAR_ROL_USUARIOS`, `GESTIONAR_USUARIOS` |

### UsuarioSistemaDTO

```json
{
  "id": 1,
  "username": "usuario",
  "activo": true,
  "rolId": 2,
  "rolNombre": "ASESOR",
  "perfilId": 10,
  "tipoPerfil": "ASESOR",
  "permisos": ["VER_CONSULTAS", "EDITAR_CONSULTAS"]
}
```

---

## 3. Cambio de perfil

Los endpoints de cambio de perfil reciben un DTO específico según el perfil destino. Todos usan `PATCH` y son gestionados por `UsuarioCambioPerfilService`.

El flujo implementado es:

1. validar usuario destino;
2. validar que el usuario esté activo;
3. validar que el rol actual esté activo;
4. validar que el perfil destino sea diferente al actual;
5. validar que el rol destino corresponda al tipo de perfil destino;
6. normalizar datos básicos;
7. crear o reactivar el perfil destino mediante Strategy;
8. desactivar el perfil anterior mediante Strategy;
9. actualizar rol y tipo de perfil actual del usuario;
10. registrar historial del cambio.

### Campos transversales del cambio

Los DTOs de cambio de perfil extienden `CambiarPerfilBaseDTO` y comparten datos básicos como:

- nombre;
- tipo de documento;
- documento;
- correo;
- teléfono;
- usuario;
- código;
- sede;
- rol destino;
- motivo del cambio.

Los perfiles especializados agregan campos propios, por ejemplo:

- asesor: `areaId`;
- estudiante: `asesorId`;
- conciliador: `tipoConciliador`;
- administrativo: `directora` cuando aplique.

---

## 4. Roles

Ruta base:

```http
/api/roles
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/roles` | Lista roles | `GESTIONAR_ROLES`, `VER_ROLES` |
| GET | `/api/roles/activos` | Lista roles activos | `GESTIONAR_ROLES`, `VER_ROLES` |
| GET | `/api/roles/{id}` | Obtiene rol | `GESTIONAR_ROLES`, `VER_ROLES` |
| POST | `/api/roles` | Crea rol | `GESTIONAR_ROLES`, `CREAR_ROLES` |
| PUT | `/api/roles/{id}` | Actualiza rol | `GESTIONAR_ROLES`, `EDITAR_ROLES` |
| PATCH | `/api/roles/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_ROLES`, `EDITAR_ROLES` |
| PATCH | `/api/roles/{rolId}/permisos/{permisoId}` | Asigna permiso al rol | `GESTIONAR_ROLES`, `ASIGNAR_PERMISOS_A_ROLES` |
| DELETE | `/api/roles/{rolId}/permisos/{permisoId}` | Quita permiso del rol | `GESTIONAR_ROLES`, `ASIGNAR_PERMISOS_A_ROLES` |

### RolDTO

```json
{
  "id": 1,
  "nombre": "ASESOR",
  "descripcion": "Rol de asesor jurídico",
  "activo": true,
  "permisoIds": [1, 2, 3],
  "permisos": [
    { "id": 1, "nombre": "VER_CONSULTAS", "descripcion": "...", "activo": true }
  ]
}
```

---

## 5. Permisos

Ruta base:

```http
/api/permisos
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/permisos` | Lista permisos | `GESTIONAR_PERMISOS`, `ASIGNAR_PERMISOS_A_ROLES` |
| GET | `/api/permisos/activos` | Lista permisos activos | `GESTIONAR_PERMISOS`, `ASIGNAR_PERMISOS_A_ROLES` |
| GET | `/api/permisos/{id}` | Obtiene permiso | `GESTIONAR_PERMISOS`, `ASIGNAR_PERMISOS_A_ROLES` |
| POST | `/api/permisos` | Crea permiso | `GESTIONAR_PERMISOS` |
| PUT | `/api/permisos/{id}` | Actualiza permiso | `GESTIONAR_PERMISOS` |
| PATCH | `/api/permisos/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_PERMISOS` |

### PermisoDTO

```json
{
  "id": 1,
  "nombre": "VER_CONSULTAS",
  "descripcion": "Permite consultar casos jurídicos",
  "activo": true
}
```

---

## 6. Reglas implementadas

- Los roles y permisos tienen estado lógico `activo`.
- La asignación de permisos a roles se realiza por endpoint explícito.
- El cambio de perfil requiere rol destino coherente con el tipo de perfil destino.
- El cambio de perfil registra historial con motivo obligatorio.
- El perfil anterior se desactiva usando Strategy.
- El perfil activo se resuelve usando Strategy.
- La cuenta de usuario conserva su identidad de acceso y actualiza el perfil operativo vigente.
