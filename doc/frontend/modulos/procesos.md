# Frontend - Módulo de procesos

## 1. Propósito del módulo

El módulo de procesos permite registrar, listar, editar, cambiar estado y eliminar lógicamente procesos asociados a consultas jurídicas. En el frontend actual se implementa mediante dos vistas:

| Ruta | Componente de página | Formulario principal |
|---|---|---|
| `/nuevoproceso` | `src/app/(dashboard)/nuevoproceso/page.js` | `NuevoProcesoForm` |
| `/procesos` | `src/app/(dashboard)/procesos/page.js` | `ProcesosForm` |

El módulo consume endpoints del backend bajo `API_URL_BASE` y usa permisos declarados en `lib/permission.js`.

## 2. Archivos fuente validados

```text
frontend/src/app/(dashboard)/nuevoproceso/page.js
frontend/src/app/(dashboard)/procesos/page.js
frontend/src/components/forms/procesos/NuevoProcesosForm.jsx
frontend/src/components/forms/procesos/ProcesosForm.jsx
frontend/src/lib/config.js
frontend/src/lib/authz.js
frontend/src/lib/permission.js
frontend/src/components/ui/ConfirmActionDialog.jsx
```

## 3. Permisos usados

Los formularios de procesos usan funciones locales para evaluar permisos del usuario autenticado. Las validaciones se basan en el objeto devuelto por:

```text
GET /api/auth/me
```

Permisos relevantes:

| Permiso | Uso en frontend |
|---|---|
| `Acceder procesos` | Permite ingresar a las rutas de procesos. |
| `Ver procesos` | Permite cargar el listado. |
| `Gestionar procesos` | Permite crear, editar, cambiar estado y eliminar procesos. |
| `Ver consultas` | Permite cargar consultas para asociar procesos. |
| `Ver catálogos` | Permite cargar departamentos, órganos de control y especialidades. |

Cuando el usuario no tiene permisos suficientes, el formulario redirige o muestra mensajes con `toast.error`.

## 4. Nuevo proceso

La vista `/nuevoproceso` renderiza `NuevoProcesoForm`. Esta vista se usa para registrar un proceso asociado a una consulta jurídica.

### 4.1 Carga inicial

El formulario carga:

```text
GET /api/auth/me
GET /api/departamentos
GET /api/organos-control
GET /api/especialidades
GET /api/consultas
```

La carga de catálogos y consultas depende de los permisos del usuario. Si faltan permisos, el formulario muestra error y evita continuar.

### 4.2 Campos principales

El formulario administra los siguientes datos:

```text
numeroRadicado
departamentoId
consultaId
organoControlId
especialidadId
```

El proceso nuevo se envía al backend mediante:

```text
POST /api/procesos
```

### 4.3 Radicado en creación

El frontend permite que el campo `numeroRadicado` quede vacío durante la creación del proceso. Esta regla está alineada con el backend: un proceso puede existir en estado `PENDIENTE` mientras se obtiene el número de radicado.

Si el usuario informa un radicado, la interfaz valida que tenga exactamente 23 caracteres antes de enviar.

### 4.4 Órgano de control y especialidad

La especialidad depende del órgano de control. Si el usuario selecciona especialidad sin seleccionar órgano de control, el formulario muestra mensaje de error.

## 5. Administración de procesos

La vista `/procesos` renderiza `ProcesosForm`. Esta vista permite consultar y gestionar procesos existentes.

### 5.1 Carga de datos

La vista carga:

```text
GET /api/auth/me
GET /api/procesos
GET /api/departamentos
GET /api/organos-control
GET /api/especialidades
GET /api/consultas
```

Los catálogos auxiliares se cargan según permisos y se usan para presentar nombres legibles en la tabla y en los formularios de edición.

### 5.2 Listado

El listado muestra procesos registrados y acciones según permisos. Cuando el usuario tiene permiso de gestión, se habilitan botones para:

- editar;
- cambiar estado;
- eliminar lógicamente.

También se muestra un botón para ir a `/nuevoproceso` cuando el usuario puede gestionar procesos.

### 5.3 Edición

La edición se realiza con:

```text
PUT /api/procesos/{id}
```

El formulario permite actualizar los datos generales del proceso y conserva la regla de radicado:

- si el proceso está pendiente, el radicado puede quedar vacío;
- si el proceso ya está en estado final, debe conservar un radicado válido;
- si se informa radicado, debe tener 23 caracteres.

### 5.4 Cambio de estado

El cambio de estado se realiza mediante:

```text
PATCH /api/procesos/{id}/estado?estado={estado}
```

Antes de cambiar a un estado final, el frontend valida que el proceso tenga radicado. Si el proceso no tiene radicado, muestra un mensaje indicando que primero debe editarse y guardarse el número de radicado.

### 5.5 Estados finales

El formulario contiene una función para identificar estados finales de proceso. Estos estados representan resultados que no deben registrarse sin radicado:

```text
SENTENCIA_FAVORABLE
SENTENCIA_DESFAVORABLE
DESISTIMIENTO
RECHAZO
PRESCRIPCION
```

La validación visual acompaña la validación del backend.

### 5.6 Eliminación lógica

La eliminación de procesos se realiza mediante:

```text
DELETE /api/procesos/{id}
```

La acción se protege con confirmación y solo se muestra cuando el usuario tiene permiso de gestión.

## 6. Relación con backend

| Regla backend | Reflejo en frontend |
|---|---|
| Proceso nuevo inicia pendiente. | El formulario de creación no fuerza estado final. |
| Radicado opcional mientras está pendiente. | El campo radicado no es obligatorio en creación. |
| Radicado obligatorio para estados finales. | La UI bloquea el cambio a estado final sin radicado. |
| Gestión por permisos. | Acciones visibles según `Gestionar procesos`. |
| Catálogos de proceso se cargan desde backend. | Departamentos, órganos y especialidades se consultan por API. |
| Proceso se asocia a consulta. | Se carga listado de consultas cuando el usuario tiene permiso. |

## 7. Manejo de errores

El módulo implementa funciones auxiliares `apiGet` y `apiEnviar` con manejo de sesión y errores. Cuando backend responde con `401`, se redirige al login. Los errores de validación se muestran con `toast.error`.

Mensajes relevantes de interfaz:

- no tiene permiso para acceder a procesos;
- no tiene permiso para crear procesos;
- no tiene permiso para ver procesos;
- número de radicado debe tener exactamente 23 caracteres;
- antes de finalizar debe registrar un número de radicado;
- seleccione departamento;
- seleccione consulta;
- seleccione primero un órgano de control.

## 8. Componentes relacionados

| Componente | Función |
|---|---|
| `NuevoProcesoForm` | Creación de proceso. |
| `ProcesosForm` | Listado, edición, cambio de estado y eliminación. |
| `ConfirmActionDialog` | Confirmación de eliminación. |

## 9. Consideraciones de mantenimiento

Al modificar el módulo debe verificarse:

1. Que `ESTADOS_FINALES` coincida con el enum del backend.
2. Que la longitud de radicado coincida con la validación backend.
3. Que los permisos evaluados coincidan con `PERMISOS` y con los permisos reales de backend.
4. Que el endpoint de cambio de estado siga usando `PATCH`.
5. Que la eliminación continúe tratándose como operación lógica en backend.
6. Que los catálogos de órganos y especialidades mantengan su relación.
