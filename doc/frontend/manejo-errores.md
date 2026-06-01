# Manejo de errores

El frontend maneja errores del backend mediante helpers de `src/lib/api.js`, mensajes locales y toasts de Sonner. El objetivo es mostrar retroalimentación clara al usuario sin perder el contexto del formulario o listado.

## Formato esperado del backend

El backend devuelve errores estructurados con campos como:

```json
{
  "fecha": "fecha-hora-del-error",
  "estado": 400,
  "error": "Tipo de error",
  "mensaje": "Mensaje descriptivo",
  "ruta": "/api/recurso"
}
```

Cuando hay detalles por campo, el payload puede incluir un objeto de detalles. Los helpers del frontend también contemplan nombres alternativos como `details`, `errors`, `fieldErrors` y `validaciones`.

## Helpers principales

| Helper | Función |
|---|---|
| `readResponseBody(response)` | Lee JSON, texto o devuelve `null` si no hay cuerpo. |
| `getApiErrorMessages(payload)` | Extrae mensajes de detalle. |
| `getApiErrorTitle(payload, fallback)` | Obtiene el mensaje principal. |
| `getApiErrorDescription(payload, fallback)` | Construye descripción para toast. |

## Respuesta por código HTTP

| Código | Manejo frontend |
|---|---|
| `200` | Usa la respuesta y actualiza UI. |
| `201` | Muestra éxito y actualiza listado o formulario. |
| `204` | Lee cuerpo como `null` y muestra éxito si aplica. |
| `400` | Muestra mensaje de validación o negocio. |
| `401` | Redirige al login en navegación o formularios. |
| `403` | Muestra mensaje de no autorizado. |
| `404` | Muestra mensaje de recurso no encontrado. |
| `409` | Muestra conflicto o duplicado informado por backend. |
| `500` | Muestra mensaje general y conserva contexto de usuario. |

## Toasts

El frontend usa Sonner para mensajes visuales. El `Toaster` se monta en el layout del dashboard.

| Caso | Mensaje típico |
|---|---|
| Operación exitosa | `toast.success(...)` |
| Error de negocio | `toast.error(título, { description })` |
| Sesión expirada | `toast.error("Sesión expirada", ...)` y redirección. |
| Sin permiso | `toast.error("No autorizado", ...)` |
| Error de red | `toast.error("Error de conexión", ...)` |

## Errores de red

Cuando `fetch` lanza excepción, los componentes o hooks capturan el error, lo registran con `console.error` y muestran mensaje de conexión.

```javascript
catch (error) {
  console.error("Error de red:", error);
  toast.error("Error de conexión", {
    description: "Verifique que el backend esté disponible",
  });
}
```

## Manejo en `useApiForm`

`useApiForm` centraliza errores para formularios JSON:

- `401`: muestra sesión expirada y redirige a `/`;
- `403`: muestra no autorizado;
- `response.ok`: muestra éxito;
- errores con cuerpo: usa `getApiErrorTitle` y `getApiErrorDescription`;
- errores de red: muestra error de conexión.

## Manejo en formularios específicos

Algunos formularios usan estado local para mostrar errores dentro del formulario, especialmente en autenticación. Otros usan toasts para operaciones de gestión.

| Módulo | Patrón observado |
|---|---|
| Login | Mensaje local bajo el formulario. |
| Recuperación de contraseña | Mensaje local de éxito o error. |
| Restablecimiento | Mensaje local de éxito o error. |
| Formularios de gestión | Toasts con mensajes del backend. |
| Operaciones sensibles | Diálogo de confirmación + toast de resultado. |

## Confirmación de acciones

`ConfirmActionDialog.jsx` proporciona confirmación visual para acciones sensibles. Recibe título, descripción, texto de confirmación, estado de carga, variante y callbacks.

Se usa para reducir errores del usuario antes de ejecutar operaciones como desactivación, eliminación lógica, cambios de estado o acciones administrativas.

## Buenas prácticas aplicadas

- No ocultar mensajes de validación del backend.
- Mantener formularios editables después de errores de negocio.
- Redirigir al login solo en sesión inválida.
- No redirigir automáticamente ante `403` para conservar contexto.
- Usar mensajes concretos del backend cuando existan.
- Registrar errores técnicos en consola para depuración.

## Relación con backend

El frontend interpreta errores; no redefine reglas. Los mensajes funcionales principales provienen del backend y se presentan al usuario con formato legible.
