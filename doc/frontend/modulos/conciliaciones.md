# Frontend - Módulo de conciliaciones

## 1. Propósito del módulo

El módulo de conciliaciones permite gestionar, desde la interfaz web, las conciliaciones asociadas a consultas jurídicas. Su objetivo es ofrecer una vista operativa para consultar conciliaciones, crear una conciliación desde una consulta, revisar el detalle, asignar estudiante y conciliador, cambiar estados operativos, cargar documentos PDF y finalizar el trámite cuando existe acta.

La implementación frontend se concentra en la ruta:

```text
/conciliaciones
```

y en el componente:

```text
src/components/forms/conciliacion/ConciliacionesForm.jsx
```

La página de conciliaciones también integra el componente de reuniones de conciliación mediante pestañas, pero la gestión específica de programación y reprogramación se documenta en `doc/frontend/modulos/reuniones-conciliacion.md`.

## 2. Ruta y composición visual

La página se define en:

```text
src/app/(dashboard)/conciliaciones/page.js
```

La ruta presenta dos pestañas principales:

| Pestaña | Componente | Uso |
|---|---|---|
| Conciliaciones | `ConciliacionesForm` | Consulta, creación, asignación, documentos, estados y finalización de conciliaciones. |
| Reuniones | `ReunionesConciliacionForm` | Programación y reprogramación de reuniones de conciliación. |

Esta composición permite mantener en una sola sección funcional todo el flujo conciliatorio, pero separando la administración general de la conciliación y la gestión de reuniones.

## 3. Archivos de código relacionados

```text
src/app/(dashboard)/conciliaciones/page.js
src/components/forms/conciliacion/ConciliacionesForm.jsx
src/components/forms/conciliacion/ReunionesConciliacionForm.jsx
src/lib/config.js
src/lib/permission.js
src/lib/authz.js
```

El componente usa `API_URL_BASE` para consumir endpoints del backend y `FILE_STORAGE_API_URL_BASE` para descargar documentos almacenados por ruta.

## 4. Control de sesión y permisos

Al cargar el módulo, el componente consulta el usuario autenticado mediante:

```text
GET /api/auth/me
```

La pantalla exige que el usuario tenga acceso al módulo de conciliaciones. En código se valida la combinación de permisos:

```text
Acceder conciliaciones
Ver conciliaciones
```

Si la sesión no es válida, el frontend redirige al login. Si el usuario no cuenta con los permisos necesarios para el módulo, redirige a `/inicio`.

## 5. Permisos usados por la pantalla

El componente utiliza permisos declarados en `src/lib/permission.js` y evaluados con helpers de `src/lib/authz.js`.

| Permiso | Uso frontend |
|---|---|
| `Acceder conciliaciones` | Habilita el ingreso a la ruta `/conciliaciones`. |
| `Ver conciliaciones` | Permite cargar y mostrar el listado y detalle de conciliaciones. |
| `Gestionar conciliaciones` | Permite crear conciliación, asignar estudiante, asignar conciliador, reemplazar solicitud y desactivar. |
| `Concluir conciliaciones` | Permite acciones de cierre o conclusión del trámite, incluyendo finalización con acta cuando el backend lo autoriza. |
| `Ver consultas` | Permite cargar consultas para crear conciliaciones asociadas. |
| `Ver estudiantes` / `Ver perfiles auxiliares` | Permite cargar estudiantes habilitados para conciliación. |
| `Ver conciliadores` / `Ver perfiles auxiliares` | Permite cargar conciliadores activos. |

El frontend no reemplaza las validaciones del backend. La interfaz muestra u oculta acciones según permisos, pero el backend conserva la decisión final de autorización, alcance y estado.

## 6. Estados usados por la interfaz

La interfaz trabaja con estados de conciliación entregados por el backend. En el componente se separan estados operativos y finales para controlar el cambio de estado normal y la finalización con acta.

Estados operativos documentados por el flujo:

```text
EN_ESPERA
ESPERANDO_REUNION
REUNION_PROGRAMADA
```

Estados finales usados en finalización:

```text
COMPLETO_CONCILIADO
COMPLETO_NO_CONCILIADO
```

La acción de finalización no se trata como un simple cambio de estado, porque requiere cargar acta en PDF y enviar un formulario multipart.

## 7. Carga inicial de datos

Durante la inicialización, el componente carga:

```text
GET /api/conciliaciones
GET /api/consultas
GET /api/estudiantes/activos/conciliacion
GET /api/conciliadores/activos
```

La carga de consultas, estudiantes y conciliadores depende de permisos. Si el usuario no tiene permiso para ver alguno de esos recursos, la pantalla puede seguir mostrando conciliaciones, pero sin habilitar las acciones que dependen de esos catálogos operativos.

El listado de conciliaciones se pagina y filtra en el cliente para facilitar la consulta visual.

## 8. Endpoints consumidos por `ConciliacionesForm`

### 8.1 Consultar conciliaciones

```text
GET /api/conciliaciones
```

Carga el listado visible para el usuario autenticado. El alcance del listado corresponde al backend.

### 8.2 Consultar detalle de conciliación

```text
GET /api/conciliaciones/{id}
```

Permite cargar el detalle de una conciliación seleccionada. El detalle se usa para mostrar documentos, responsables, estado y reunión asociada cuando existe.

### 8.3 Crear conciliación desde consulta

```text
POST /api/conciliaciones/consulta/{consultaId}
Content-Type: multipart/form-data
```

El frontend envía un `FormData` con el campo:

```text
solicitud
```

El archivo de solicitud es obligatorio en la interfaz y debe ser PDF. La validación visual evita enviar extensiones distintas a `.pdf`.

### 8.4 Asignar estudiante

```text
PATCH /api/conciliaciones/{id}/estudiante?estudianteId={estudianteId}
```

El estudiante se selecciona desde la lista de estudiantes activos habilitados para conciliación. La pantalla valida que exista selección antes de enviar la petición.

### 8.5 Asignar conciliador

```text
PATCH /api/conciliaciones/{id}/conciliador?conciliadorId={conciliadorId}
```

El conciliador se selecciona desde la lista de conciliadores activos. La pantalla valida la selección antes del envío.

### 8.6 Cambiar estado operativo

```text
PATCH /api/conciliaciones/{id}/estado?estado={codigoEstado}
```

Esta acción se usa para estados no finales. La finalización formal se hace con un endpoint independiente porque requiere acta.

### 8.7 Finalizar conciliación con acta

```text
POST /api/conciliaciones/{id}/finalizar
Content-Type: multipart/form-data
```

El frontend envía un `FormData` con:

```text
estado
acta
```

El campo `acta` debe ser un PDF. El uso de `POST` corresponde al código actual del frontend y al contrato backend vigente.

### 8.8 Reemplazar solicitud PDF

```text
POST /api/conciliaciones/{id}/solicitud
Content-Type: multipart/form-data
```

Permite reemplazar el documento de solicitud de conciliación. La interfaz valida que el archivo seleccionado sea PDF.

### 8.9 Desactivar conciliación

```text
DELETE /api/conciliaciones/{id}
```

La pantalla pide confirmación antes de desactivar la conciliación. La desactivación se presenta como una operación distinta de la finalización, de modo que el usuario visualiza que no equivale a concluir el trámite con acta.

### 8.10 Descargar documentos

```text
GET /files/download/{path}
```

La descarga se realiza usando `FILE_STORAGE_API_URL_BASE`. El path se codifica para evitar problemas con caracteres especiales en rutas.

## 9. Validación de archivos PDF

El componente valida visualmente que los documentos cargados para solicitud o acta sean PDF. Esta validación se aplica antes de construir el `FormData`.

Validaciones aplicadas por la interfaz:

- la solicitud de creación debe existir y ser PDF;
- el acta de finalización debe existir y ser PDF;
- el reemplazo de solicitud debe ser PDF;
- se limpian los inputs después de una operación exitosa.

La validación frontend mejora la experiencia de usuario, mientras que la validación definitiva corresponde al backend.

## 10. Gestión de mensajes y errores

El componente usa estados internos de error y mensaje para informar el resultado de las operaciones. Además, captura errores de red, errores HTTP y mensajes del backend.

El patrón general de ejecución es:

```text
1. Limpiar mensajes anteriores.
2. Activar estado de guardado.
3. Ejecutar petición al backend.
4. Refrescar listado y detalle si aplica.
5. Mostrar mensaje de éxito o error.
6. Finalizar estado de guardado.
```

Las operaciones críticas como desactivar conciliación usan confirmación del usuario.

## 11. Relación con reuniones de conciliación

`ConciliacionesForm` carga y muestra información general de la conciliación, incluyendo datos de reunión cuando el backend los entrega en el detalle. La programación y reprogramación se gestionan en `ReunionesConciliacionForm`, que consume los endpoints de reunión.

La separación por pestañas permite que el usuario trabaje con conciliaciones y reuniones dentro de la misma ruta sin mezclar responsabilidades en un solo formulario.

## 12. Alcance de la documentación

Este documento describe la implementación frontend real del módulo de conciliaciones. La definición completa de reglas de negocio, estados, permisos backend y efectos sobre notificaciones se desarrolla en:

```text
doc/backend/conciliaciones.md
doc/api/conciliaciones.md
doc/reglas/conciliaciones.md
```
