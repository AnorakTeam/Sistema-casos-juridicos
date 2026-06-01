# API - Perfiles

> Documento validado contra el código fuente actualizado del sistema. La documentación describe únicamente comportamiento implementado en backend.


## 1. Propósito

La API de perfiles permite administrar los perfiles operativos del sistema: administrativos, asesores, monitores, estudiantes y conciliadores. Cada grupo de endpoints opera sobre un tipo de perfil concreto y mantiene separación entre lectura, creación, edición, cambio de estado y eliminación lógica.

---

## 2. Administrativos

Ruta base:

```http
/api/administrativos
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/administrativos` | Lista administrativos | `VER_ADMINISTRADORES`, `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/administrativos/activos` | Lista administrativos activos | `VER_PERFILES_AUXILIARES`, `VER_ADMINISTRADORES`, `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/administrativos/directoras` | Lista administrativos marcados como directora | `VER_ADMINISTRADORES`, `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/administrativos/{id}` | Obtiene un administrativo por id | `VER_ADMINISTRADORES`, `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| POST | `/api/administrativos` | Crea administrativo | `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| PUT | `/api/administrativos/{id}` | Actualiza datos del perfil | `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/administrativos/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/administrativos/{id}/directora?directora=true|false` | Cambia marca de directora | `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |
| DELETE | `/api/administrativos/{id}` | Desactiva lógicamente | `GESTIONAR_ADMINISTRADORES`, `GESTIONAR_USUARIOS` |

### Cuerpo base

```json
{
  "nombre": "Nombre completo",
  "tipoDocumentoId": 1,
  "documento": "1090000000",
  "email": "usuario@correo.com",
  "telefono": "3000000000",
  "usuario": "usuario",
  "codigo": "ADM001",
  "sedeId": 1,
  "directora": false
}
```

---

## 3. Asesores

Ruta base:

```http
/api/asesores
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/asesores` | Lista asesores | `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/asesores/activos` | Lista asesores activos | `VER_PERFILES_AUXILIARES`, `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/asesores/{id}` | Obtiene asesor por id | `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| POST | `/api/asesores` | Crea asesor | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| PUT | `/api/asesores/{id}` | Actualiza asesor | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/asesores/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| DELETE | `/api/asesores/{id}` | Desactiva lógicamente | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |

### Cuerpo base

```json
{
  "nombre": "Nombre completo",
  "tipoDocumentoId": 1,
  "documento": "1090000000",
  "email": "asesor@correo.com",
  "telefono": "3000000000",
  "usuario": "asesor",
  "codigo": "ASE001",
  "sedeId": 1,
  "areaId": 1
}
```

---

## 4. Monitores

Ruta base:

```http
/api/monitores
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/monitores` | Lista monitores | `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/monitores/activos` | Lista monitores activos | `VER_PERFILES_AUXILIARES`, `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/monitores/{id}` | Obtiene monitor | `VER_ASESORES_MONITORES`, `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| POST | `/api/monitores` | Crea monitor | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| PUT | `/api/monitores/{id}` | Actualiza monitor | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/monitores/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |
| DELETE | `/api/monitores/{id}` | Desactiva lógicamente | `GESTIONAR_ASESORES_MONITORES`, `GESTIONAR_USUARIOS` |

---

## 5. Estudiantes

Ruta base:

```http
/api/estudiantes
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/estudiantes` | Lista estudiantes | `VER_ESTUDIANTES`, `VER_PERFILES_AUXILIARES`, `GESTIONAR_USUARIOS` |
| GET | `/api/estudiantes/activos` | Lista estudiantes activos | `VER_ESTUDIANTES`, `VER_PERFILES_AUXILIARES`, `GESTIONAR_USUARIOS` |
| GET | `/api/estudiantes/conciliacion` | Lista estudiantes habilitados para conciliación | `VER_ESTUDIANTES`, `VER_PERFILES_AUXILIARES`, `GESTIONAR_USUARIOS` |
| GET | `/api/estudiantes/activos/asesor/{asesorId}` | Lista estudiantes activos por asesor | `VER_ESTUDIANTES`, `VER_PERFILES_AUXILIARES`, `GESTIONAR_USUARIOS` |
| GET | `/api/estudiantes/{id}` | Obtiene estudiante | `VER_ESTUDIANTES`, `VER_PERFILES_AUXILIARES`, `GESTIONAR_USUARIOS` |
| POST | `/api/estudiantes` | Crea estudiante | `CREAR_USUARIOS`, `GESTIONAR_USUARIOS` |
| PUT | `/api/estudiantes/{id}` | Actualiza estudiante | `EDITAR_USUARIOS`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/estudiantes/{id}/activo?activo=true|false` | Cambia estado activo | `CAMBIAR_ESTADO_ESTUDIANTES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/estudiantes/{id}/conciliacion?conciliacion=true|false` | Habilita o deshabilita conciliación | `EDITAR_USUARIOS`, `GESTIONAR_USUARIOS` |
| DELETE | `/api/estudiantes/{id}` | Desactiva lógicamente | `GESTIONAR_USUARIOS` |
| POST | `/api/estudiantes/importar` | Importa estudiantes desde archivo | permisos de gestión indicados por controller |

### Importación

La importación recibe un `MultipartFile` en el parámetro `archivo` y retorna un resultado con:

- `totalFilas`;
- `exitosos`;
- `fallidos`;
- `errores`.

---

## 6. Conciliadores

Ruta base:

```http
/api/conciliadores
```

| Método | Endpoint | Descripción | Permisos principales |
|---|---|---|---|
| GET | `/api/conciliadores` | Lista conciliadores | `VER_CONCILIADORES`, `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/conciliadores/activos` | Lista conciliadores activos | `VER_PERFILES_AUXILIARES`, `VER_CONCILIADORES`, `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| GET | `/api/conciliadores/{id}` | Obtiene conciliador | `VER_CONCILIADORES`, `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| POST | `/api/conciliadores` | Crea conciliador | `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| PUT | `/api/conciliadores/{id}` | Actualiza conciliador | `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| PATCH | `/api/conciliadores/{id}/activo?activo=true|false` | Cambia estado activo | `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |
| DELETE | `/api/conciliadores/{id}` | Desactiva lógicamente | `GESTIONAR_CONCILIADORES`, `GESTIONAR_USUARIOS` |

---

## 7. Reglas transversales de la API de perfiles

- En creación no se debe enviar `id`.
- En actualización no se permite cambiar el `id` del perfil.
- `PUT` actualiza datos generales del perfil.
- `PATCH /activo` cambia estado lógico.
- `DELETE` realiza desactivación lógica.
- La desactivación de asesores, estudiantes y monitores valida consultas operativas asociadas.
- La activación/desactivación de un perfil sincroniza el estado del `UsuarioSistema` asociado.
- Las respuestas devuelven DTOs del perfil correspondiente.

---

## 8. Relación con UsuarioSistema

Cada perfil operativo se vincula con un usuario del sistema. Esa relación permite que autenticación, rol, permisos y alcance se resuelvan desde una cuenta de acceso única, conservando la identidad funcional en el módulo correspondiente.
