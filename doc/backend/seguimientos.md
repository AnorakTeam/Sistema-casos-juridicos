# Backend - Módulo de seguimientos y respuestas

## Propósito del módulo

El módulo de seguimientos permite registrar tareas, requerimientos o controles asociados a una consulta jurídica. También administra respuestas realizadas por estudiantes y su revisión por usuarios autorizados. El módulo integra notificaciones inmediatas, recordatorios programados, alertas disciplinarias y reglas de visibilidad para estudiantes.

La implementación vigente separa tres responsabilidades principales:

1. Gestión del seguimiento.
2. Gestión de respuestas del estudiante.
3. Gestión de notificaciones y recordatorios.

## Fuentes de código revisadas

| Tipo | Archivos principales |
|---|---|
| Controllers | `SeguimientoController`, `SeguimientoRespuestaController`, `CategoriaSeguimientoController` |
| Servicios fachada | `SeguimientoService`, `SeguimientoRespuestaService`, `CategoriaSeguimientoService`, `SeguimientoNotificacionService` |
| Escritura | `SeguimientoCommandService`, `SeguimientoRespuestaCommandService` |
| Lectura | `SeguimientoQueryService`, `SeguimientoRespuestaQueryService` |
| Estado | `SeguimientoEstadoService` |
| Validadores | `SeguimientoValidator`, `SeguimientoRespuestaValidator`, `CategoriaSeguimientoValidator` |
| Notificaciones | `SeguimientoNotificacionService`, `SeguimientoRecordatorioService`, `SeguimientoEnvioNotificacionService`, `SeguimientoNotificacionEstadoService`, `SeguimientoNotificacionInmediataService`, `SeguimientoDestinatarioService` |
| Scheduler | `SeguimientoNotificacionScheduler` |
| DTOs | `SeguimientoRequestDTO`, `SeguimientoResponseDTO`, `SeguimientoRespuestaRequestDTO`, `SeguimientoRespuestaDecisionDTO`, `SeguimientoRespuestaResponseDTO`, `CategoriaSeguimientoDTO` |
| Entidades | `Seguimiento`, `SeguimientoRespuesta`, `SeguimientoNotificacion`, `CategoriaSeguimiento` |
| Estados | `EstadoSeguimiento`, `EstadoRespuestaSeguimiento` |
| Repositorios | `SeguimientoRepository`, `SeguimientoRespuestaRepository`, `SeguimientoNotificacionRepository`, `CategoriaSeguimientoRepository` |
| Pruebas | `SeguimientoValidatorTest`, `SeguimientoRespuestaValidatorTest`, `ConsultaEstadoServiceTest` |

## Seguimiento

La entidad `Seguimiento` representa una tarea o requerimiento asociado a una consulta.

Campos principales:

| Campo | Descripción |
|---|---|
| `descripcion` | Texto descriptivo del seguimiento. |
| `fechaEntrega` | Fecha límite asociada al seguimiento. |
| `diasNotificacion` | Número de días previos para recordatorio. |
| `notificarPartes` | Indica si se generan notificaciones a partes. |
| `notificarEstudiante` | Indica si el seguimiento es visible/notificable para estudiante. |
| `alertaDisciplinaria` | Marca el seguimiento como alerta disciplinaria. |
| `categoriaSeguimiento` | Categoría funcional del seguimiento. |
| `consulta` | Consulta asociada. |
| `autor` | Usuario que crea el seguimiento. |
| `estado` | Estado funcional del seguimiento. |
| `activo` | Marca de eliminación lógica. |

## Estados de seguimiento

El enum `EstadoSeguimiento` define:

| Estado | Uso funcional |
|---|---|
| `PENDIENTE` | Estado inicial. Requiere gestión o respuesta. |
| `COMPLETADO` | Seguimiento cumplido. |
| `CANCELADO` | Seguimiento cancelado. |

Solo los seguimientos pendientes conservan notificaciones activas. Al cambiar de estado se aplican efectos mediante `SeguimientoEstadoService`.

## Creación de seguimiento

El flujo de creación en `SeguimientoCommandService` incluye:

1. Validar DTO de creación.
2. Validar permiso y alcance.
3. Normalizar descripción.
4. Validar fecha de entrega y días de notificación.
5. Cargar categoría activa.
6. Cargar consulta y validar operación operativa.
7. Normalizar booleanos (`notificarPartes`, `notificarEstudiante`, `alertaDisciplinaria`).
8. Validar que `notificarEstudiante=true` solo se use si la consulta tiene estudiante activo.
9. Crear seguimiento en estado `PENDIENTE` y `activo=true`.
10. Guardar.
11. Sincronizar notificaciones después de guardar.

## Regla de estudiante activo

`SeguimientoValidator.validarNotificarEstudianteConConsulta` aplica la regla vigente:

- Si `notificarEstudiante=false`, no se exige estudiante.
- Si `notificarEstudiante=true`, la consulta debe existir.
- Si `notificarEstudiante=true`, la consulta debe tener estudiante asignado.
- Si `notificarEstudiante=true`, el estudiante asignado debe estar activo.

Esta regla evita crear tareas visibles o notificables para un estudiante inexistente o inactivo.

## Actualización de seguimiento

La actualización permite modificar datos del seguimiento mientras esté pendiente. El backend valida:

- permisos de edición;
- consulta asociada operativa;
- seguimiento en estado `PENDIENTE`;
- que no cambie la consulta;
- existencia de cambios reales;
- consistencia de notificación al estudiante;
- actualización de notificaciones según nuevo estado.

## Eliminación lógica

`DELETE /api/seguimientos/{id}` elimina lógicamente el seguimiento. Antes de desactivar el registro se cancelan notificaciones pendientes. Las notificaciones ya enviadas permanecen como historial.

## Respuestas de seguimiento

La entidad `SeguimientoRespuesta` representa la respuesta de un estudiante a un seguimiento visible para él.

Estados:

| Estado | Descripción |
|---|---|
| `PENDIENTE` | Respuesta enviada y pendiente de revisión. |
| `APROBADA` | Respuesta aprobada por usuario autorizado. |
| `RECHAZADA` | Respuesta rechazada con observación. |

## Creación y edición de respuestas

El estudiante puede crear respuesta sobre seguimientos visibles para él. Puede editar la respuesta mientras permanezca pendiente. La validación de acceso se maneja con `SeguimientoRespuestaAccessService`.

## Decisión de respuestas

La decisión se realiza mediante `SeguimientoRespuestaDecisionDTO`. El backend permite aprobar o rechazar.

Reglas:

- La decisión debe ser `APROBADA` o `RECHAZADA`.
- No se acepta `PENDIENTE` como decisión final.
- Si la decisión es `RECHAZADA`, `observacionRevision` es obligatoria.
- La observación no puede superar 500 caracteres.
- Para `APROBADA`, la observación es opcional.

## Notificaciones

El módulo administra notificaciones de seguimiento mediante entidades y servicios especializados.

Componentes principales:

| Componente | Función |
|---|---|
| `SeguimientoNotificacionService` | Orquesta sincronización y cancelación de notificaciones. |
| `SeguimientoRecordatorioService` | Crea recordatorios asociados a fecha de entrega. |
| `SeguimientoNotificacionInmediataService` | Gestiona notificaciones inmediatas. |
| `SeguimientoEnvioNotificacionService` | Envía notificaciones pendientes. |
| `SeguimientoNotificacionEstadoService` | Crea, marca enviada o cancela notificaciones. |
| `SeguimientoDestinatarioService` | Determina destinatarios. |
| `SeguimientoNotificacionScheduler` | Procesa notificaciones programadas. |

## Alertas disciplinarias

Los seguimientos pueden marcarse como alerta disciplinaria mediante el campo `alertaDisciplinaria`. Existe endpoint específico para listar alertas disciplinarias y requiere permiso `VER_ALERTAS_DISCIPLINARIAS`.

## Relación con cierre de consulta

`ConsultaEstadoService` bloquea el cierre de una consulta si existen:

- seguimientos pendientes;
- respuestas pendientes;
- notificaciones pendientes no enviadas.

Esto asegura que una consulta no se cierre mientras existan tareas académicas u operativas abiertas.

## Permisos

| Función | Permiso |
|---|---|
| Ver seguimientos | `VER_SEGUIMIENTOS` |
| Crear seguimientos | `CREAR_SEGUIMIENTOS` |
| Editar seguimientos | `EDITAR_SEGUIMIENTOS` |
| Eliminar seguimientos | `ELIMINAR_SEGUIMIENTOS` |
| Ver alertas disciplinarias | `VER_ALERTAS_DISCIPLINARIAS` |
| Responder seguimientos | `RESPONDER_SEGUIMIENTOS` |
| Aprobar respuestas | `APROBAR_RESPUESTAS_SEGUIMIENTO` |

## Pruebas relacionadas

- `SeguimientoValidatorTest` valida la regla de notificación al estudiante.
- `SeguimientoRespuestaValidatorTest` valida reglas de decisión y observación.
- `ConsultaEstadoServiceTest` valida que seguimientos, respuestas y notificaciones pendientes bloqueen cierre de consulta.
