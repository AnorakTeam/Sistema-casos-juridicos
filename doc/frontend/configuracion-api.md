# Configuración de API

La configuración central de conexión con el backend se encuentra en `src/lib/config.js`.

## Variables de entorno

El frontend usa variables `NEXT_PUBLIC_*` de Next.js para conocer las URLs del backend.

| Variable | Propósito | Valor por defecto |
|---|---|---|
| `NEXT_PUBLIC_API_URL_BASE` | URL base del backend principal, incluyendo el segmento `/api`. | `http://localhost:8080/api` |
| `NEXT_PUBLIC_API_URL` | URL base del backend **sin** el segmento `/api`. Usada por docker-compose. `config.js` agrega `/api` automáticamente si falta. | `http://localhost:8080` |
| `NEXT_PUBLIC_FILE_STORAGE_API_URL_BASE` | URL base del servicio de almacenamiento de archivos. | Igual a `NEXT_PUBLIC_API_URL_BASE` |

> **Nota sobre docker-compose**: el archivo `docker-compose.yml` pasa `NEXT_PUBLIC_API_URL=http://localhost:8080` (sin `/api`). `src/lib/config.js` normaliza la URL automáticamente agregando el sufijo `/api` cuando no termina en él, por lo que ambas formas funcionan correctamente.

Las variables `NEXT_PUBLIC_*` son visibles en el navegador. No deben contener secretos, tokens, API keys, contraseñas ni datos sensibles.

Para configurar el entorno de desarrollo, copiar `.env.example` a `.env.local` y ajustar los valores:

```bash
cp .env.example .env.local
```

## Constantes exportadas

```javascript
// src/lib/config.js
export const API_URL_BASE             // URL base del backend principal
export const FILE_STORAGE_API_URL_BASE // URL base del servicio de archivos
```

Los componentes y hooks deben importar estas constantes. No se deben hardcodear URLs del backend en ningún componente.

## Regla de uso

```javascript
import { API_URL_BASE, FILE_STORAGE_API_URL_BASE } from "@/lib/config";

// Correcto
const response = await fetch(`${API_URL_BASE}/personas`, { credentials: "include" });

// Incorrecto — nunca hardcodear
const response = await fetch("http://localhost:8080/api/personas", { credentials: "include" });
```

## Credenciales de sesión

Todas las peticiones protegidas deben incluir:

```javascript
credentials: "include"
```

El backend gestiona la sesión mediante una cookie HTTP-only llamada `access_token`. Esta cookie se envía automáticamente con cada petición cuando se usa `credentials: "include"`. El frontend no la lee ni la manipula directamente.

## Cliente HTTP centralizado

`src/lib/apiClient.js` provee métodos `get`, `post`, `put`, `patch` y `delete` que incluyen `credentials: "include"` automáticamente y manejan el cuerpo JSON.

```javascript
import { apiClient } from "@/lib/apiClient";

const res = await apiClient.get("/auth/me");
const user = await res.json();

const res = await apiClient.post("/personas", payload);
```

Para código nuevo se recomienda usar `apiClient` en lugar de llamar `fetch` directamente, para mantener uniformidad en headers y credenciales.

## Peticiones de archivos

Los endpoints de archivos usan `FILE_STORAGE_API_URL_BASE` y envían `multipart/form-data`.

```javascript
const formData = new FormData();
formData.append("files", archivo);
formData.append("path", String(consultaId));

const response = await fetch(`${FILE_STORAGE_API_URL_BASE}/files/upload-multiple`, {
  method: "POST",
  credentials: "include",
  body: formData,
  // No incluir Content-Type: el navegador lo agrega con el boundary correcto
});
```

## Reglas de seguridad

- No usar variables de entorno para información sensible visible en el navegador.
- No registrar URLs reales de producción en esta documentación.
- No incluir tokens ni secretos en ejemplos de código.
- La configuración real de producción se gestiona fuera del repositorio.
