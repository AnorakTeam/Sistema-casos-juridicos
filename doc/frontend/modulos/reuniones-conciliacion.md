# Frontend - Módulo de reuniones de conciliación

## 1. Propósito del módulo

El módulo de reuniones de conciliación permite programar y reprogramar reuniones asociadas a conciliaciones. Está integrado en la ruta `/conciliaciones`, dentro de una pestaña independiente, y trabaja sobre conciliaciones ya existentes.

El componente principal es:

```text
src/components/forms/conciliacion/ReunionesConciliacionForm.jsx
```

## 2. Ubicación en la interfaz

La ruta de acceso es:

```text
/conciliaciones
```

La página se define en:

```text
src/app/(dashboard)/conciliaciones/page.js
```

y renderiza `ReunionesConciliacionForm` en la pestaña `Reuniones`.

## 3. Archivos relacionados

```text
src/components/forms/conciliacion/ReunionesConciliacionForm.jsx
src/components/forms/conciliacion/ConciliacionesForm.jsx
src/app/(dashboard)/conciliaciones/page.js
src/lib/config.js
src/lib/permission.js
src/lib/authz.js
```

## 4. Permisos aplicados

El componente consulta `/api/auth/me` y verifica permisos antes de permitir el uso de la pantalla.

| Permiso | Uso frontend |
|---|---|
| `Acceder conciliaciones` | Permite ingresar a la sección de conciliaciones. |
| `Ver conciliaciones` | Permite cargar el listado y detalle de conciliaciones. |
| `Programar reuniones de conciliación` | Habilita la programación cuando la conciliación no tiene reunión registrada. |
| `Reprogramar reuniones de conciliación` | Habilita la reprogramación cuando ya existe reunión. |

El componente también evita habilitar programación o reprogramación para estudiantes mediante la validación de perfil en frontend. El backend conserva la autorización definitiva.

## 5. Carga inicial

Durante la inicialización, el componente carga:

```text
GET /api/auth/me
GET /api/conciliaciones
GET /api/sedes
```

Las conciliaciones alimentan la tabla de selección y el detalle. Las sedes se usan para el selector del formulario de reunión.

Si no se cargan sedes activas o el usuario no tiene autorización suficiente, la interfaz muestra mensajes informativos sin romper la pantalla.

## 6. Endpoints consumidos

### 6.1 Listar conciliaciones

```text
GET /api/conciliaciones
```

La lista se muestra con filtros y paginación en cliente. El alcance de registros visibles depende del backend.

### 6.2 Consultar detalle de conciliación

```text
GET /api/conciliaciones/{id}
```

El detalle se usa para determinar si la conciliación tiene reunión existente y para precargar el formulario cuando se reprograma.

### 6.3 Cargar sedes

```text
GET /api/sedes
```

Las sedes activas se usan como opciones del selector de sede de la reunión.

### 6.4 Programar reunión

```text
POST /api/conciliaciones/{id}/reunion
Content-Type: application/json
```

Payload enviado por el frontend:

```json
{
  "fechaReunion": "2026-06-10T14:30:00",
  "sedeId": 1,
  "observaciones": "Primera programación de reunión."
}
```

### 6.5 Reprogramar reunión

```text
PUT /api/conciliaciones/{id}/reunion
Content-Type: application/json
```

El mismo formulario se usa para programar y reprogramar. El método se decide según exista o no una reunión actual asociada al detalle de la conciliación.

## 7. Normalización de fecha

El formulario trabaja con controles HTML de fecha y hora. Antes de enviar la petición al backend, normaliza el valor hacia el formato esperado por el contrato JSON:

```text
fechaReunion
```

La fecha de reunión se muestra al usuario en formato legible usando utilidades de formateo del componente.

## 8. Reglas visuales de programación

La interfaz aplica las siguientes reglas de presentación:

- si la conciliación no tiene reunión, el formulario opera en modo programación;
- si la conciliación ya tiene reunión, el formulario opera en modo reprogramación;
- el botón y los mensajes cambian según el modo;
- el selector de sede es obligatorio;
- la fecha de reunión es obligatoria;
- las observaciones son opcionales;
- después de guardar, se recarga el listado y el detalle.

## 9. Historial y efectos backend

El frontend no construye manualmente el historial de reuniones. Esa responsabilidad corresponde al backend. La interfaz se limita a enviar la programación o reprogramación y a refrescar los datos devueltos por la API.

Según el flujo implementado, el backend registra historial y gestiona notificaciones asociadas a la reunión. La interfaz muestra la información vigente después de recargar el detalle.

## 10. Manejo de errores

El componente maneja:

- errores de sesión;
- errores de permisos;
- errores de carga de conciliaciones;
- errores de carga de sedes;
- errores de validación visual;
- errores devueltos por el backend al programar o reprogramar.

Los mensajes se muestran mediante estado local y `toast` cuando corresponde.

## 11. Relación con el módulo de conciliaciones

La pestaña de reuniones complementa la pestaña principal de conciliaciones. El usuario puede consultar o gestionar la conciliación y luego programar o reprogramar su reunión desde la misma ruta.

## 12. Alcance de la documentación

Este documento describe la implementación frontend real de reuniones de conciliación. La lógica backend de historial, estados y notificaciones se documenta en:

```text
doc/backend/conciliaciones.md
doc/api/conciliaciones.md
doc/reglas/conciliaciones.md
```
