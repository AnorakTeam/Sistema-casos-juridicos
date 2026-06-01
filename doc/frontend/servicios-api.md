# Servicios de API

El frontend consume el backend mediante `fetch` nativo de la Fetch API, centralizado en utilidades de `src/lib/` y un hook compartido para formularios.

## Cliente HTTP centralizado

`src/lib/apiClient.js` es el cliente HTTP principal. Incluye `credentials: "include"` y `Content-Type: application/json` automáticamente.

```javascript
import { apiClient } from "@/lib/apiClient";

// GET autenticado
const res = await apiClient.get("/auth/me");
const user = await res.json();

// POST con JSON
const res = await apiClient.post("/personas", payload);

// PUT
const res = await apiClient.put(`/areas/${id}`, data);

// PATCH
const res = await apiClient.patch(`/personas/${id}/desactivar`);

// DELETE
const res = await apiClient.delete(`/areas/${id}`);
```

El método `request` base también acepta un campo `json` que serializa automáticamente:

```javascript
const res = await apiClient.request("/areas", { method: "POST", json: data });
```

## Hook useApiForm

`src/hooks/useApiForm.js` centraliza la lógica de envío de formularios: estado de carga, toasts de éxito y error, y redirección ante 401.

```javascript
import { useApiForm } from "@/hooks/useApiForm";

const { submit, isSubmitting } = useApiForm({
  endpoint: `${API_URL_BASE}/personas`,
  method: "POST",
  successMessage: "Persona creada correctamente",
});

const handleGuardar = async (data) => {
  const result = await submit(data);
  if (result.success) {
    // limpiar o redirigir
  }
};
```

El hook:
- Deshabilita el botón mientras `isSubmitting` es `true`.
- Muestra `toast.success` si la respuesta es exitosa.
- Muestra `toast.error` con el mensaje del backend si falla.
- Redirige a `/` si recibe `401`.
- Devuelve `{ success: boolean, data?, error? }`.

## Helpers de respuesta

`src/lib/api.js` provee funciones para leer y normalizar respuestas:

### `readResponseBody(response)`

Lee el cuerpo de la respuesta como JSON o string. Devuelve `null` si está vacío o es 204.

```javascript
import { readResponseBody } from "@/lib/api";
const payload = await readResponseBody(response);
```

### `getApiErrorTitle(payload, fallback)`

Extrae el título del error del payload (`mensaje`, `message`, `descripcion`, `error`).

```javascript
import { getApiErrorTitle } from "@/lib/api";
const titulo = getApiErrorTitle(payload, "Error al guardar");
```

### `getApiErrorDescription(payload, fallback)`

Extrae los mensajes de detalle del error (`detalles`, `details`, `errors`, `fieldErrors`). Si no hay detalles, usa el título.

```javascript
import { getApiErrorDescription } from "@/lib/api";
const descripcion = getApiErrorDescription(payload);
```

### Patrón completo de manejo de error

```javascript
const res = await fetch(`${API_URL_BASE}/personas`, {
  method: "POST",
  credentials: "include",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(payload),
});

const data = await readResponseBody(res);

if (!res.ok) {
  toast.error(getApiErrorTitle(data, "Error al guardar"), {
    description: getApiErrorDescription(data),
  });
  return;
}
```

## Patrones de consumo por tipo de endpoint

### Listados

```javascript
const res = await fetch(`${API_URL_BASE}/areas`, { credentials: "include" });
if (res.status === 401) { router.replace("/"); return; }
if (!res.ok) { toast.error("Error al cargar"); return; }
const data = await res.json();
setAreas(Array.isArray(data) ? data : []);
```

### Peticiones con multipart/form-data (archivos)

```javascript
const formData = new FormData();
archivos.forEach((file) => formData.append("files", file));
formData.append("path", String(consultaId));

const res = await fetch(`${FILE_STORAGE_API_URL_BASE}/files/upload-multiple`, {
  method: "POST",
  credentials: "include",
  body: formData,
  // No incluir Content-Type: el navegador lo gestiona con el boundary
});
```

### Acciones sin cuerpo (PATCH de estado)

```javascript
const res = await fetch(`${API_URL_BASE}/areas/${id}/activo?activo=false`, {
  method: "PATCH",
  credentials: "include",
});
```

## Endpoints principales por módulo

| Módulo | Base path |
|---|---|
| Autenticación | `/api/auth` |
| Personas | `/api/personas` |
| Catálogos (áreas, temas, tipos) | `/api/areas`, `/api/temas`, `/api/tipos` |
| Catálogos (sedes, departamentos…) | `/api/sedes`, `/api/departamentos`, etc. |
| Consultas jurídicas | `/api/consultas` |
| Seguimientos | `/api/seguimientos` |
| Procesos | `/api/procesos` |
| Conciliaciones | `/api/conciliaciones` |
| Estadísticas | `/api/estadisticas` |
| Usuarios y roles | `/api/usuarios`, `/api/roles`, `/api/permisos` |
| Perfiles (asesores, monitores…) | `/api/asesores`, `/api/monitores`, `/api/estudiantes` |
| Archivos | Ver `FILE_STORAGE_API_URL_BASE` |
| Auditoría | `/api/auditoria` |

Para la descripción completa de cada endpoint, ver `doc/api/`.
