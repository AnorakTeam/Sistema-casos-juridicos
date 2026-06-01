# Autenticación y autorización

## Autenticación

La autenticación se implementa en backend mediante `AuthController` y `AuthService`. El flujo de login valida credenciales, verifica que el usuario pueda autenticarse, genera un JWT y lo entrega al navegador en una cookie HTTP-only.

Endpoint principal:

```text
POST /api/auth/login
```

El cuerpo esperado corresponde a `LoginRequestDTO`, con usuario y contraseña.

## Cookie de sesión

Después del login exitoso, el backend crea la cookie:

```text
access_token
```

La cookie contiene el JWT y se configura como HTTP-only. El frontend no manipula manualmente el token; simplemente envía peticiones autenticadas con:

```javascript
credentials: "include"
```

## Sesión actual

El endpoint:

```text
GET /api/auth/me
```

permite consultar el usuario autenticado a partir del token recibido en la cookie. El backend valida que exista sesión activa y que el usuario pueda autenticarse.

## Logout

El endpoint:

```text
POST /api/auth/logout
```

responde eliminando la cookie de autenticación mediante `maxAge=0`.

## Cambio de contraseña

El endpoint:

```text
PATCH /api/auth/cambiar-password
```

requiere sesión activa y valida:

- datos obligatorios;
- contraseña actual correcta;
- contraseña nueva diferente de la actual.

## Recuperación de contraseña

El backend expone:

```text
POST /api/auth/solicitar-recuperacion
POST /api/auth/restablecer-password
```

La solicitud de recuperación devuelve un mensaje genérico para no revelar si el correo existe. El restablecimiento valida el token y actualiza la contraseña cuando la solicitud es válida.

## Seguridad HTTP

`SecurityConfig` configura:

- CORS;
- CSRF deshabilitado por tratarse de API stateless con JWT;
- `SessionCreationPolicy.STATELESS`;
- form login y HTTP Basic deshabilitados;
- manejo JSON de 401 y 403;
- `JwtAuthenticationFilter` antes de `UsernamePasswordAuthenticationFilter`;
- endpoints públicos de autenticación y Swagger/OpenAPI;
- autenticación obligatoria para el resto de endpoints.

## Autorización por permisos

Los controllers usan `@PreAuthorize` con permisos definidos en `PermisoNombre`. Ejemplos de grupos de permisos:

- navegación: `Acceder inicio`, `Acceder recepción`, `Acceder tareas`, etc.;
- consultas: `Ver consultas`, `Crear consultas`, `Editar consultas`, `Cambiar estado consultas`, `Archivar consultas`;
- seguimientos: `Ver seguimientos`, `Crear seguimientos`, `Editar seguimientos`, `Responder seguimientos`, `Aprobar respuestas de seguimiento`;
- procesos: `Ver procesos`, `Gestionar procesos`;
- conciliaciones: `Ver conciliaciones`, `Gestionar conciliaciones`, `Programar reuniones de conciliación`, `Concluir conciliaciones`;
- usuarios, roles y permisos;
- reportes: `Ver reportes`.

## Servicios de acceso

Además de `@PreAuthorize`, el backend contiene servicios de acceso por módulo. Estos servicios verifican alcance funcional sobre recursos específicos, por ejemplo:

- acceso a consultas;
- acceso a procesos;
- acceso a seguimientos;
- acceso a respuestas de seguimiento;
- acceso a conciliaciones;
- acceso a perfiles.

Esto permite combinar permisos generales con reglas de pertenencia o asignación.

## Resolución de perfil activo

El usuario autenticado tiene un tipo de perfil actual. Para resolver el perfil activo asociado se usa Strategy:

```text
PerfilUsuarioActivoResolver
PerfilUsuarioActivoResolverRegistry
```

Resolvers implementados:

- `AdministrativoPerfilUsuarioActivoResolver`;
- `AsesorPerfilUsuarioActivoResolver`;
- `ConciliadorPerfilUsuarioActivoResolver`;
- `EstudiantePerfilUsuarioActivoResolver`;
- `MonitorPerfilUsuarioActivoResolver`.

Cada resolver consulta el repositorio correspondiente y exige que el perfil esté activo.

## Cambio de perfil

El cambio de perfil usa estrategias para crear o actualizar el perfil destino:

```text
PerfilCambioHandler
PerfilCambioHandlerRegistry
```

Handlers implementados:

- `CambiarAAdministrativoHandler`;
- `CambiarAAsesorHandler`;
- `CambiarAConciliadorHandler`;
- `CambiarAEstudianteHandler`;
- `CambiarAMonitorHandler`.

Para desactivar el perfil anterior también se usa Strategy:

```text
PerfilEstadoHandler
PerfilEstadoHandlerRegistry
```

Handlers implementados:

- `AdministrativoPerfilEstadoHandler`;
- `AsesorPerfilEstadoHandler`;
- `ConciliadorPerfilEstadoHandler`;
- `EstudiantePerfilEstadoHandler`;
- `MonitorPerfilEstadoHandler`.

Los handlers de asesor, estudiante y monitor validan consultas operativas antes de desactivar el perfil anterior.

## Sincronización entre perfil y UsuarioSistema

El servicio `UsuarioSistemaPerfilEstadoService` mantiene sincronizado el estado del perfil con el estado del usuario de acceso cuando un perfil se desactiva o reactiva desde los command services de perfiles.

Esto aplica a:

- Administrativo;
- Asesor;
- Conciliador;
- Estudiante;
- Monitor.

## Frontend

El frontend implementa:

- `LoginForm` para login;
- `RecuperarPasswordForm` para solicitud de recuperación;
- `RestablecerPasswordForm` para restablecimiento;
- `PermissionSidebar` para navegación por permisos;
- consumo de `/auth/me` para sesión actual;
- envío de cookies con `credentials: "include"`.
