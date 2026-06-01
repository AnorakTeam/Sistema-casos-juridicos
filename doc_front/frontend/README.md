# Frontend — documentación

Esta carpeta documenta la implementación frontend del sistema de gestión de casos jurídicos.

La documentación describe lo que está implementado. No presenta como vigente ningún componente, pantalla o flujo que no exista en el código fuente actual.

## Índice

| Documento | Contenido |
|---|---|
| `estructura.md` | Árbol de carpetas real, responsabilidades y convenciones de nombres. |
| `configuracion-api.md` | Variables de entorno, URL base, uso de credenciales y reglas de seguridad. |
| `autenticacion-sesion.md` | Flujo de login, sesión, cookie, usuario autenticado, logout y manejo de 401. |
| `navegacion-permisos.md` | Menús visibles, rutas protegidas, permisos de navegación y de acción. |
| `servicios-api.md` | Cliente HTTP centralizado, hooks de formulario y patrones de consumo. |
| `formularios-validaciones.md` | Patrón de formularios, validaciones frontend y relación con validaciones backend. |
| `manejo-errores.md` | Mensajes por código HTTP, helpers de error y patrones de feedback. |
| `modulos/consultas.md` | Creación y gestión de consultas jurídicas. |
| `modulos/seguimientos.md` | Seguimientos, respuestas, calendario y comportamiento por rol. |
| `modulos/personas.md` | Registro y gestión de personas con formulario por pasos. |
| `modulos/procesos.md` | Procesos judiciales. |
| `modulos/estadisticas.md` | Dashboard de estadísticas por semestre y rango libre. |
| `modulos/catalogos.md` | Áreas, temas y tipos (catálogos del sistema). |
| `modulos/conciliaciones.md` | Pantallas de conciliaciones, estados, acciones y endpoints consumidos. |
| `modulos/reuniones-conciliacion.md` | Programación y reprogramación de reuniones de conciliación. |
| `modulos/usuarios-roles.md` | Usuarios, roles, permisos, estudiantes, asesores y cargue masivo. |
| `modulos/eliminacion.md` | Desactivación y reactivación de registros. |
| `mantenimiento-frontend.md` | Checklist para actualizar documentación cuando cambian rutas, permisos o formularios. |

## Tecnologías principales

- Next.js 15 con App Router.
- React 19.
- Tailwind CSS.
- shadcn/ui (Radix UI).
- react-hook-form para formularios.
- Fetch API con `credentials: "include"`.
- Sonner para notificaciones toast.
- Playwright para pruebas end-to-end.

## Principios

- El frontend controla visibilidad de menús, botones y acciones según permisos.
- El backend valida permisos, alcance y reglas de negocio en cada endpoint.
- La visibilidad en frontend no reemplaza la seguridad del backend.
- No se documentan secretos, tokens, credenciales ni datos personales reales.

## Relación con la documentación existente

| Documento | Cómo lo usa el frontend |
|---|---|
| `doc/api` | Referencia de contratos REST consumidos desde hooks y formularios. |
| `doc/reglas` | Fuente para traducir reglas de negocio en validaciones de UI. |
| `doc/backend` | Referencia para entender responsabilidades del backend, no para duplicar lógica. |
| `doc/base-datos` | Fuente para entender catálogos, estados y entidades que aparecen en formularios. |
| `doc/decisiones` | Guía de coherencia para permisos, seguridad y manejo de estado. |

## Seguridad documental

Las variables `NEXT_PUBLIC_*` son visibles en el navegador. No deben contener secretos, tokens, API keys, contraseñas, usuarios reales de prueba ni cadenas de conexión con credenciales.
