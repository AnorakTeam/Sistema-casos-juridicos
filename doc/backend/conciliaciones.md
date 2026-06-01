# Backend - Módulo de conciliaciones y reuniones

## Propósito del módulo

El módulo de conciliaciones administra solicitudes de conciliación asociadas a consultas jurídicas, asignación de estudiante y conciliador, programación de reuniones, historial de reprogramaciones, notificaciones a destinatarios y finalización con acta. Su diseño integra reglas de negocio del consultorio jurídico con trazabilidad documental y control de estados.

## Fuentes de código revisadas

| Tipo | Archivos principales |
|---|---|
| Controller | `ConciliacionController` |
| Servicio fachada | `ConciliacionService` |
| Escritura | `ConciliacionCommandService`, `ReunionConciliacionCommandService` |
| Lectura | `ConciliacionQueryService` |
| Validadores | `ConciliacionValidator`, `ReunionConciliacionValidator` |
| Acceso | `ConciliacionAccessService`, `ConciliacionAlcanceService` |
| Asignación | `ConciliacionAsignacionService` |
| Documentos | `ConciliacionDocumentoService` |
| Reunión | `ReunionConciliacionService`, `ReunionConciliacionHistorialService`, `ReunionConciliacionMapper` |
| Notificaciones | `ReunionConciliacionNotificacionService`, `ReunionConciliacionRecordatorioService`, `ReunionConciliacionEnvioNotificacionService`, `ReunionConciliacionNotificacionEstadoService`, `ReunionConciliacionDestinatarioService` |
| Scheduler | `ReunionConciliacionNotificacionScheduler` |
| DTOs | `ConciliacionResponseDTO`, `ConciliacionDetalleResponseDTO`, `ReunionConciliacionRequestDTO`, `ReunionConciliacionResponseDTO` |
| Entidades | `Conciliacion`, `EstadoConciliacion`, `ReunionConciliacion`, `ReunionConciliacionHistorial`, `ReunionConciliacionNotificacion` |
| Repositorios | `ConciliacionRepository`, `EstadoConciliacionRepository`, `ReunionConciliacionRepository`, `ReunionConciliacionHistorialRepository`, `ReunionConciliacionNotificacionRepository` |

## Estados técnicos de conciliación

Los códigos técnicos definidos son:

| Código | Descripción |
|---|---|
| `EN_ESPERA` | Conciliación creada, pero sin responsables mínimos completos. |
| `ESPERANDO_REUNION` | Tiene responsables mínimos y está lista para programar reunión. |
| `REUNION_PROGRAMADA` | Tiene reunión programada. |
| `COMPLETO_CONCILIADO` | Finalizada con acuerdo conciliado. |
| `COMPLETO_NO_CONCILIADO` | Finalizada sin conciliación. |

`ConciliacionValidator` distingue estados finalizados y no finalizados para controlar creación, cierre de consulta y cambios de estado.

## Creación desde consulta

La conciliación se crea desde una consulta existente mediante solicitud PDF. El backend valida:

1. Permiso para crear conciliación.
2. Consulta existente.
3. Consulta no cerrada ni archivada.
4. Ausencia de conciliación activa no finalizada para la misma consulta.
5. Selección de estudiante por reglas de asignación.
6. Selección de conciliador por carga.
7. Solicitante actual.
8. Estado calculado según asignación.
9. Guardado del documento de solicitud.

El documento se guarda después del primer `save` porque la ruta depende del id de conciliación.

## Asignación de responsables

El módulo maneja dos responsables específicos:

- estudiante habilitado para conciliación;
- conciliador activo.

La asignación puede ser automática al crear o manual mediante endpoints específicos. Si falta alguno de los responsables, el estado puede permanecer en `EN_ESPERA`. Si ambos existen, puede pasar a `ESPERANDO_REUNION`.

## Programación de reunión

La reunión se programa mediante `ReunionConciliacionRequestDTO`, que contiene:

| Campo | Regla |
|---|---|
| `fechaReunion` | Obligatoria. Debe ser futura. |
| `sedeId` | Obligatoria. Debe corresponder a sede activa. |
| `observaciones` | Opcional, máximo 300 caracteres. |

Al programar reunión:

1. Se valida que la conciliación no esté finalizada.
2. Se valida que la consulta permita operación.
3. Se exige estudiante y conciliador.
4. Se exige fecha futura.
5. Se guarda reunión.
6. Se registra historial.
7. Se crean notificaciones inmediatas y recordatorios.
8. El estado pasa a `REUNION_PROGRAMADA`.

## Reprogramación de reunión

La reprogramación usa `PUT /api/conciliaciones/{id}/reunion`. El backend cancela notificaciones pendientes anteriores y registra nuevo historial. Las notificaciones ya enviadas permanecen como historial.

## Notificaciones de reunión

El sistema genera notificaciones a destinatarios de la reunión y recordatorios. Los componentes principales son:

| Servicio | Función |
|---|---|
| `ReunionConciliacionNotificacionService` | Orquesta creación, envío y cancelación de pendientes. |
| `ReunionConciliacionRecordatorioService` | Crea recordatorios programados. |
| `ReunionConciliacionEnvioNotificacionService` | Envía notificaciones pendientes. |
| `ReunionConciliacionNotificacionEstadoService` | Crea, envía o desactiva notificaciones. |
| `ReunionConciliacionDestinatarioService` | Obtiene destinatarios con correo. |
| `ReunionConciliacionNotificacionScheduler` | Procesa pendientes por fecha. |

## Finalización

La finalización se realiza con acta PDF mediante:

`POST /api/conciliaciones/{id}/finalizar`

Reglas:

- Se requiere permiso de gestión o conclusión.
- La conciliación debe estar activa.
- La consulta no debe estar cerrada ni archivada.
- El estado final debe ser `COMPLETO_CONCILIADO` o `COMPLETO_NO_CONCILIADO`.
- Debe existir estudiante y conciliador.
- Debe guardarse acta.
- Se registra fecha de finalización.
- Se cancelan notificaciones pendientes de reunión.

## Desactivación lógica

`DELETE /api/conciliaciones/{id}` desactiva la conciliación. No representa finalización con acta. Al desactivar, también se cancelan notificaciones pendientes para evitar recordatorios futuros de una conciliación inactiva.

## Relación con cierre de consulta

Una consulta no puede cerrarse si tiene conciliaciones activas en estados no finalizados:

- `EN_ESPERA`
- `ESPERANDO_REUNION`
- `REUNION_PROGRAMADA`

Los estados finalizados no bloquean el cierre.

## Permisos

| Operación | Permiso |
|---|---|
| Ver conciliaciones | `VER_CONCILIACIONES` |
| Crear, asignar conciliador, reemplazar solicitud, desactivar | `GESTIONAR_CONCILIACIONES` |
| Programar reunión | `PROGRAMAR_REUNIONES_CONCILIACION` |
| Reprogramar reunión | `REPROGRAMAR_REUNIONES_CONCILIACION` |
| Concluir/finalizar | `CONCLUIR_CONCILIACIONES` o `GESTIONAR_CONCILIACIONES` |

## Resumen funcional

El módulo de conciliación implementa un flujo completo: solicitud, asignación, reunión, notificación, historial y finalización documental. El backend mantiene consistencia entre estados, responsables, documentos y notificaciones pendientes.
