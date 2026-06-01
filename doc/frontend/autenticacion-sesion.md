# Autenticación y sesión

El frontend implementa autenticación real contra el backend. El inicio de sesión se realiza con credenciales de usuario, el backend crea la sesión y las peticiones protegidas se envían con `credentials: "include"` para incluir la cookie de sesión.

## Componentes involucrados

| Archivo | Responsabilidad |
|---|---|
| `src/app/page.js` | Página pública de login. |
| `src/components/auth/LoginForm.jsx` | Formulario de autenticación. |
| `src/app/recuperar-password/page.js` | Página de recuperación de contraseña. |
| `src/components/auth/RecuperarPasswordForm.jsx` | Solicita instrucciones de recuperación. |
| `src/app/restablecer-password/page.js` | Página de restablecimiento con token. |
| `src/components/auth/RestablecerPasswordForm.jsx` | Envía nueva contraseña al backend. |
| `src/components/navigation/PermissionSidebar.jsx` | Valida sesión consultando `/auth/me`. |
| `src/components/app-sidebar.jsx` | Muestra usuario y ejecuta logout. |

## Login

`LoginForm.jsx` usa `react-hook-form` y envía:

```http
POST /api/auth/login
```

El payload enviado es:

```json
{
  "username": "correo@dominio.com",
  "password": "contraseña"
}
```

La petición usa:

```javascript
credentials: "include"
```

Si la respuesta es exitosa, el frontend redirige a:

```text
/inicio
```

Si la respuesta falla, el formulario muestra el mensaje devuelto por el backend mediante los helpers de `src/lib/api.js`.

## Validación de usuario autenticado

La sesión se consulta con:

```http
GET /api/auth/me
```

Este endpoint se usa en componentes de navegación para obtener:

- usuario autenticado;
- nombre o correo visible;
- rol;
- tipo de perfil;
- permisos asignados.

`PermissionSidebar.jsx` usa esta información para filtrar el menú lateral. `app-sidebar.jsx` también consulta `/auth/me` para mostrar nombre y correo en la barra lateral.

## Comportamiento ante sesión expirada

Cuando `PermissionSidebar.jsx` recibe `401` en `/auth/me`, redirige al login:

```text
/
```

`useApiForm` también redirige a `/` ante `401`, mostrando un mensaje de sesión expirada.

## Logout

El cierre de sesión se ejecuta desde `app-sidebar.jsx` con:

```http
POST /api/auth/logout
```

La petición incluye cookies y, después de llamar al backend, el frontend redirige al login con `router.replace("/")`.

## Recuperación de contraseña

La ruta `/recuperar-password` renderiza `RecuperarPasswordForm`. El formulario envía:

```http
POST /api/auth/solicitar-recuperacion
```

Payload:

```json
{
  "username": "correo@dominio.com"
}
```

El componente muestra mensaje de éxito o error. Cuando la solicitud es exitosa, redirige al login después de unos segundos.

## Restablecimiento de contraseña

La ruta `/restablecer-password` recibe un parámetro de URL:

```text
/restablecer-password?token=...
```

Si el token no existe, la página muestra enlace inválido o incompleto. Si el token existe, renderiza `RestablecerPasswordForm` y envía:

```http
POST /api/auth/restablecer-password
```

Payload:

```json
{
  "token": "valor-del-token",
  "passwordNueva": "nueva-contraseña",
  "confirmarPassword": "confirmación"
}
```

El formulario valida coincidencia de contraseñas en frontend y el backend conserva la validación definitiva.

## Tema visual en pantallas públicas

Las páginas públicas de login, recuperación y restablecimiento incluyen selector de tema usando `next-themes`. Las opciones visibles son:

- claro;
- oscuro;
- sistema.

## Responsabilidad de seguridad

El frontend nunca almacena tokens de acceso en `localStorage`. La sesión depende de la cookie gestionada por backend y enviada en peticiones con `credentials: "include"`.

La autorización real no depende del frontend. Aunque el menú o los botones se filtren visualmente por permisos, el backend valida cada operación protegida.
