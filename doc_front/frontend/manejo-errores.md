# Manejo de errores

El frontend maneja los errores del backend de forma consistente usando helpers de `src/lib/api.js` y toasts de Sonner.

Ver `doc/05-estandar-api-errores.md` para la especificación completa del formato de error del backend.

## Formato de error del backend

```json
{
  "fecha": "fecha-hora-del-error",
  "estado": 400,
  "error": "Tipo de error",
  "mensaje": "Mensaje descriptivo para el usuario",
  "ruta": "/ruta/del/endpoint"
}
```

Con errores de validación por campo:

```json
{
  "fecha": "fecha-hora-del-error",
  "estado": 400,
  "error": "Error de validación",
  "mensaje": "Uno o más campos no son válidos",
  "ruta": "/ruta/del/endpoint",
  "detalles": {
    "campo": "Mensaje de validación del campo"
  }
}
```

## Helpers para extraer mensajes de error

```javascript
import { readResponseBody, getApiErrorTitle, getApiErrorDescription } from "@/lib/api";

const payload = await readResponseBody(response);
const titulo = getApiErrorTitle(payload, "Error al guardar");
const descripcion = getApiErrorDescription(payload);

toast.error(titulo, { description: descripcion });
```

`getApiErrorDescription` extrae los mensajes del campo `detalles`. Si no hay detalles, usa el título del error.

## Respuesta por código HTTP

| Código | Significado | Acción en el frontend |
|---|---|---|
| `200` | Éxito con respuesta. | Usar la respuesta normalmente. |
| `201` | Recurso creado. | Mostrar toast de éxito y actualizar la lista o redirigir. |
| `204` | Éxito sin cuerpo. | Mostrar toast de éxito. `readResponseBody` devuelve `null`. |
| `400` | Error de validación o de negocio. | Mostrar el mensaje del backend en un toast. Mantener el formulario editable. |
| `401` | Sesión no válida o expirada. | Redirigir a `/` (login). |
| `403` | Sin permiso o sin alcance. | Mostrar toast de error. No redirigir para preservar el contexto. |
| `404` | Recurso no encontrado. | Mostrar mensaje y volver al listado o detalle válido. |
| `409` | Conflicto (duplicado u operación inválida). | Mostrar el mensaje del backend. |
| `500` | Error interno del servidor. | Mostrar mensaje general y permitir reintentar. |

## Errores de red

Cuando `fetch` lanza una excepción (timeout, servidor caído, sin conexión), el frontend muestra un toast de error de conexión:

```javascript
catch (error) {
  console.error("Error de red:", error);
  toast.error("Error de conexión", {
    description: "Verifique que el backend esté disponible",
  });
}
```

## Feedback al usuario

El frontend usa **Sonner** para las notificaciones toast. Las variantes usadas son:

| Variante | Uso |
|---|---|
| `toast.success` | Operación exitosa (guardar, actualizar, importar). |
| `toast.error` | Error del backend, sin permiso, error de red. |
| `toast.warning` | Advertencia parcial (ej: consulta creada pero archivos no subidos). |

Los mensajes de toast muestran el texto del backend directamente cuando está disponible, para que el usuario reciba información precisa sin necesidad de buscar en los logs.

## Errores de validación de archivos

`FormFileUpload` valida tipo MIME y tamaño antes de agregar cada archivo. Los archivos que no cumplen se rechazan con un `toast.error` que indica el nombre del archivo y la razón:

```text
"archivo.exe" tiene un formato no permitido. Use PDF, imágenes o documentos Office.
"documento.pdf" supera el tamaño máximo de 10 MB.
```

Los archivos válidos del mismo lote se agregan normalmente.

## Errores de permisos en acciones

Cuando el usuario intenta ejecutar una acción sin el permiso necesario, el formulario muestra un toast sin redirigir:

```javascript
if (!tienePermiso(user, PERMISOS.CAMBIAR_ESTADO_ESTUDIANTES)) {
  toast.error("Sin permiso", {
    description: "No tienes permiso para cambiar el estado de estudiantes.",
  });
  return;
}
```

Esta regla evita que el usuario pierda el contexto de la pantalla en la que estaba trabajando.

## Errores de validación cruzada en formularios

Las validaciones que dependen de múltiples campos se ejecutan en el handler de envío, antes de llamar al backend. Si fallan, se muestra un toast con descripción específica y, cuando aplica, el formulario navega al paso donde está el campo con error:

```javascript
// Ejemplo: validación cruzada de contacto en PersonaForm
if (!telefono && !correo) {
  toast.error("Contacto requerido", {
    description: "Debe ingresar al menos un teléfono o un correo electrónico.",
  });
  setPasoActual(pasos.indexOf("Contacto"));
  return;
}
```

## Errores de formato en importación masiva

El endpoint `POST /api/estudiantes/importar` devuelve `400` con texto plano cuando el archivo no tiene el formato esperado. El formulario captura este caso específico y lo muestra como "Error de formato" con el mensaje exacto del backend.

Cuando el proceso se ejecuta parcialmente, la respuesta incluye el resumen:

```json
{
  "exitosos": 3,
  "fallidos": 2,
  "totalFilas": 5,
  "errores": [
    "Fila 2: Ya existe un estudiante con ese documento",
    "Fila 4: El email es inválido"
  ]
}
```

El formulario muestra tarjetas con los totales y una lista con cada error por fila.
