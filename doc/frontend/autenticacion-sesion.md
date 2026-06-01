# Autenticación y sesión

El sistema usa autenticación basada en JWT entregado mediante cookie HTTP-only.

Ver `doc/03-autenticacion-autorizacion.md` para la descripción completa del mecanismo de autenticación del backend.

## Componentes frontend involucrados

```text
src/components/auth/LoginForm.jsx
src/components/auth/RecuperarPasswordForm.jsx
src/components/auth/RestablecerPasswordForm.jsx
src/components/app-sidebar.jsx
src/lib/config.js
src/lib/authz.js
src/lib/permission.js
```

## Endpoints consumidos

| Método | Ruta | Uso |
|---|---|---|
| POST | `/api/auth/login` | Inicia sesión con usuario y contraseña. |
| GET | `/api/auth/me` | Consulta el usuario autenticado y sus permisos. |
| POST | `/api/auth/logout` | Cierra sesión y limpia la cookie. |
| POST | `/api/auth/solicitar-recuperacion` | Solicita envío de token de recuperación por email. |
| POST | `/api/auth/restablecer-password` | Restablece contraseña con token válido. |

## Flujo de login

1. El usuario accede a la ruta raíz `/`.
2. `LoginForm` verifica si ya existe sesión activa con `GET /api/auth/me`.
3. Si la sesión es válida, redirige a `/inicio`.
4. Si no hay sesión, muestra el formulario de login.
5. El usuario ingresa usuario y contraseña.
6. El formulario envía `POST /api/auth/login` con `credentials: "include"`.
7. Si la respuesta es exitosa, el backend entrega la cookie de sesión.
8. El frontend redirige a `/inicio`.

### Request de login

```javascript
fetch(`${API_URL_BASE}/auth/login`, {
  method: "POST",
  credentials: "include",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ username: "correo@ejemplo.com", password: "contraseña" }),
});
```

### Validaciones en LoginForm

- El campo `username` valida que sea un email con formato válido antes de enviar.
- El botón de envío se deshabilita mientras `isSubmitting` es `true` para evitar envíos duplicados.

## Objeto de usuario autenticado

`GET /api/auth/me` devuelve el usuario con sus permisos:

```json
{
  "id": 1,
  "username": "correo@ejemplo.com",
  "nombre": "Nombre Apellido",
  "tipoPerfil": "ASESOR",
  "rolNombre": "Asesor",
  "rolId": 2,
  "perfilId": 5,
  "permisos": [
    { "id": 1, "nombre": "Ver consultas" },
    { "id": 2, "nombre": "Acceder inicio" }
  ]
}
```

El campo `perfilId` es el ID del perfil específico del usuario (asesor, estudiante, monitor), no el ID del usuario del sistema. Se usa para cargar estadísticas filtradas por perfil.

## Sesión en formularios protegidos

Cada formulario del dashboard verifica la sesión al montarse:

```javascript
useEffect(() => {
  async function verificar() {
    const res = await fetch(`${API_URL_BASE}/auth/me`, { credentials: "include" });
    if (res.status === 401) { router.replace("/"); return; }
    const user = await res.json();
    // verificar permisos específicos...
  }
  verificar();
}, []);
```

Esta verificación ocurre en el formulario, no en el layout del dashboard. El layout solo proporciona la estructura visual.

## Carga del usuario en el sidebar

`AppSidebar` hace una petición independiente a `/api/auth/me` al montarse para obtener el nombre y email del usuario autenticado y mostrarlos en el pie del sidebar. Si la petición falla, el área de usuario queda vacía sin interrumpir la navegación.

## Logout

El botón de cierre de sesión en `AppSidebar` llama a `POST /api/auth/logout` y redirige a `/`:

```javascript
await fetch(`${API_URL_BASE}/auth/logout`, {
  method: "POST",
  credentials: "include",
});
router.replace("/");
```

Si la petición falla, el frontend redirige igualmente para limpiar el estado visual.

## Manejo de 401 y 403

| Código | Significado | Acción en el frontend |
|---|---|---|
| `401` | Sesión no válida o expirada. | Redirigir a `/` (login). |
| `403` | Usuario autenticado sin permiso o sin alcance sobre el recurso. | Mostrar toast de error. No redirigir para que el usuario no pierda el contexto. |

## Recuperación de contraseña

El flujo de recuperación es independiente de la sesión:

1. El usuario accede a `/recuperar-password` y envía su email.
2. El frontend llama a `POST /api/auth/solicitar-recuperacion` con el email.
3. El backend envía un token por email.
4. El usuario accede a `/restablecer-password?token=...` con el token recibido.
5. El frontend lee el token de los query params de la URL y llama a `POST /api/auth/restablecer-password` con el token y la nueva contraseña.
6. El backend valida el token y restablece la contraseña.

El frontend no almacena ni manipula el token de recuperación directamente. El token se lee únicamente de la URL.

### Request de solicitud de recuperación

```javascript
fetch(`${API_URL_BASE}/auth/solicitar-recuperacion`, {
  method: "POST",
  credentials: "include",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ email: "correo@ejemplo.com" }),
});
```

### Request de restablecimiento

```javascript
fetch(`${API_URL_BASE}/auth/restablecer-password`, {
  method: "POST",
  credentials: "include",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ token: "token-del-link", nuevaPassword: "nueva-contraseña" }),
});
```
